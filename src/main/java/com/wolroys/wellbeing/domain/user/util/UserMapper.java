package com.wolroys.wellbeing.domain.user.util;


import com.wolroys.wellbeing.domain.user.entity.User;
import com.wolroys.wellbeing.domain.user.entity.dto.UserDto;
import com.wolroys.wellbeing.domain.user.entity.dto.UserRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDto toDto(User user);

    User toEntity(UserRequest userRequest);
}
