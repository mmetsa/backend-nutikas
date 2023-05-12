package ee.nutikas.games.engine.memorycards.dto;

import ee.nutikas.games.engine.CreateGameRequest;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CreateMemoryGameRequest implements CreateGameRequest {

    private Integer time;
    private String gameType;
    private List<GameQuestion> questions;
    private List<Long> playerIds;
}
