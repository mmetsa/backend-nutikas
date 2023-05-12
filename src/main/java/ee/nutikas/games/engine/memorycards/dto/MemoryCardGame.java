package ee.nutikas.games.engine.memorycards.dto;

import ee.nutikas.games.engine.Game;
import lombok.Setter;

import java.util.List;

public class MemoryCardGame implements Game<MemoryCardGameState, MemoryGamePlayer> {

    @Setter
    private List<GameQuestion> questions;

    @Setter
    private List<MemoryCardGameState> gameStates;

    @Override
    public List<GameQuestion> getQuestions() {
        return this.questions;
    }

    @Override
    public List<MemoryCardGameState> getGameStates() {
        return this.gameStates;
    }
}
