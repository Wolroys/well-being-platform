package com.wolroys.wellbeing.domain.user.entity.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserParameterRequest {

    private Long id;
    private Long userId;
    private Double weight;
    private Double height;
}
