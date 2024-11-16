package com.wolroys.wellbeing.domain.user.util;

import com.wolroys.wellbeing.domain.user.entity.UserParameter;
import com.wolroys.wellbeing.domain.user.entity.dto.UserParameterDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserParameterMapper {

    UserParameterDto toDto(UserParameter entity);
}
