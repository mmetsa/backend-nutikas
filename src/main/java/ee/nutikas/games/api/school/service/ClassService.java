package ee.nutikas.games.api.school.service;

import ee.nutikas.games.api.auth.model.UserRoleSchoolClassModel;
import ee.nutikas.games.api.enums.Role;
import ee.nutikas.games.api.school.dto.ClassResponse;
import ee.nutikas.games.api.school.mapper.ClassMapper;
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

    public List<ClassResponse> getUserClasses(Long userId) {
        var user = userService.findUserById(userId).orElseThrow();

        var classes = user.getUserRoles().stream()
                .filter(cm -> Role.TEACHER.equals(cm.getRole().getName()))
                .map(UserRoleSchoolClassModel::getClassModel)
                .toList();
        return ClassMapper.INSTANCE.toResponseList(classes);
    }

}
