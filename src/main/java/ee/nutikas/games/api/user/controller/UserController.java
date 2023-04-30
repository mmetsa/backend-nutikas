package ee.nutikas.games.api.user.controller;

import ee.nutikas.games.api.security.service.UserDetailsImpl;
import ee.nutikas.games.api.user.dto.ApproveUserRequest;
import ee.nutikas.games.api.user.dto.UserResponse;
import ee.nutikas.games.api.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    @GetMapping("/approvals")
    public ResponseEntity<List<UserResponse>> getUsersForApproval(@AuthenticationPrincipal UserDetailsImpl user, @RequestParam @NotNull Long schoolId) {
        return ResponseEntity.ok(userService.getUsersForApproval(user, schoolId));
    }

    @PostMapping("/approve")
    public ResponseEntity<Void> approveUser(@AuthenticationPrincipal UserDetailsImpl user,
                                            @RequestParam @NotNull Long schoolId,
                                            @RequestBody @Valid ApproveUserRequest request) {
        userService.approveUser(user, request, schoolId);
        return ResponseEntity.ok().build();
    }
}
