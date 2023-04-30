package ee.nutikas.games.api.user.service;

import ee.nutikas.games.api.auth.model.UserModel;
import ee.nutikas.games.api.auth.model.UserRoleSchoolClassModel;
import ee.nutikas.games.api.auth.repository.UserRepository;
import ee.nutikas.games.api.auth.repository.UserRoleRepository;
import ee.nutikas.games.api.enums.Role;
import ee.nutikas.games.api.security.service.UserDetailsImpl;
import ee.nutikas.games.api.user.dto.ApproveUserRequest;
import ee.nutikas.games.api.user.dto.UserResponse;
import ee.nutikas.games.api.user.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;

    public Optional<UserModel> findUserById(Long userId) {
        return userRepository.findById(userId);
    }

    public Integer getClassUserCount(Long classId) {
        return userRoleRepository.findAllByClassModelId(classId).size();
    }

    public List<UserResponse> getUsersForApproval(UserDetailsImpl authUser, Long schoolId) {
        var u = userRepository.findById(authUser.getId());
        if (u.isPresent() && u.get().getUserRoles().stream().noneMatch(r -> r.getSchool().getId().equals(schoolId) && r.getRole().getName().equals(Role.SCHOOL_ADMIN))) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Invalid School ID");
        }
        var users = userRoleRepository.findAllBySchoolIdAndDisabled(schoolId, true).stream()
                .map(UserRoleSchoolClassModel::getUser)
                .toList();
        var response = new ArrayList<UserResponse>();
        users.forEach(us -> {
            var user = UserMapper.INSTANCE.toResponse(us);
            user.setClassName(us.getUserRoles().stream()
                    .filter(ur -> ur.getSchool().getId().equals(schoolId))
                    .findFirst().orElseThrow()
                    .getClassModel().getName());
            response.add(user);
        });
        return response;
    }

    /**
     * Currently approves all User roles
     */
    public void approveUser(UserDetailsImpl authUser, ApproveUserRequest request, Long schoolId) {
        var user = userRepository.findById(authUser.getId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "User not found"));
        var userToApprove = userRepository.findById(request.getUserId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "User not found"));

        var userRoles = user.getUserRoles().stream().filter(r -> r.getSchool().getId().equals(schoolId)).toList();
        var userToApproveRoles = userToApprove.getUserRoles().stream().filter(r -> r.getSchool().getId().equals(schoolId)).toList();

        if (userRoles.stream().anyMatch(r -> Role.SCHOOL_ADMIN.equals(r.getRole().getName()))) {
            // Approve
            userToApproveRoles.forEach(r -> {
                r.setDisabled(false);
                r.getRole().setName(request.getRole());
                r.setExpirationDate(Timestamp.valueOf(LocalDateTime.now().plusYears(1)));
            });
            userRepository.save(userToApprove);
        } else if (request.getClassId() != null) {
            if (
                // API User is TEACHER for the class in request
                userRoles.stream().anyMatch(r -> r.getRole().getName().equals(Role.TEACHER)
                && r.getClassModel().getId().equals(request.getClassId()))
                // Target User is STUDENT for the class in request
                && userToApproveRoles.stream().anyMatch(r -> r.getRole().getName().equals(Role.STUDENT)
                && r.getClassModel().getId().equals(request.getClassId()))) {
                    // Approve the student
                    userToApproveRoles.forEach(r -> {
                        r.setDisabled(false);
                        r.getRole().setName(Role.STUDENT);
                        r.setExpirationDate(Timestamp.valueOf(LocalDateTime.now().plusYears(1)));
                    });
                    userRepository.save(userToApprove);
            }
        } else {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Forbidden");
        }

    }

}
