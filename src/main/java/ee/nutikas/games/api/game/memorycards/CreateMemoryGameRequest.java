package ee.nutikas.games.api.game.memorycards;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class CreateMemoryGameRequest implements CreateGameRequest {

    private UUID id;
    private Integer time;
    private String gameType;
    private List<GameQuestion> questions;
    private List<Long> playerIds;

}
