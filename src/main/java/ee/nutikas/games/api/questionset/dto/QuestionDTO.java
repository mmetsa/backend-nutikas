package ee.nutikas.games.api.questionset.dto;

import ee.nutikas.games.api.enums.AnswerType;
import lombok.Data;

import java.util.List;

@Data
public class QuestionDTO {

    private Long id;
    private QuestionSetDTO questionSet;
    private String text;
    private AnswerType answerType;
    private List<AnswerDTO> answers;

}
