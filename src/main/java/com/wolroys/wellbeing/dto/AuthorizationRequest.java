package com.wolroys.wellbeing.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthorizationRequest {

    private String email;

    private String password;
}
