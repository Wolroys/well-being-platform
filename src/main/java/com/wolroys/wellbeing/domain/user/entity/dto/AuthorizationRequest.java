package com.wolroys.wellbeing.domain.user.entity.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthorizationRequest {

    private String email;

    private String password;
}
