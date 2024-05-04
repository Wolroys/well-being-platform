package com.wolroys.wellbeing.service.impl;

import com.wolroys.wellbeing.dto.AuthorizationRequest;
import com.wolroys.wellbeing.dto.UserDto;
import com.wolroys.wellbeing.dto.UserRequestDto;
import com.wolroys.wellbeing.entity.Role;
import com.wolroys.wellbeing.entity.User;
import com.wolroys.wellbeing.repository.UserRepository;
import com.wolroys.wellbeing.service.UserService;
import com.wolroys.wellbeing.util.jwt.JwtTokenProvider;
import com.wolroys.wellbeing.util.mapper.UserMapper;
import com.wolroys.wellbeing.util.response.Response;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public List<UserDto> getAll() {
        return userRepository.findAll()
                .stream()
                .map(userMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public UserDto findById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("User with id - {} wasn't found", id);
                    return new IllegalArgumentException("User doesn't exist");
                });

        return userMapper.toDto(user);
    }

    @Override
    @Transactional
    public UserDto deleteById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("User with id - {} wasn't found", id);
                    return new IllegalArgumentException("This user doesn't exist");
                });

        userRepository.deleteById(id);
        log.info("User with id - {} was deleted", id);

        return userMapper.toDto(user);
    }

    @Override
    @Transactional
    public UserDto edit(Long id, UserRequestDto updatedUser) {
        boolean isUserEdited = false;

        User user = userRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Event with id - {} wasn't found", id);
                    return new IllegalArgumentException("This event doesn't exist");
                });

        if (StringUtils.hasText(updatedUser.getName())) {
            user.setName(updatedUser.getName());
            isUserEdited = true;
        }

        if (StringUtils.hasText(updatedUser.getEmail())) {
            user.setEmail(updatedUser.getEmail());
            isUserEdited = true;
        }


        if (isUserEdited) {
            user = userRepository.save(user);
            log.info("Event has been edited");
        }


        return userMapper.toDto(user);
    }

    @Override
    public Response<UserDto> login(AuthorizationRequest request) {

        String email = request.getEmail().toLowerCase(Locale.ROOT);
        Authentication authenticate;
        String token = null;
        try {
            authenticate =
                    authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, request.getPassword()));

        } catch (AuthenticationException e) {
            log.error(e.getMessage());
            throw new AccessDeniedException("Wrong email or password");
        }

        if (authenticate.isAuthenticated()) {
            UserDetails userDetails = userDetailsService.loadUserByUsername(email);
            token = jwtTokenProvider.generateToken(userDetails, email);
            log.info("User with email {} logged in successfully", email);
        }

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Email wasn't found"));

        return new Response<UserDto>().login(token, userMapper.toDto(user));
    }

    @Override
    @Transactional
    public UserDto register(UserRequestDto userRequestDto) {
        User user = userMapper.toEntity(userRequestDto);

        user.setRole(Role.USER);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        log.info("User {} was added", user.getName());

        return userMapper.toDto(user);
    }
}
