package com.wolroys.wellbeing.domain.event.entity;

import com.wolroys.wellbeing.domain.user.entity.dto.UserDto;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class EventDto {

    private Long id;

    private String title;

    private String description;

    private LocalDateTime startDate;

    private UserDto speaker;

    private String url;

    private Status status;
}
