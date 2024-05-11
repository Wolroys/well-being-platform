package com.wolroys.wellbeing.domain.user.service;

import com.wolroys.wellbeing.domain.confirmationToken.ConfirmationToken;
import com.wolroys.wellbeing.domain.confirmationToken.ConfirmationTokenRepository;
import com.wolroys.wellbeing.domain.exception.UserNotFoundException;
import com.wolroys.wellbeing.domain.user.UserMapper;
import com.wolroys.wellbeing.domain.user.UserRepository;
import com.wolroys.wellbeing.domain.user.entity.*;
import com.wolroys.wellbeing.util.jwt.JwtTokenProvider;
import com.wolroys.wellbeing.util.mail.EmailService;
import com.wolroys.wellbeing.util.response.Response;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.mail.javamail.JavaMailSender;
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
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.io.File;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ConfirmationTokenRepository confirmationTokenRepository;

    private final UserDetailsService userDetailsService;
    private final EmailService emailService;

    private final UserMapper userMapper;

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final TemplateEngine templateEngine;
    private final PasswordEncoder passwordEncoder;
    private final JavaMailSender javaMailSender;

    @Value("${server.location}")
    private String serverLocation;

    @Override
    public List<UserDto> findAll(Pageable pageable, String name) {
        return userRepository.findAll(pageable, name)
                .stream()
                .map(userMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public UserDto findById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("User with id - {} wasn't found", id);
                    return new UserNotFoundException("User doesn't exist");
                });

        return userMapper.toDto(user);
    }

    @Override
    @Transactional
    public UserDto deleteById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("User with id - {} wasn't found", id);
                    return new UserNotFoundException("This user doesn't exist");
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

        String email = request.getEmail();
        Authentication authenticate;
        String token = null;
        try {
            authenticate =
                    authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, request.getPassword()));

        } catch (AuthenticationException e) {
            log.error(e.getMessage());
            throw new AccessDeniedException("Wrong email or password");
        }

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Email wasn't found"));

        if (!user.isActive()) {
            log.error("User account with email {} not activated", user.getEmail());
            throw new AccessDeniedException("account not activated");
        }

        if (authenticate.isAuthenticated()) {
            UserDetails userDetails = userDetailsService.loadUserByUsername(email);
            token = jwtTokenProvider.generateToken(userDetails, email);
            log.info("User with email {} logged in successfully", email);
        }


        return new Response<UserDto>().login(token, userMapper.toDto(user));
    }

    @Override
    @Transactional
    public UserDto register(UserRequestDto userRequestDto) {
        User user = userMapper.toEntity(userRequestDto);

        user.setRole(Role.USER);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        log.info("User {} was registered", user.getEmail());

        sendEmailConfirmationTokenToEmail(user);

        return userMapper.toDto(user);
    }

    @Override
    public void sendEmailConfirmationTokenToEmail(User user) {
        createAndSendEmailConfirmationToken(user);
    }

    @Override
    @Transactional
    public UserDto confirmRegistrationToken(String token) {

        ConfirmationToken confirmationToken = confirmationTokenRepository.findByToken(token);

        if (confirmationToken == null) {
            log.error("Not found token {} in database", token);
            throw new IllegalArgumentException("Entered token not found in DB. Token has errors or has been confirmed");
        }

        User user = confirmationToken.getUser();
        user.setActive(true);

        userRepository.save(user);
        log.info("User with email {} confirmed", user.getEmail());

        List<ConfirmationToken> existingTokens = confirmationTokenRepository.findByUserId(user.getId());
        confirmationTokenRepository.deleteAll(existingTokens);


        return userMapper.toDto(user);
    }

    private void createAndSendEmailConfirmationToken(User user) {

        ConfirmationToken confirmationToken = new ConfirmationToken();

        confirmationToken.setUser(user);
        confirmationToken.setCreatedDate(new Date());
        confirmationToken.setToken(UUID.randomUUID().toString());
        confirmationTokenRepository.save(confirmationToken);
        log.debug("Confirmation token to account {} sent", user.getEmail());

        String confirmLink = serverLocation + "/account/confirm-email?token=" + confirmationToken.getToken();

        MimeMessage mailMessage = javaMailSender.createMimeMessage();

        ResourceBundle bundle = ResourceBundle.getBundle("lang/messages", Locale.forLanguageTag("ru"));

        emailService.sendEmail(emailService.createMessage(mailMessage, user.getEmail(),
                bundle.getString("mail.theme.confirmMail"), generateConfirmationMailHtml(confirmLink, "ru")));

        log.debug("Email with confirmation token to account {} sent", user.getEmail());
    }

    private String generateConfirmationMailHtml(String confirmLink, String locale) {
        final String templateFileName;
        Map<String, Object> variables = new HashMap<>();
        variables.put("confirmLink", confirmLink);

        templateFileName = locale + File.separator + "confirmMail";
        String output = templateEngine.process(templateFileName, new Context(Locale.getDefault(), variables));
        log.debug("Confirmation email generated");
        return output;
    }
}
