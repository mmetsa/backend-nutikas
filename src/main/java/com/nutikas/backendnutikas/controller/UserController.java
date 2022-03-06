package com.nutikas.backendnutikas.controller;

import com.nutikas.backendnutikas.dto.UserResponse;
import com.nutikas.backendnutikas.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUserDetails(@PathVariable Long id) {
        var user = userService.getUser(id);
        return ResponseEntity.ok(user);
    }



}
