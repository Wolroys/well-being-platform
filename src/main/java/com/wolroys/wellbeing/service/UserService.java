package com.wolroys.wellbeing.service;

import com.wolroys.wellbeing.dto.AuthorizationRequest;
import com.wolroys.wellbeing.dto.UserDto;
import com.wolroys.wellbeing.dto.UserRequestDto;
import com.wolroys.wellbeing.util.response.Response;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserService {

    List<UserDto> findAll(Pageable pageable, String name);

    UserDto findById(Long id);

    UserDto deleteById(Long id);

    UserDto edit(Long id, UserRequestDto userDto);

    Response<UserDto> login(AuthorizationRequest authorizationRequest);

    UserDto register(UserRequestDto userRequestDto);
}
