package com.wolroys.wellbeing.service;

import com.wolroys.wellbeing.entity.Event;
import com.wolroys.wellbeing.entity.dto.EventDto;

import java.util.List;

public interface EventService {

    List<EventDto> getAll();

    EventDto getById(Long id);

    EventDto create(EventDto eventDto);

    EventDto deleteById(Long id);

    EventDto edit(Long id, EventDto updatedEvent);
}
