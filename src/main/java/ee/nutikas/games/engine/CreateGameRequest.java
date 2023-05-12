package ee.nutikas.games.engine;

import ee.nutikas.games.engine.memorycards.dto.GameQuestion;

import java.util.List;

public interface CreateGameRequest {

    Integer getTime();

    String getGameType();

    List<GameQuestion> getQuestions();

    List<Long> getPlayerIds();
}
