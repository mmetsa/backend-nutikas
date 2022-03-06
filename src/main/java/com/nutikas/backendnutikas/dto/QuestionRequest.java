package com.nutikas.backendnutikas.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class QuestionRequest {

    private String question;

    private String answerType;

    private List<AnswerRequest> answers;

}
