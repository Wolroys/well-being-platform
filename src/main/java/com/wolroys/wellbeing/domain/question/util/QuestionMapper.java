package com.wolroys.wellbeing.domain.question.util;

import com.wolroys.wellbeing.domain.question.entity.Question;
import com.wolroys.wellbeing.domain.question.entity.QuestionDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface QuestionMapper {

    @Mapping(source = "java(user.getFullName())", target = "author")
    @Mapping(source = "user.email", target = "authorEmail")
    QuestionDto toDto(Question question);
}
