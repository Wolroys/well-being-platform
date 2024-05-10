package com.wolroys.wellbeing.domain.event.service;

import com.wolroys.wellbeing.domain.event.EventMapper;
import com.wolroys.wellbeing.domain.event.EventRepository;
import com.wolroys.wellbeing.domain.event.entity.Event;
import com.wolroys.wellbeing.domain.event.entity.EventDto;
import com.wolroys.wellbeing.domain.event.entity.EventRequestDto;
import com.wolroys.wellbeing.domain.user.UserMapper;
import com.wolroys.wellbeing.domain.user.UserRepository;
import com.wolroys.wellbeing.domain.user.entity.UserDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
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
    private final UserRepository userRepository;

    private final EventMapper eventMapper;
    private final UserMapper userMapper;

    @Override
    public List<EventDto> findAll(Pageable pageable, String title) {
        return eventRepository.findAll(pageable, title)
                .stream()
                .map(eventMapper::toDto)
                .toList();
    }

    @Override
    public EventDto findById(Long id) {
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
        log.info("Event {} was added", event.getTitle());

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
        boolean isEventEdited = false;

        Event event = eventRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Event with id - {} wasn't found", id);
                    return new IllegalArgumentException("This event doesn't exist");
                });

        if (StringUtils.hasText(updatedEvent.getTitle())) {
            event.setTitle(updatedEvent.getTitle());
            isEventEdited = true;
        }

        if (StringUtils.hasText(updatedEvent.getLink())) {
            event.setLink(updatedEvent.getLink());
            isEventEdited = true;
        }

        if (StringUtils.hasText(updatedEvent.getDescription())) {
            event.setDescription(updatedEvent.getDescription());
            isEventEdited = true;
        }

        if (updatedEvent.getSpeakerId() != null) {
            UserDto speaker = userMapper.toDto(
                    userRepository.findById(updatedEvent.getSpeakerId())
                            .orElseThrow(() -> {
                                log.error("Cannot find speaker with id - {} in DB", updatedEvent.getSpeakerId());
                                return new IllegalArgumentException("This speaker doesn't exist");
                            })
            );
        }

        if (updatedEvent.getStatus() != null && StringUtils.hasText(updatedEvent.getStatus().toString())) {
            event.setStatus(updatedEvent.getStatus());
            isEventEdited = true;
        }

        if (updatedEvent.getDate() != null && StringUtils.hasText(updatedEvent.getDate().toString())) { //TODO проверка что дата должна быть не раньше чем 30 минут после создания
            event.setDate(updatedEvent.getDate());
            isEventEdited = true;
        }

        if (isEventEdited) {
            event = eventRepository.save(event);
            log.info("Event has been edited");
        }


        return eventMapper.toDto(event);
    }


}
