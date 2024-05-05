package com.wolroys.wellbeing.service;

import com.wolroys.wellbeing.dto.EventDto;
import com.wolroys.wellbeing.dto.EventRequestDto;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface EventService {

    List<EventDto> getAll(Pageable pageable);

    EventDto getById(Long id);

    EventDto create(EventRequestDto eventRequestDto);

    EventDto deleteById(Long id);

    EventDto edit(Long id, EventRequestDto updatedEvent);
}
