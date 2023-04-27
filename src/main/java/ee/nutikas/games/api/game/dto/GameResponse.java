package ee.nutikas.games.api.game.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import ee.nutikas.games.api.enums.GameType;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class GameResponse {
    private Long id;
    private Long classId;
    private String name;
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private LocalDateTime startingTime;
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private LocalDateTime endingTime;
    private Boolean hintsEnabled;
    private Integer maxPlays;
    private Integer pointPenalty;
    private Integer pointsPerQuestion;
    private Boolean powerUpsEnabled;
    private String questionSetName;
    private Boolean streaks;
    private Integer time;
    private GameType type;
    private String code;
}
