package com.wolroys.wellbeing.domain.user.service;

import com.wolroys.wellbeing.domain.user.entity.*;
import com.wolroys.wellbeing.util.response.Response;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserService {

    List<UserDto> findAll(Pageable pageable, String name);

    UserDto findById(Long id);

    UserDto deleteById(Long id);

    UserDto edit(UserRequest userDto);

    Response<UserDto> login(AuthorizationRequest authorizationRequest);

    UserDto register(UserRequest userRequest);

    UserDto confirmRegistrationToken(String token);

    User getAccountFromSecurityContext();

    List<UserDto> findAllSpeakers(String name);

    UserParameterDto setBodyParameters(UserRequest request);
}
