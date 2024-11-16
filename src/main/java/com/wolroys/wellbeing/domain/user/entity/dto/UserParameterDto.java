package com.wolroys.wellbeing.domain.user.entity.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
public class UserParameterDto {

    private Long id;
    private Double weight;
    private Double height;
    private LocalDateTime addedAt;
}
