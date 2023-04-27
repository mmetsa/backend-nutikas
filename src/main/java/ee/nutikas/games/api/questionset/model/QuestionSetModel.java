package ee.nutikas.games.api.questionset.model;

import ee.nutikas.games.api.enums.Visibility;
import ee.nutikas.games.api.model.BaseModel;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "question_set")
@Getter
@Setter
public class QuestionSetModel extends BaseModel {

    private Long ownerId;

    @Enumerated(EnumType.STRING)
    private Visibility visibility;

    private String name;

    @OneToMany(mappedBy = "questionSet", cascade = CascadeType.PERSIST)
    private List<QuestionModel> questions;

}
