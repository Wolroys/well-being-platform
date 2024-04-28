package com.wolroys.wellbeing.service;

import com.wolroys.wellbeing.dto.EventDto;
import com.wolroys.wellbeing.dto.EventRequestDto;

import java.util.List;

public interface EventService {

    List<EventDto> getAll();

    EventDto getById(Long id);

    EventDto create(EventRequestDto eventRequestDto);

    EventDto deleteById(Long id);

    EventDto edit(Long id, EventRequestDto updatedEvent);
}
