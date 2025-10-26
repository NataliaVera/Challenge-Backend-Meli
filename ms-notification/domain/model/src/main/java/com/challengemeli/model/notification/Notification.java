package com.challengemeli.model.notification;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class Notification {

    private UUID notificationId;
    private UUID storeId;
    private UUID productId;
    private String message;
    private NotificationStatus status;
    private LocalDateTime createdAt;
}
