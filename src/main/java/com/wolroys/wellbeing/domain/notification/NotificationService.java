package com.wolroys.wellbeing.domain.notification;

import com.wolroys.wellbeing.domain.event.entity.Event;

public interface NotificationService {

    void sendReminderNotification(Event event);
}
