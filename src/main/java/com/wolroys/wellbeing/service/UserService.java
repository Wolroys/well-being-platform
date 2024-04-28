package com.wolroys.wellbeing.service;

import com.wolroys.wellbeing.dto.UserDto;
import com.wolroys.wellbeing.dto.UserRequestDto;

import java.util.List;

public interface UserService {

    List<UserDto> getAll();

    UserDto findById(Long id);

    UserDto create(UserRequestDto userRequestDto);

    UserDto deleteById(Long id);

    UserDto edit(Long id, UserRequestDto userDto);
}
