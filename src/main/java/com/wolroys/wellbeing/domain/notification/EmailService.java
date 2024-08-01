package com.wolroys.wellbeing.domain.notification;


import jakarta.mail.internet.MimeMessage;
import org.springframework.scheduling.annotation.Async;

public interface EmailService {

    @Async
    void sendEmail(MimeMessage email);

    MimeMessage createMessage(MimeMessage mailMessage, String email, String subject, String text);
}
