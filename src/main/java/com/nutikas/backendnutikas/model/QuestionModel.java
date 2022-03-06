package com.nutikas.backendnutikas.model;

import com.nutikas.backendnutikas.enumerator.AnswerType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity(name = "question")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class QuestionModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String question;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AnswerType answerType;

    @ManyToMany(targetEntity = AnswerModel.class, cascade = CascadeType.ALL)
    private List<AnswerModel> answers;
}
