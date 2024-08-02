package com.wolroys.wellbeing.domain.question.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QuestionRequest {

    private String text;

    private Long eventId;
}
