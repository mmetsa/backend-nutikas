package ee.nutikas.games.api.auth.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import ee.nutikas.games.api.auth.dto.JwtResponse;
import ee.nutikas.games.api.auth.dto.LoginRequest;
import ee.nutikas.games.api.auth.dto.RefreshRequest;
import ee.nutikas.games.api.auth.dto.RegisterRequest;
import ee.nutikas.games.api.auth.service.AuthService;
import ee.nutikas.games.api.auth.repository.UserRepository;
import ee.nutikas.games.api.auth.service.LevelSystem;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.List;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final AuthService authService;
    private final JwtUtils jwtUtils;
    private final LevelSystem levelSystem;

    @PublicEndpoint
    @PostMapping("/login")
    public ResponseEntity<JwtResponse> authenticateUser(@RequestBody LoginRequest loginRequest) throws JsonProcessingException {
        var authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getNickname(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String[] tokens = jwtUtils.generateJwtTokens(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList();

        if (!userDetails.isEnabled()) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Account is disabled");
        }
        var currentLevel =
                levelSystem.getLevelForExperience(userDetails.getExperience());

        return ResponseEntity.ok(new JwtResponse(tokens[0], tokens[1],
                roles, userDetails.getCoins(), userDetails.getExperience(),
                currentLevel,
                levelSystem.getExperienceRequiredForNextLevel(currentLevel),
                levelSystem.getExperienceRequiredForNextLevel(currentLevel - 1)));
    }

    @PublicEndpoint
    @PostMapping("/refresh")
    public ResponseEntity<JwtResponse> refreshToken(@RequestBody RefreshRequest refreshRequest) throws JsonProcessingException {
        String username = jwtUtils.getUserNameFromJwtToken(refreshRequest.getRefreshToken());
        UserDetailsImpl userDetails = UserDetailsImpl.build(userRepository.findByNickname(username).orElseThrow());
        var auth = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

        if (jwtUtils.validateJwtToken(refreshRequest.getRefreshToken())) {
            var tokens = jwtUtils.generateJwtTokens(auth);

            var currentLevel =
                    levelSystem.getLevelForExperience(userDetails.getExperience());

            return ResponseEntity.ok(new JwtResponse(tokens[0], tokens[1], userDetails.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .toList(), userDetails.getCoins(),
                    userDetails.getExperience(), currentLevel,
                    levelSystem.getExperienceRequiredForNextLevel(currentLevel), levelSystem.getExperienceRequiredForNextLevel(currentLevel - 1)));
        } else {
            log.info("Invalid refresh token used to refresh JWT");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "INVALID_REFRESH_TOKEN");
        }
    }

    @PublicEndpoint
    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody @Valid RegisterRequest request) {
        authService.registerUser(request);
        return ResponseEntity.ok().build();
    }
}
