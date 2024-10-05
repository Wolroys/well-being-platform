package com.wolroys.wellbeing.domain.question.service;

import com.wolroys.wellbeing.domain.event.EventRepository;
import com.wolroys.wellbeing.domain.event.entity.Event;
import com.wolroys.wellbeing.domain.question.entity.Question;
import com.wolroys.wellbeing.domain.question.entity.QuestionDto;
import com.wolroys.wellbeing.domain.question.entity.QuestionRequest;
import com.wolroys.wellbeing.domain.question.entity.QuestionStatus;
import com.wolroys.wellbeing.domain.question.repository.QuestionRepository;
import com.wolroys.wellbeing.domain.question.util.QuestionMapper;
import com.wolroys.wellbeing.domain.user.entity.User;
import com.wolroys.wellbeing.domain.user.service.UserService;
import com.wolroys.wellbeing.util.exception.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class QuestionServiceImpl implements QuestionService {

    private final QuestionRepository questionRepository;
    private final EventRepository eventRepository;

    private final UserService userService;

    private final QuestionMapper questionMapper;

    @Override
    public List<QuestionDto> findAll(Long eventId) {
        return questionRepository.findAllByEventId(eventId)
                .stream()
                .map(questionMapper::toDto)
                .toList();
    }

    @Override
    public QuestionDto findById(Long id) {
        return questionRepository
                .findById(id)
                .map(questionMapper::toDto)
                .orElseThrow(() -> {
                    log.error("Question with id {} not found", id);
                    return new IllegalArgumentException("Question with id " + id + " not found");
                });
    }

    @Override
    @Transactional
    public QuestionDto create(QuestionRequest request) {
        Event event = eventRepository.findById(request.getEventId())
                .orElseThrow(() -> {
                    log.error("Event with id {} not found", request.getEventId());
                    return new IllegalArgumentException("Event with id " + request.getEventId() + " not found");
                });

        if (!StringUtils.hasText(request.getText())) {
            log.error("The comment should not be empty");
            throw new IllegalArgumentException("The comment should not be empty");
        }

        User currentUser = userService.getAccountFromSecurityContext();

        Question question = new Question();

        question.setEvent(event);
        question.setText(request.getText());
        question.setStatus(QuestionStatus.OPEN);

        question.setUser(currentUser);

        Question createdQuestion = questionRepository.save(question);

        return questionMapper.toDto(createdQuestion);
    }

    @Override
    @Transactional
    public QuestionDto edit(QuestionRequest request) {

        Question existQuestion = questionRepository.findById(request.getId())
                .orElseThrow(() -> {
                    log.error("Question with id - {} not found", request.getId());
                    return new EntityNotFoundException("Question with id " + request.getId() + " not found");
                });

        if (StringUtils.hasText(request.getText())) {
            existQuestion.setText(request.getText());
        }

        if (request.getStatus() != null) {
            existQuestion.setStatus(request.getStatus());
        }

        Question updatedQuestion = questionRepository.save(existQuestion);

        return questionMapper.toDto(updatedQuestion);
    }

    @Override
    @Transactional
    public QuestionDto delete(Long id) {
        Question question = questionRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Question with id {} not found", id);
                    return new EntityNotFoundException("Question with id " + id + " not found");
                });

        questionRepository.deleteById(id);

        return questionMapper.toDto(question);
    }
}
