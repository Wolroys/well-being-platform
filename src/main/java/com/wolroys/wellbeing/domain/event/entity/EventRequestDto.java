package com.wolroys.wellbeing.domain.event.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

@Getter
@Setter
@Accessors
public class EventRequestDto {

    private String title;

    private String description;

    private LocalDateTime date;

    private Long speakerId;

    private String link;

    private Status status;
}
