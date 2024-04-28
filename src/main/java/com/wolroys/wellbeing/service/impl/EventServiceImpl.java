package com.wolroys.wellbeing.service.impl;

import com.wolroys.wellbeing.util.mapper.EventMapper;
import com.wolroys.wellbeing.dto.EventRequestDto;
import com.wolroys.wellbeing.dto.EventDto;
import com.wolroys.wellbeing.entity.Event;
import com.wolroys.wellbeing.repository.EventRepository;
import com.wolroys.wellbeing.service.EventService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class EventServiceImpl implements EventService {

    private final EventRepository eventRepository;
    private final EventMapper eventMapper;

    @Override
    public List<EventDto> getAll() {
        return eventRepository.findAllByOrderByDateAsc()
                .stream()
                .map(eventMapper::toDto)
                .toList();
    }

    @Override
    public EventDto getById(Long id) {
        Event event = eventRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Event with id - {} wasn't found", id);
                    return new IllegalArgumentException("This event doesn't exist");
                });

        return eventMapper.toDto(event);
    }

    @Override
    @Transactional
    public EventDto create(EventRequestDto eventRequest) {
        Event event = eventMapper.toEntity(eventRequest);

        eventRepository.save(event);

        return eventMapper.toDto(event);
    }

    @Override
    @Transactional
    public EventDto deleteById(Long id) {
        Event event = eventRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Event with id - {} wasn't found", id);
                    return new IllegalArgumentException("This event doesn't exist");
        });

        eventRepository.deleteById(id);
        log.info("Event with id - {} was deleted", id);

        return eventMapper.toDto(event);
    }

    @Override
    @Transactional
    public EventDto edit(Long id, EventRequestDto updatedEvent) {
        boolean isAccountEdited = false;

        Event event = eventRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Event with id - {} wasn't found", id);
                    return new IllegalArgumentException("This event doesn't exist");
                });

        if (StringUtils.hasText(updatedEvent.getTitle())) {
            event.setTitle(updatedEvent.getTitle());
            isAccountEdited = true;
        }

        if (StringUtils.hasText(updatedEvent.getLink())) {
            event.setLink(updatedEvent.getLink());
            isAccountEdited = true;
        }

        if (StringUtils.hasText(updatedEvent.getDescription())) {
            event.setDescription(updatedEvent.getDescription());
            isAccountEdited = true;
        }

        if (StringUtils.hasText(updatedEvent.getSpeaker())) {
            event.setSpeaker(updatedEvent.getSpeaker());
            isAccountEdited = true;
        }

        if (updatedEvent.getStatus() != null && StringUtils.hasText(updatedEvent.getStatus().toString())) {
            event.setStatus(updatedEvent.getStatus());
            isAccountEdited = true;
        }

        if (updatedEvent.getDate() != null && StringUtils.hasText(updatedEvent.getDate().toString())) { //TODO проверка что дата должна быть не раньше текущего времени
            event.setDate(updatedEvent.getDate());
            isAccountEdited = true;
        }

        if (isAccountEdited) {
            event = eventRepository.save(event);
            log.info("Event has been edited");
        }


        return eventMapper.toDto(event);
    }


}
