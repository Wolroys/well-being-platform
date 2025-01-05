package com.wolroys.wellbeing.domain.event.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

@Getter
@Setter
@Accessors(chain = true)
public class EventRequestDto {

    private Long id;

    private String title;

    private String description;

    private LocalDateTime startDate;

    private Long speakerId;

    private String url;

    private Status status;
}
