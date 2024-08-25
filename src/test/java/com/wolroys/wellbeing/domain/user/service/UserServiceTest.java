package com.wolroys.wellbeing.domain.user.service;

import com.wolroys.wellbeing.domain.confirmationToken.ConfirmationTokenRepository;
import com.wolroys.wellbeing.domain.exception.EntityNotFoundException;
import com.wolroys.wellbeing.domain.exception.UserNotFoundException;
import com.wolroys.wellbeing.domain.user.UserMapper;
import com.wolroys.wellbeing.domain.user.UserRepository;
import com.wolroys.wellbeing.domain.user.entity.*;
import com.wolroys.wellbeing.util.jwt.JwtTokenProvider;
import com.wolroys.wellbeing.util.response.Response;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private UserDetailsService userDetailsService;

    @Mock
    private JwtTokenProvider jwtTokenProvider;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private ConfirmationTokenRepository confirmationTokenRepository;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    void testFindAll() {

        //given
        List<User> users = List.of(new User(), new User(), new User());
        Pageable pageable = PageRequest.of(0, 10, Sort.by("name").ascending());

        Page<User> userPage = PageableExecutionUtils.getPage(users, pageable, users::size);
        when(userRepository.findAll(any(Pageable.class), anyString())).thenReturn(userPage);
        when(userMapper.toDto(any(User.class))).thenReturn(new UserDto());

        //when
        List<UserDto> result = userService.findAll(Pageable.unpaged(), "");

        //then
        assertThat(result).hasSize(3);
        verify(userRepository, times(1)).findAll(any(Pageable.class), anyString());
        verify(userMapper, times(3)).toDto(any(User.class));
    }

    @Test
    void testFindById() {

        //given
        User user = new User();
        user.setId(1L);
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(userMapper.toDto(user)).thenReturn(new UserDto());

        when(userRepository.findById(2L)).thenReturn(Optional.empty());

        //when
        UserDto result = userService.findById(1L);

        //then
        assertThatThrownBy(() -> userService.findById(2L)).isInstanceOf(UserNotFoundException.class);

        assertThat(result).isNotNull();
        verify(userRepository, times(1)).findById(1L);
        verify(userMapper, times(1)).toDto(user);
    }

    @Test
    void testDeleteById() {

        //given
        when(userRepository.findById(anyLong())).thenReturn(Optional.empty());
        when(userRepository.findById(1L)).thenReturn(Optional.of(new User()));

        //when
        userService.deleteById(1L);

        //verify
        assertThatThrownBy(() -> userService.deleteById(2L)).isInstanceOf(EntityNotFoundException.class);
        verify(userRepository, times(1)).deleteById(1L);
    }

    @Test
    void testEdit() {

        //given
        User user = new User();
        user.setId(1L);
        user.setEmail("test@mail.ru");
        user.setName("test");

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(userRepository.save(any(User.class))).thenAnswer(invocationOnMock -> invocationOnMock.getArgument(0));
        when(userMapper.toDto(any(User.class))).thenAnswer(invocationOnMock -> {
            User editedUser = invocationOnMock.getArgument(0);
            UserDto userDto = new UserDto();
            userDto.setName(editedUser.getName());
            userDto.setEmail(editedUser.getEmail());
            userDto.setId(editedUser.getId());
            return userDto;
        });

        //when
        UserRequestDto updatedUser = new UserRequestDto();
        updatedUser.setName("Alex");
        updatedUser.setEmail("test@gmail.com");

        UserDto result = userService.edit(1L, updatedUser);

        //verify
        assertThat(result).isNotNull();
        assertThat(result.getName()).isEqualTo("Alex");
        assertThat(result.getEmail()).isEqualTo("test@gmail.com");

        verify(userRepository, times(1)).findById(1L);
        verify(userRepository, times(1)).save(any(User.class));
        verify(userMapper, times(1)).toDto(any(User.class));
    }

    @Test
    void testLogin() {

        //given
        String email = "test@gmail.com";
        String password = "password";
        AuthorizationRequest request = new AuthorizationRequest();
        request.setEmail(email);
        request.setPassword(password);

        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(email, password);

        Authentication authentication = mock(Authentication.class);

        when(authentication.isAuthenticated()).thenReturn(true);
        when(authenticationManager.authenticate(authenticationToken)).thenReturn(authentication);

        User user = new User();
        user.setId(1L);
        user.setName("test");
        user.setEmail(email);
        user.setActive(true);
        user.setPassword(password);

        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));

        UserDetails userDetails = mock(UserDetails.class);

        when(userDetailsService.loadUserByUsername(email)).thenReturn(userDetails);

        when(jwtTokenProvider.generateToken(userDetails, email)).thenReturn("jwt_token");
        when(userMapper.toDto(user)).thenAnswer(invocationOnMock -> {
            User loginUser = invocationOnMock.getArgument(0);
            UserDto userDto = new UserDto();
            userDto.setId(loginUser.getId());
            userDto.setEmail(loginUser.getEmail());
            userDto.setEmail(loginUser.getEmail());
            return userDto;
        });

        //when
        Response<UserDto> result = userService.login(request);

        assertThat(result.getData()).isNotNull();
        assertThat(result.getToken()).isEqualTo("jwt_token");
        assertThat(result.getData().getEmail()).isEqualTo(email);
        verify(authenticationManager, times(1)).authenticate(authenticationToken);
        verify(authentication, times(1)).isAuthenticated();
        verify(userRepository, times(1)).findByEmail(email);
        verify(userDetailsService, times(1)).loadUserByUsername(email);
        verify(jwtTokenProvider, times(1)).generateToken(userDetails, email);
    }

    @Test
    void testRegister() {

        //given
        UserRequestDto userRequestDto = new UserRequestDto();
        userRequestDto.setName("Alex");
        userRequestDto.setEmail("test@example.com");
        userRequestDto.setPassword("password");

        when(userRepository.save(any(User.class))).thenAnswer(invocationOnMock -> invocationOnMock.getArgument(0));
        when(passwordEncoder.encode(anyString())).thenAnswer(invocationOnMock -> invocationOnMock.getArgument(0));
        when(userMapper.toEntity(any(UserRequestDto.class))).thenAnswer(invocationOnMock -> {
            User user = new User();
            user.setRole(Role.USER);
            user.setEmail(userRequestDto.getEmail());
            user.setName(userRequestDto.getName());
            user.setPassword(userRequestDto.getPassword());
            return user;
        });

        when(userMapper.toDto(any(User.class))).thenAnswer(invocationOnMock -> {
            User registeredUser = invocationOnMock.getArgument(0);
            UserDto userDto = new UserDto();
            userDto.setId(registeredUser.getId());
            userDto.setName(registeredUser.getName());
            userDto.setEmail(registeredUser.getEmail());
            userDto.setRole(Role.USER);
            return userDto;
        });

        UserServiceImpl userServiceSpy = spy(userService);

        //when
        UserDto result = userServiceSpy.register(userRequestDto);

        //then
        assertThat(result).isNotNull();
        assertThat(result.getName()).isEqualTo(userRequestDto.getName());
        assertThat(result.getEmail()).isEqualTo(userRequestDto.getEmail());
        verify(userRepository, times(1)).save(any(User.class));
        verify(userMapper, times(1)).toDto(any(User.class));
    }

    @Test
    void testRegister_whenUserWithEmailAlreadyExists_thenThrowIllegalArgumentException() {

        //given
        UserRequestDto userRequestDto = new UserRequestDto();
        userRequestDto.setName("Alex");
        userRequestDto.setEmail("test@example.com");
        userRequestDto.setPassword("password");

        when(userRepository.existsUserByEmail(userRequestDto.getEmail())).thenReturn(true);

        //then
        assertThatThrownBy(() -> userService.register(userRequestDto))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("This email already taken");
    }

}