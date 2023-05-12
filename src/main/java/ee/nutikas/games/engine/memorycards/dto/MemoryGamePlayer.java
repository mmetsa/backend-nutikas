package ee.nutikas.games.engine.memorycards.dto;

import ee.nutikas.games.engine.GamePlayer;
import lombok.Data;

import java.util.List;

@Data
public class MemoryGamePlayer implements GamePlayer {

    private Long id;
    private Integer score;
    private List<Integer> lastClickedCardIds;
    private List<MemoryCard> cards;
}
