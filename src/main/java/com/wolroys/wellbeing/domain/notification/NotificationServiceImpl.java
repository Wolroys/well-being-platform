package com.wolroys.wellbeing.domain.notification;

import com.wolroys.wellbeing.domain.event.entity.Event;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Locale;
import java.util.ResourceBundle;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private final JavaMailSender mailSender;
    private final EmailService emailService;

    @Override
    public void sendReminderNotification(Event event) {
        MimeMessage message = mailSender.createMimeMessage();

        ResourceBundle bundle = ResourceBundle.getBundle("lang/messages", Locale.forLanguageTag("ru"));
    }
}
