package ee.nutikas.games.api.questionset.dto;

import lombok.Data;

@Data
public class AnswerDTO {
    private Long id;
    private String text;
    private Boolean isCorrect;

}
