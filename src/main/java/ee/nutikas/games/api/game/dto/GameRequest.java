package ee.nutikas.games.api.game.dto;

import ee.nutikas.games.api.enums.GameType;
import lombok.Data;

import java.time.LocalDateTime;
@Data
public class GameRequest {

    private Long classId;
    private String name;
    private LocalDateTime startingTime;
    private LocalDateTime endingTime;
    private Boolean hintsEnabled;
    private Integer maxPlays;
    private Integer pointPenalty;
    private Integer pointsPerQuestion;
    private Boolean powerUpsEnabled;
    private Long questionSetId;
    private Boolean streaks;
    private Integer time;
    private GameType type;
}
