package ee.nutikas.games.api.school.controller;

import ee.nutikas.games.api.school.dto.SchoolRequest;
import ee.nutikas.games.api.school.dto.SchoolResponse;
import ee.nutikas.games.api.school.service.SchoolService;
import ee.nutikas.games.api.security.annotation.PublicEndpoint;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/school")
public class SchoolController {

    private final SchoolService service;

    @PostMapping("/create")
    public ResponseEntity<SchoolResponse> createSchool(@RequestBody SchoolRequest schoolRequest) {
        var response = service.createSchool(schoolRequest);
        return ResponseEntity.ok(response);
    }

    @PublicEndpoint
    @GetMapping("/list")
    public ResponseEntity<List<SchoolResponse>> getList() {
        var response = service.getList();
        return ResponseEntity.ok(response);
    }
}
