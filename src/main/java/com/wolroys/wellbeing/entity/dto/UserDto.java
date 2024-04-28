package com.wolroys.wellbeing.entity.dto;

import com.wolroys.wellbeing.entity.Role;
import lombok.Data;

@Data
public class UserDto {

    private Long id;

    private String name;

    private String email;

    private Role role;
}
