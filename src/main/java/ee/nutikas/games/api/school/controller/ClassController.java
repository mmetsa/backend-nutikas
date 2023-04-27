package ee.nutikas.games.api.school.controller;

import ee.nutikas.games.api.school.dto.ClassResponse;
import ee.nutikas.games.api.school.service.ClassService;
import ee.nutikas.games.api.security.service.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/class")
public class ClassController {

    private final ClassService classService;

    @GetMapping("/list")
    public ResponseEntity<List<ClassResponse>> getUserClasses(@AuthenticationPrincipal UserDetailsImpl user) {
        var classes = classService.getUserClasses(user.getId());
        return ResponseEntity.ok(classes);
    }
}
