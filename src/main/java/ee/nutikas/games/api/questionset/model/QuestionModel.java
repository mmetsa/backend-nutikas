package ee.nutikas.games.api.questionset.model;

import ee.nutikas.games.api.enums.AnswerType;
import ee.nutikas.games.api.model.BaseModel;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "question")
@Getter
@Setter
public class QuestionModel extends BaseModel {

    @ManyToOne
    @JoinColumn(name = "question_set_id")
    private QuestionSetModel questionSet;

    @OneToMany(mappedBy = "question", cascade = CascadeType.PERSIST)
    private List<AnswerModel> answers;

    private String text;

    @Enumerated(value = EnumType.STRING)
    private AnswerType answerType;


}
