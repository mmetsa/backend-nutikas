package ee.nutikas.games.api.school.service;

import ee.nutikas.games.api.auth.dto.RegisterRequest;
import ee.nutikas.games.api.auth.service.AuthService;
import ee.nutikas.games.api.enums.Role;
import ee.nutikas.games.api.school.dto.SchoolRequest;
import ee.nutikas.games.api.school.dto.SchoolResponse;
import ee.nutikas.games.api.school.mapper.SchoolMapper;
import ee.nutikas.games.api.school.model.SchoolCodeModel;
import ee.nutikas.games.api.school.repository.SchoolCodeRepository;
import ee.nutikas.games.api.school.repository.SchoolRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class SchoolService {

    private final SchoolRepository repository;
    private final SchoolCodeRepository schoolCodeRepository;
    private final AuthService authService;
    private final ClassService classService;

    public void createSchool(SchoolRequest request) {
        validateCode(request.getCode());

        var model = SchoolMapper.MAPPER.toModel(request);
        repository.save(model);

        schoolCodeRepository.findByCode(request.getCode()).ifPresent(m -> {
            m.setSchoolModel(model);
            schoolCodeRepository.save(m);
        });

        classService.createBaseClassesForSchool(model);

        var register = new RegisterRequest();
        register.setEmail(request.getAdminEmail());
        register.setNickname(request.getUsername());
        register.setPassword(request.getPassword());
        register.setSchoolId(model.getId());
        authService.registerUser(register, Role.SCHOOL_ADMIN);
    }

    public List<SchoolResponse> getList() {
        var schools = repository.findAll();
        return SchoolMapper.MAPPER.toResponseList(schools);
    }

    public List<String> generateSchoolCodes(Integer amount) {
        List<SchoolCodeModel> codeModels = new ArrayList<>();
        for (int i = 0; i < amount; i++) {
            codeModels.add(SchoolCodeModel.builder().code(SchoolCodeGenerator.generateCode()).build());
        }
        codeModels = schoolCodeRepository.saveAll(codeModels);
        return codeModels.stream().map(SchoolCodeModel::getCode).toList();
    }

    public boolean validateCode(String code) {
        var model = schoolCodeRepository.findByCode(code);
        if (model.isPresent() && model.get().getSchoolModel() == null) {
            return true;
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "INVALID_CODE");
        }
    }
}
