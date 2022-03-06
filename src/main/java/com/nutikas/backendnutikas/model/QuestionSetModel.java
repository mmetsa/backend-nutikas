package com.nutikas.backendnutikas.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity(name = "question_set")
@Getter
@Setter
@RequiredArgsConstructor
public class QuestionSetModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String name;

    @ManyToMany(mappedBy = "questionSets", cascade = CascadeType.ALL)
    private List<UserModel> users;

    @ManyToMany(targetEntity = QuestionModel.class, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<QuestionModel> questions;
}
