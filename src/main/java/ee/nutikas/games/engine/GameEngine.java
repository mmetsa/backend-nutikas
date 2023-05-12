package ee.nutikas.games.engine;

import ee.nutikas.games.engine.memorycards.MemoryCardGameHandler;
import ee.nutikas.games.engine.memorycards.dto.GameType;
import ee.nutikas.games.engine.memorycards.dto.GameUpdate;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GameEngine {

    private final MemoryCardGameHandler handler;

    public UUID joinGame(CreateGameRequest request) {
        var gameType = GameType.fromString(request.getGameType());
        if (GameType.MEMORY_MATCH.equals(gameType)) {
            return handler.addGame(request);
        }
        throw new IllegalArgumentException("Invalid Game Type");
    }

    @Scheduled(fixedDelay = 10000)
    private void printGames() {
        System.out.println("Active games");
        handler.getGames().keySet().forEach(key -> System.out.println(key.toString()));
    }

    public void publishLatestState(String uuid) {
        handler.publishLatestState(UUID.fromString(uuid));
    }

    public void handleGameStateUpdate(String uuid, GameUpdate update) {
        handler.handleGameStateUpdate(UUID.fromString(uuid), update);
    }


}
