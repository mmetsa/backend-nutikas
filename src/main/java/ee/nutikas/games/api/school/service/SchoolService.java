package ee.nutikas.games.api.school.service;

import ee.nutikas.games.api.school.dto.SchoolRequest;
import ee.nutikas.games.api.school.dto.SchoolResponse;
import ee.nutikas.games.api.school.mapper.SchoolMapper;
import ee.nutikas.games.api.school.repository.SchoolRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class SchoolService {

    private final SchoolRepository repository;

    public SchoolResponse createSchool(SchoolRequest request) {

        var dto = SchoolMapper.MAPPER.toDTO(request);
        var model = repository.save(SchoolMapper.MAPPER.toModel(dto));

        dto = SchoolMapper.MAPPER.toDTO(model);

        return SchoolMapper.MAPPER.toResponse(dto);
    }

    public List<SchoolResponse> getList() {
        var schools = repository.findAll();
        return SchoolMapper.MAPPER.toResponseList(schools);
    }

}
