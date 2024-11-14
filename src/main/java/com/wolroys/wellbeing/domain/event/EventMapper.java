package com.wolroys.wellbeing.domain.event;

import com.wolroys.wellbeing.domain.event.entity.Event;
import com.wolroys.wellbeing.domain.event.entity.EventDto;
import com.wolroys.wellbeing.domain.event.entity.EventRequestDto;
import com.wolroys.wellbeing.domain.event.entity.UnauthorizedEventDto;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface EventMapper {

    Event toEntity(EventRequestDto eventRequestDto);

    EventDto toDto(Event event);

    UnauthorizedEventDto toUnauthorizedEventDto(Event event);
}
