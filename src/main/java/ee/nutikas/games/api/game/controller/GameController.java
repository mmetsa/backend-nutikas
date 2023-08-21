package ee.nutikas.games.api.game.controller;

import ee.nutikas.games.api.game.dto.GameRequest;
import ee.nutikas.games.api.game.dto.GameResponse;
import ee.nutikas.games.api.game.service.GameService;
import ee.nutikas.games.api.security.service.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/game")
public class GameController {

    private final GameService gameService;

    @PostMapping("/create")
    public ResponseEntity<Void> createGame(@AuthenticationPrincipal UserDetailsImpl user, @RequestBody GameRequest request) {
        gameService.createGame(request, user.getId());
        return ResponseEntity.ok().build();
    }

    @GetMapping("/list")
    public ResponseEntity<List<GameResponse>> getTeacherGames(@AuthenticationPrincipal UserDetailsImpl user) {
        return ResponseEntity.ok(gameService.getTeacherGames(user.getId()));
    }

    @GetMapping("/list/student")
    public ResponseEntity<List<GameResponse>> getStudentGames(@AuthenticationPrincipal UserDetailsImpl user) {
        return ResponseEntity.ok(gameService.getStudentGames(user.getId()));
    }

    @PostMapping("/join")
    public ResponseEntity<UUID> joinGameById(@AuthenticationPrincipal UserDetailsImpl user, @RequestParam @NotNull Long id) {
        return ResponseEntity.ok(gameService.joinGameById(user, id));
    }

}
