package ee.nutikas.games.api.questionset.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import ee.nutikas.games.api.enums.Visibility;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class QuestionSetDTO {

    private Long id;
    private Long ownerId;
    private String name;
    private List<QuestionDTO> questions;
    // Amount of questions
    private Integer amount;
    // Creation date for the question set
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private LocalDateTime date;
    private Visibility visibility;

}
