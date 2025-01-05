package com.wolroys.wellbeing.domain.event.service;

import com.wolroys.wellbeing.domain.event.EventMapper;
import com.wolroys.wellbeing.domain.event.EventRepository;
import com.wolroys.wellbeing.domain.event.entity.Event;
import com.wolroys.wellbeing.domain.event.entity.EventDto;
import com.wolroys.wellbeing.domain.event.entity.EventRequestDto;
import com.wolroys.wellbeing.domain.event.entity.UnauthorizedEventDto;
import com.wolroys.wellbeing.domain.user.entity.User;
import com.wolroys.wellbeing.domain.user.repository.UserRepository;
import com.wolroys.wellbeing.util.exception.EventNotFoundException;
import com.wolroys.wellbeing.util.exception.IncorrectDateException;
import com.wolroys.wellbeing.util.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class EventServiceImpl implements EventService {

    private final EventRepository eventRepository;
    private final UserRepository userRepository;

    private final EventMapper eventMapper;

    @Override
    public List<EventDto> findAll(Pageable pageable, String title) {
        return eventRepository.findAll(pageable, title)
                .stream()
                .map(eventMapper::toDto)
                .toList();
    }

    @Override
    public EventDto findById(Long id) {
        Event event = getEvent(id);

        return eventMapper.toDto(event);
    }

    @Override
    @Transactional
    public EventDto create(EventRequestDto request) {
        Event event = new Event();

        setGeneralFields(request, event);

        event = eventRepository.save(event);

        log.info("Event {} - {} was added", event.getTitle(), event.getId());

        return eventMapper.toDto(event);
    }

    @Override
    @Transactional
    public EventDto deleteById(Long id) {
        Event event = getEvent(id);

        eventRepository.deleteById(id);
        log.info("Event with id - {} was deleted", id);

        return eventMapper.toDto(event);
    }

    @Override
    @Transactional
    public EventDto edit(EventRequestDto request) {

        if (request.getId() == null) {
            throw new IllegalArgumentException("The request id is mandatory");
        }

        Event event = getEvent(request.getId());

        setGeneralFields(request, event);

        return eventMapper.toDto(event);
    }

    @Override
    public List<UnauthorizedEventDto> findAllForUnauthorized(Pageable pageable, String title) {
        return eventRepository.findAll(pageable, title)
                .map(eventMapper::toUnauthorizedEventDto)
                .toList();
    }

    private void setGeneralFields(EventRequestDto request, Event event) {
        if (request.getStartDate().isBefore(LocalDateTime.now())) {
            throw new IncorrectDateException("Start date cannot be before today date");
        }

        if (StringUtils.hasText(request.getTitle())) {
            event.setTitle(request.getTitle());
        }

        if (StringUtils.hasText(request.getDescription())) {
            event.setDescription(request.getDescription());
        }

        if (request.getSpeakerId() != null) {
            User speaker = userRepository.findById(request.getSpeakerId())
                    .orElseThrow(() -> {
                        log.error("Cannot find speaker with id - {} in DB", request.getSpeakerId());
                        return new UserNotFoundException("This speaker doesn't exist");
                    });

            event.setSpeaker(speaker);
        }

        if (StringUtils.hasText(request.getUrl()))
            event.setUrl(request.getUrl());

        if (request.getStatus() != null) {
            event.setStatus(request.getStatus());
        }

        event.setStartDate(request.getStartDate());

    }

    private Event getEvent(Long id) {
        return eventRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Event with id - {} wasn't found", id);
                    return new EventNotFoundException("This event doesn't exist");
                });
    }
}
