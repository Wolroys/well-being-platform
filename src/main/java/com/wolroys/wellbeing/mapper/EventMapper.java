package com.wolroys.wellbeing.mapper;

import com.wolroys.wellbeing.entity.Event;
import com.wolroys.wellbeing.entity.dto.EventDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface EventMapper {

    EventMapper INSTANCE = Mappers.getMapper(EventMapper.class);

    Event toEntity(EventDto eventDto);

    EventDto toDto(Event event);
}
