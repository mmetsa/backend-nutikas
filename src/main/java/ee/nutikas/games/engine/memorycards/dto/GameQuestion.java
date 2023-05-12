package ee.nutikas.games.engine.memorycards.dto;

import lombok.Data;

import java.util.List;

@Data
public class GameQuestion {

    private Long id;
    private String text;
    private String answerType;
    private Integer pointValue;

    private List<Answer> answers;

}
