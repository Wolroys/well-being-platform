package com.wolroys.wellbeing.domain.question.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QuestionRequest {

    private Long id;

    private String text;

    private Long eventId;

    private QuestionStatus status;
}
