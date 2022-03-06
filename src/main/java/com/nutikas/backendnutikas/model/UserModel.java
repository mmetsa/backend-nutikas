package com.nutikas.backendnutikas.model;


import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity(name = "users")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false)
    private String nickName;

    @Column(nullable = false)
    private Long coins;

    @Transient
    private Integer level;

    @Column(nullable = false)
    private Long experience;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @ManyToMany(targetEntity = RoleModel.class, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<RoleModel> roles;

    @ManyToMany(targetEntity = QuestionSetModel.class, cascade = CascadeType.ALL)
    @JoinTable(name = "user_question_set", joinColumns = {@JoinColumn(name = "fk_user")}, inverseJoinColumns = {@JoinColumn(name = "fk_question_set")})
    private List<QuestionSetModel> questionSets;
}
