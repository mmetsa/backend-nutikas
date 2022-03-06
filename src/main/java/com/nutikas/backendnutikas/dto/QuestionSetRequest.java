package com.nutikas.backendnutikas.dto;

import lombok.Data;

import java.util.List;

@Data
public class QuestionSetRequest {

    private String name;

    private List<QuestionRequest> questions;

}
