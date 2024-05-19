package com.wolroys.wellbeing.domain.user.entity;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRequestDto {

    @NotEmpty(message = "The name must not be empty")
    private String name;

    @NotEmpty(message = "The last name must not be empty")
    private String lastName;

    @NotEmpty(message = "The email must not be empty")
    private String email;

    @NotEmpty(message = "The password must not be empty")
    private String password;
}
