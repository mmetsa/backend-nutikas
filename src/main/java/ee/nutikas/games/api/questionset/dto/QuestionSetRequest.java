package ee.nutikas.games.api.questionset.dto;

import ee.nutikas.games.api.enums.Visibility;
import lombok.Data;

import java.util.List;

@Data
public class QuestionSetRequest {
    private String name;
    private Visibility visibility;
    private List<QuestionRequest> questions;
}
