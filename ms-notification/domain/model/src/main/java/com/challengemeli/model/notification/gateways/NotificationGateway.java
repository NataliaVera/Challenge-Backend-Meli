package com.challengemeli.model.notification.gateways;

import com.challengemeli.model.notification.Notification;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface NotificationGateway {
    Mono<Notification> saveNotification(Notification notification);
    Flux<Notification> findPendingNotifications();
    Mono<Void> markAsSent(UUID notificationId);
}
