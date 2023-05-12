package ee.nutikas.games.engine.websocket;

import ee.nutikas.games.engine.GameEngine;
import ee.nutikas.games.engine.memorycards.dto.GameUpdate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

import java.util.UUID;

@Controller
@RequiredArgsConstructor
@Slf4j
public class GameWebSocketController {

    private final GameEngine gameEngine;

    @MessageMapping("/live/latest")
    public void processGameMessage(Message<String> message, @Header String uuid) {
        log.debug("Message payload: " + message.getPayload());
        gameEngine.publishLatestState(uuid);
    }

    @MessageMapping("/live/state")
    public void processGameUpdate(Message<GameUpdate> message, @Header String uuid) {
        gameEngine.handleGameStateUpdate(uuid, message.getPayload());
    }
}
