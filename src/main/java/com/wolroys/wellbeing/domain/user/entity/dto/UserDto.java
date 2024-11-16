package com.wolroys.wellbeing.domain.user.entity.dto;

import com.wolroys.wellbeing.domain.user.entity.Role;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDto {

    private Long id;

    private String name;

    private String lastName;

    private String email;

    private Role role;
}
