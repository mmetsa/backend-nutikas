package ee.nutikas.games.api.auth.service;

import ee.nutikas.games.api.auth.dto.RegisterRequest;
import ee.nutikas.games.api.auth.model.UserModel;
import ee.nutikas.games.api.auth.model.UserRoleSchoolClassModel;
import ee.nutikas.games.api.auth.repository.RoleRepository;
import ee.nutikas.games.api.auth.repository.UserRepository;
import ee.nutikas.games.api.enums.Role;
import ee.nutikas.games.api.school.model.ClassModel;
import ee.nutikas.games.api.school.repository.ClassRepository;
import ee.nutikas.games.api.school.repository.SchoolRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashSet;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {

    private final UserRepository userRepository;
    private final ClassRepository classRepository;
    private final SchoolRepository schoolRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public void registerUser(RegisterRequest request) {
        registerUser(request, Role.STUDENT);
    }

    public void registerUser(RegisterRequest request, Role role) {

        if (Boolean.TRUE.equals(userRepository.existsByNickname(request.getNickname()))) {
            log.info("Registration with existing username");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Nickname already taken!");
        }

        if (request.getEmail() != null && Boolean.TRUE.equals(userRepository.existsByEmail(request.getEmail()))) {
            log.info("Registration with existing email");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email already taken!");
        }

        // Create new user account
        UserModel userModel = new UserModel();
        userModel.setNickname(request.getNickname());
        userModel.setEmail(request.getEmail());
        userModel.setPasswordHash(passwordEncoder.encode(request.getPassword()));
        userModel.setFirstName(request.getFirstName());
        userModel.setLastName(request.getLastName());
        userModel.setBirthDate(request.getBirthday());

        var roleModel = roleRepository.findByName(role).orElseThrow();
        var schoolModel = schoolRepository.findById(request.getSchoolId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid School ID"));
        ClassModel classModel = null;
        if (request.getClassId() != null) {
            classModel = classRepository.findById(request.getClassId()).orElse(null);
        }

        var userRoleModel = new UserRoleSchoolClassModel();
        userRoleModel.setUser(userModel);
        userRoleModel.setRole(roleModel);
        userRoleModel.setSchool(schoolModel);
        userRoleModel.setClassModel(classModel);
        if (Role.SCHOOL_ADMIN.equals(role)) {
            userRoleModel.setDisabled(false);
        }

        var set = new HashSet<UserRoleSchoolClassModel>();
        set.add(userRoleModel);

        userModel.setUserRoles(set);

        userRepository.save(userModel);
    }

}
