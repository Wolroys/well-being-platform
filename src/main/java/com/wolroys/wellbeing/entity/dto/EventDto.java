package com.wolroys.wellbeing.entity.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class EventDto {

    private String title;

    private String description;

    private LocalDateTime date;

    private String speaker;

    private String link;

    private String status;
}
