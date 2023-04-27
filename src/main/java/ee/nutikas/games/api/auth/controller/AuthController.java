package ee.nutikas.games.api.auth.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import ee.nutikas.games.api.auth.dto.JwtResponse;
import ee.nutikas.games.api.auth.dto.LoginRequest;
import ee.nutikas.games.api.auth.dto.RefreshRequest;
import ee.nutikas.games.api.auth.dto.RegisterRequest;
import ee.nutikas.games.api.auth.model.UserRoleSchoolClassModel;
import ee.nutikas.games.api.enums.Role;
import ee.nutikas.games.api.auth.model.UserModel;
import ee.nutikas.games.api.auth.repository.RoleRepository;
import ee.nutikas.games.api.auth.repository.UserRepository;
import ee.nutikas.games.api.school.repository.ClassRepository;
import ee.nutikas.games.api.school.repository.SchoolRepository;
import ee.nutikas.games.api.security.annotation.PublicEndpoint;
import ee.nutikas.games.api.security.jwt.JwtUtils;
import ee.nutikas.games.api.security.service.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashSet;
import java.util.List;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final SchoolRepository schoolRepository;
    private final ClassRepository classRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;

    @PublicEndpoint
    @PostMapping("/login")
    public ResponseEntity<JwtResponse> authenticateUser(@RequestBody LoginRequest loginRequest) throws JsonProcessingException {
        var authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getNickname(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String[] tokens = jwtUtils.generateJwtTokens(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList();

        return ResponseEntity.ok(new JwtResponse(tokens[0], tokens[1], roles));
    }

    @PublicEndpoint
    @PostMapping("/refresh")
    public ResponseEntity<JwtResponse> refreshToken(@RequestBody RefreshRequest refreshRequest) throws JsonProcessingException {
        String username = jwtUtils.getUserNameFromJwtToken(refreshRequest.getRefreshToken());
        UserDetailsImpl userDetails = UserDetailsImpl.build(userRepository.findByNickname(username).orElseThrow());
        var auth = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

        if (jwtUtils.validateJwtToken(refreshRequest.getRefreshToken())) {
            var tokens = jwtUtils.generateJwtTokens(auth);
            return ResponseEntity.ok(new JwtResponse(tokens[0], tokens[1], userDetails.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .toList()));
        } else {
            log.info("Invalid refresh token used to refresh JWT");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "INVALID_REFRESH_TOKEN");
        }
    }

    @PublicEndpoint
    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody RegisterRequest request) {

        if (Boolean.TRUE.equals(userRepository.existsByNickname(request.getNickname()))) {
            log.info("Registration with existing username");
            return ResponseEntity.badRequest().body("Error: Username is already taken!");
        }

        if (request.getEmail() != null && Boolean.TRUE.equals(userRepository.existsByEmail(request.getEmail()))) {
            log.info("Registration with existing email");
            return ResponseEntity.badRequest().body("Error: Email is already taken!");
        }

        // Create new user account
        UserModel userModel = new UserModel();
        userModel.setNickname(request.getNickname());
        userModel.setEmail(request.getEmail());
        userModel.setPasswordHash(passwordEncoder.encode(request.getPassword()));
        userModel.setFirstName(request.getFirstName());
        userModel.setLastName(request.getLastName());
        userModel.setBirthDate(request.getBirthday());

        var roleModel = roleRepository.findByName(Role.STUDENT).orElseThrow();
        var schoolModel = schoolRepository.findById(request.getSchoolId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid School ID"));
        var classModel = classRepository.findById(request.getClassId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid Class ID"));

        var userRoleModel = new UserRoleSchoolClassModel();
        userRoleModel.setUser(userModel);
        userRoleModel.setRole(roleModel);
        userRoleModel.setSchool(schoolModel);
        userRoleModel.setClassModel(classModel);

        var set = new HashSet<UserRoleSchoolClassModel>();
        set.add(userRoleModel);

        userModel.setUserRoles(set);

        userRepository.save(userModel);
        return ResponseEntity.ok("Register successful");
    }
}
