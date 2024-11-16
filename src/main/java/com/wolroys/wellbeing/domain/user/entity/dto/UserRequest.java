package com.wolroys.wellbeing.domain.user.entity.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRequest {

    private Long id;

    private String name;

    private String lastName;

    private String email;

    private String password;

    private Double weight;

    private Double height;
}
