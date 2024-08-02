package com.wolroys.wellbeing.domain.question.entity;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QuestionDto {

    private int id;

    private String text;

    private String author;

    private String authorEmail;

    private QuestionStatus status;
}
