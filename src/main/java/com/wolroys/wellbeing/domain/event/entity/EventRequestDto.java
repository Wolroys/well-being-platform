package com.wolroys.wellbeing.domain.event.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

@Getter
@Setter
@Accessors
public class EventRequestDto {

    private Long id;

    private String title;

    private String description;

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    private Long speakerId;

    private String url;

    private Status status;
}
