package com.wolroys.wellbeing.domain.notification;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class KafkaConsumer {

    private final MailingService mailingService;

    @KafkaListener(topics = "reminder_topic")
    public void receiveNotification(String message) {
        log.info("Reminder consumer received new message");
        mailingService.sendNotificationMail(message);
    }
}
