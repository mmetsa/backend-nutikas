package ee.nutikas.games.engine.memorycards.dto;

import ee.nutikas.games.engine.GameState;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
public class MemoryCardGameState implements GameState<MemoryGamePlayer> {

    private List<MemoryGamePlayer> players;
    private Integer time;

}
