package com.wolroys.wellbeing.domain.user;


import com.wolroys.wellbeing.domain.user.entity.User;
import com.wolroys.wellbeing.domain.user.entity.UserDto;
import com.wolroys.wellbeing.domain.user.entity.UserRequestDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    UserDto toDto(User user);

    User toEntity(UserRequestDto userRequestDto);
}
