package com.wolroys.wellbeing.domain.question.service;

import com.wolroys.wellbeing.domain.question.entity.QuestionDto;
import com.wolroys.wellbeing.domain.question.entity.QuestionRequest;

import java.util.List;

public interface QuestionService {

    List<QuestionDto> findAll(Long eventId);

    QuestionDto findById(Long id);

    QuestionDto create(QuestionRequest request);

    QuestionDto edit(QuestionRequest request);

    void delete(Long id);
}
