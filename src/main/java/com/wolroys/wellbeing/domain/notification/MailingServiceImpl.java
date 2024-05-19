package com.wolroys.wellbeing.domain.notification;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MailingServiceImpl implements MailingService {

    private final JavaMailSender javaMailSender;

    @Override
    public void sendNotificationMail(String message) {

    }
}
