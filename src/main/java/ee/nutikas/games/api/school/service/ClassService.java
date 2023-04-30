package ee.nutikas.games.api.school.service;

import ee.nutikas.games.api.auth.model.UserRoleSchoolClassModel;
import ee.nutikas.games.api.enums.Role;
import ee.nutikas.games.api.school.dto.ClassResponse;
import ee.nutikas.games.api.school.mapper.ClassMapper;
import ee.nutikas.games.api.school.model.ClassModel;
import ee.nutikas.games.api.school.model.SchoolModel;
import ee.nutikas.games.api.school.repository.ClassRepository;
import ee.nutikas.games.api.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ClassService {

    private final UserService userService;
    private final ClassRepository classRepository;

    public List<ClassResponse> getUserClasses(Long userId, Long schoolId) {
        var user = userService.findUserById(userId).orElseThrow();

        List<ClassModel> classes;

        if (user.getUserRoles().stream().anyMatch(r -> schoolId.equals(r.getSchool().getId())
                && Role.SCHOOL_ADMIN.equals(r.getRole().getName()))) {
            classes = classRepository.findBySchoolId(schoolId);
        } else {
            classes = user.getUserRoles().stream()
                    .filter(cm -> cm.getSchool().getId().equals(schoolId) &&
                            Role.TEACHER.equals(cm.getRole().getName()))
                    .map(UserRoleSchoolClassModel::getClassModel)
                    .toList();
        }

        var classResponses = ClassMapper.INSTANCE.toResponseList(classes);
        classResponses.forEach(c -> c.setCount(userService.getClassUserCount(c.getId())));

        return classResponses;
    }

    public void createBaseClassesForSchool(SchoolModel school) {
        for (int i = 1; i < 10; i++) {
            var classModel = new ClassModel();
            classModel.setSchool(school);
            classModel.setName(i + ". klass");
            classModel.setNumber(i);
            classRepository.save(classModel);
        }
    }

}
