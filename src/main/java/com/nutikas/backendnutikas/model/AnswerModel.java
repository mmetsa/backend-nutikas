package com.nutikas.backendnutikas.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity(name = "answer")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AnswerModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String value;

    private Boolean isCorrect;
}
