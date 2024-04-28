package com.wolroys.wellbeing.dto;

import com.wolroys.wellbeing.entity.Status;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class EventDto {

    private Long id;

    private String title;

    private String description;

    private LocalDateTime date;

    private String speaker;

    private String link;

    private Status status;
}
