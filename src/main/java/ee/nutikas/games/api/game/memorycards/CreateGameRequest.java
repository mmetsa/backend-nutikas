package ee.nutikas.games.api.game.memorycards;

import java.util.List;
import java.util.UUID;

public interface CreateGameRequest {

    UUID getId();
    Integer getTime();

    String getGameType();

    List<GameQuestion> getQuestions();

    List<Long> getPlayerIds();
}
