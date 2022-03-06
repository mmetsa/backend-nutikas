package com.nutikas.backendnutikas.dto;

import lombok.Data;

import java.util.List;

@Data
public class UserDTO {

    private Long id;
    private String firstName;
    private String lastName;
    private String nickName;
    private Long coins;
    private Integer level;
    private Long experience;
    private String email;
    private List<RoleResponse> roles;
    private List<QuestionSetDTO> questionSets;

}
