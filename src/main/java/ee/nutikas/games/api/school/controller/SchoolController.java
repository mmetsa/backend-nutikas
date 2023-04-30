package ee.nutikas.games.api.school.controller;

import ee.nutikas.games.api.school.dto.SchoolRequest;
import ee.nutikas.games.api.school.dto.SchoolResponse;
import ee.nutikas.games.api.school.service.SchoolService;
import ee.nutikas.games.api.security.annotation.PublicEndpoint;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/school")
public class SchoolController {

    private final SchoolService service;

    @PublicEndpoint
    @PostMapping("/create")
    public ResponseEntity<SchoolResponse> createSchool(@RequestBody @Valid SchoolRequest schoolRequest) {
        service.createSchool(schoolRequest);
        return ResponseEntity.ok().build();
    }

    @PublicEndpoint
    @GetMapping("/list")
    public ResponseEntity<List<SchoolResponse>> getList() {
        var response = service.getList();
        return ResponseEntity.ok(response);
    }

    @PostMapping("/codes")
    public ResponseEntity<List<String>> createSchoolCodes(@RequestParam Integer amount) {
        return ResponseEntity.ok(service.generateSchoolCodes(amount));
    }

    @PublicEndpoint
    @PostMapping("/code/validate")
    public ResponseEntity<Void> validateCode(@RequestParam String code) {
        service.validateCode(code);
        return ResponseEntity.ok().build();
    }
}
