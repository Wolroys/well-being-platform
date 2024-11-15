package com.wolroys.wellbeing.domain.user.util;

import com.wolroys.wellbeing.domain.user.entity.UserParameter;
import com.wolroys.wellbeing.domain.user.entity.UserParameterDto;
import org.mapstruct.Mapper;

@Mapper
public interface UserParameterMapper {

    UserParameterDto toDto(UserParameter entity);
}
