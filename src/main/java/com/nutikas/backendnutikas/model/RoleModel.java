package com.nutikas.backendnutikas.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
@Entity(name = "role")
@Getter
@Setter
@RequiredArgsConstructor
public class RoleModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

}
