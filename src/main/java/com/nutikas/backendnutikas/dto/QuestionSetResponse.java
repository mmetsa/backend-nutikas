package com.nutikas.backendnutikas.dto;

import lombok.Data;

import java.util.List;

@Data
public class QuestionSetResponse {

    private Long id;

    private String name;

    private List<QuestionRequest> questions;

}
