package com.nutikas.backendnutikas.controller;

import com.nutikas.backendnutikas.dto.QuestionSetRequest;
import com.nutikas.backendnutikas.dto.QuestionSetResponse;
import com.nutikas.backendnutikas.service.QuestionSetService;
import com.nutikas.backendnutikas.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/questionsets")
@RequiredArgsConstructor
public class QuestionSetController {

    private final QuestionSetService questionSetService;

    @PostMapping("/create")
    public ResponseEntity<QuestionSetResponse> createQuestionSet(Authentication authentication,
                                                                 @RequestBody QuestionSetRequest questionSet) {
        var username = authentication.getName();
        var result = questionSetService.createQuestionSet(username, questionSet);
        return ResponseEntity.ok(result);
    }


}
