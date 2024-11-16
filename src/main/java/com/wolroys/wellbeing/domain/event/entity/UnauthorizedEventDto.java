package com.wolroys.wellbeing.domain.event.entity;

import com.wolroys.wellbeing.domain.user.entity.dto.UserDto;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class UnauthorizedEventDto {

    private Long id;
    private String title;
    private String description;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private UserDto speaker;
}
