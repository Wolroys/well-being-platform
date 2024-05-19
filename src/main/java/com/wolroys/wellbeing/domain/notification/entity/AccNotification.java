package com.wolroys.wellbeing.domain.notification.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "acc_notification")
@Data
public class AccNotification {

    @Id
    private Long userId;

    @Id
    private Long notificationId;
}
