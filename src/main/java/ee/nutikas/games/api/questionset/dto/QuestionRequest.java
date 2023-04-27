package ee.nutikas.games.api.questionset.dto;

import ee.nutikas.games.api.enums.AnswerType;
import lombok.Data;

import java.util.List;

@Data
public class QuestionRequest {
    private String questionText;
    private AnswerType answerType;
    private List<AnswerRequest> answers;
}
