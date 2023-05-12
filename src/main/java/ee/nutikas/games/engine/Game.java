package ee.nutikas.games.engine;

import ee.nutikas.games.engine.memorycards.dto.GameQuestion;

import java.util.List;

public interface Game<T extends GameState<T1>, T1 extends GamePlayer> {

    List<GameQuestion> getQuestions();

    List<T> getGameStates();
}
