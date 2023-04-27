package ee.nutikas.games.api.questionset.controller;

import ee.nutikas.games.api.questionset.dto.QuestionSetDTO;
import ee.nutikas.games.api.questionset.dto.QuestionSetRequest;
import ee.nutikas.games.api.security.service.UserDetailsImpl;
import ee.nutikas.games.api.questionset.service.QuestionSetService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/questionset")
public class QuestionSetController {

    private final QuestionSetService questionSetService;

    @GetMapping("/{userId}")
    public ResponseEntity<List<QuestionSetDTO>> getQuestionSets(@AuthenticationPrincipal UserDetailsImpl user,
                                                                @PathVariable Long userId) {
        if (!user.getId().equals(userId)) {
            log.info("Unauthorized request: User {} tried to access user {} question set", user.getId(), userId);
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Unauthorized");
        }
        var questionSets = questionSetService.getQuestionSets(user.getId());
        return ResponseEntity.ok(questionSets);
    }

    @PostMapping("/create")
    public ResponseEntity<Void> createQuestionSet(@AuthenticationPrincipal UserDetailsImpl user,
                                                  @RequestBody QuestionSetRequest questionSetRequest) {
        questionSetService.save(questionSetRequest, user.getId());
        return ResponseEntity.ok().build();
    }

}
