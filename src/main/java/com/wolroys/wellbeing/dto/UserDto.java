package com.wolroys.wellbeing.dto;

import com.wolroys.wellbeing.entity.Role;
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
