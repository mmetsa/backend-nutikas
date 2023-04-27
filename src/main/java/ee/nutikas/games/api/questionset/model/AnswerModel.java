package ee.nutikas.games.api.questionset.model;

import ee.nutikas.games.api.model.BaseModel;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "answer")
@Getter
@Setter
public class AnswerModel extends BaseModel {

    @Column(name = "value")
    private String value;

    @Column(name = "is_correct")
    private Boolean isCorrect;

    @ManyToOne
    @JoinColumn(name = "question_id")
    private QuestionModel question;
}
