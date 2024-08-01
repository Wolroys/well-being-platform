package com.wolroys.wellbeing.domain.notification;

import com.wolroys.wellbeing.domain.exception.EmailSendingException;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;

@Service
@Slf4j
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender mailSender;

    @Value("${mail_username}")
    private String emailUsername;

    @Value("${mail_personal}")
    private String emailPersonal;

    @Override
    public void sendEmail(MimeMessage email) {
        mailSender.send(email);
    }

    @Override
    public MimeMessage createMessage(MimeMessage mailMessage, String email, String subject, String text) {
        MimeMessageHelper helper = new MimeMessageHelper(mailMessage);

        try {
            helper.setTo(email);
            helper.setFrom(emailUsername, emailPersonal);
            helper.setSubject(subject);
            helper.setText(text, true);
        } catch (MessagingException | UnsupportedEncodingException e) {
            log.error("An error occurred while creation message: {}", e.getMessage());
            throw new EmailSendingException("An error occurred while sending the email", e);
        }

        return mailMessage;
    }

}
