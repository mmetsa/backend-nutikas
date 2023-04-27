package ee.nutikas.games.api.questionset.dto;

import lombok.Data;

@Data
public class AnswerRequest {
    private String text;
    private Boolean isCorrect;
}
