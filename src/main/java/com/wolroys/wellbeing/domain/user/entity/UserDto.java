package com.wolroys.wellbeing.domain.user.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDto {

    private Long id;

    private String name;

    private String email;

    private Role role;
}
