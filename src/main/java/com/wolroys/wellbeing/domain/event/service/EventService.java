package com.wolroys.wellbeing.domain.event.service;

import com.wolroys.wellbeing.domain.event.entity.EventDto;
import com.wolroys.wellbeing.domain.event.entity.EventRequestDto;
import com.wolroys.wellbeing.domain.event.entity.UnauthorizedEventDto;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface EventService {

    List<EventDto> findAll(Pageable pageable, String title);

    EventDto findById(Long id);

    EventDto create(EventRequestDto eventRequestDto);

    EventDto deleteById(Long id);

    EventDto edit(EventRequestDto updatedEvent);

    List<UnauthorizedEventDto> findAllForUnauthorized(Pageable pageable, String title);
}
