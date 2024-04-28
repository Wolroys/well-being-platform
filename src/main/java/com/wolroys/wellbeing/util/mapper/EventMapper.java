package com.wolroys.wellbeing.util.mapper;

import com.wolroys.wellbeing.dto.EventDto;
import com.wolroys.wellbeing.dto.EventRequestDto;
import com.wolroys.wellbeing.entity.Event;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface EventMapper {

    EventMapper INSTANCE = Mappers.getMapper(EventMapper.class);

    Event toEntity(EventRequestDto eventRequestDto);

    EventDto toDto(Event event);
}
