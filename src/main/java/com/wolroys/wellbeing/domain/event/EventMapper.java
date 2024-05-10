package com.wolroys.wellbeing.domain.event;

import com.wolroys.wellbeing.domain.event.entity.Event;
import com.wolroys.wellbeing.domain.event.entity.EventDto;
import com.wolroys.wellbeing.domain.event.entity.EventRequestDto;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface EventMapper {

    EventMapper INSTANCE = Mappers.getMapper(EventMapper.class);

    Event toEntity(EventRequestDto eventRequestDto);

    EventDto toDto(Event event);
}
