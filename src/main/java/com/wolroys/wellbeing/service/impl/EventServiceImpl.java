package com.wolroys.wellbeing.service.impl;

import com.wolroys.wellbeing.entity.Event;
import com.wolroys.wellbeing.entity.Status;
import com.wolroys.wellbeing.entity.dto.EventDto;
import com.wolroys.wellbeing.mapper.EventMapper;
import com.wolroys.wellbeing.repository.EventRepository;
import com.wolroys.wellbeing.service.EventService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class EventServiceImpl implements EventService {

    private final EventRepository eventRepository;
    private final EventMapper eventMapper;

    @Override
    public List<EventDto> getAll(){
        return eventRepository.findAllByOrderByDateAsc()
                .stream()
                .map(eventMapper::toDto)
                .toList();
    }

    @Override
    public EventDto getById(Long id) {
        Event event = eventRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("This event doesn't exist"));

        return eventMapper.toDto(event);
    }

    @Override
    @Transactional
    public EventDto create(EventDto eventDto) {
        Event event = eventMapper.toEntity(eventDto);

        eventRepository.save(event);
        log.info("Event - {} with id - {} was created", event.getTitle(), event.getId());
        return eventDto;
    }

    @Override
    @Transactional
    public EventDto deleteById(Long id) {

        Event event = eventRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Event with id - {} doesn't exist", id);
                    return new IllegalArgumentException("This event doesn't exist");
                });

        eventRepository.deleteById(id);
        log.info("Event with id - {} was deleted", id);

        return eventMapper.toDto(event);
    }

    @Override
    @Transactional
    public EventDto edit(Long id, EventDto updatedEvent) {

        boolean isAccountEdited = false;

        Event event = eventRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Event with id - {} doesn't exist", id);
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

        if (StringUtils.hasText(updatedEvent.getStatus())) {
            event.setStatus(Status.valueOf(updatedEvent.getStatus()));
            isAccountEdited = true;
        }

        return eventMapper.toDto(event);
    }
}
