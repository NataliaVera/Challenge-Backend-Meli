package com.challengemeli.usecase.sendnotification;

import com.challengemeli.model.exception.NotificationException;
import com.challengemeli.model.notification.Notification;
import com.challengemeli.model.notification.NotificationStatus;
import com.challengemeli.model.notification.gateways.NotificationGateway;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

import static com.challengemeli.model.exception.NotificationErrorCodes.INVALID_NOTIFICATION;
import static com.challengemeli.model.exception.NotificationErrorCodes.NOT_FOUND;
import static com.challengemeli.model.exception.NotificationErrorCodes.UPDATE_ERROR;

@RequiredArgsConstructor
public class SendNotificationUseCase {

    private final NotificationGateway notificationGateway;

    public Mono<Notification> sendNotification(Notification notification){
        if(notification == null || notification.getStoreId() == null){
            return Mono.error(new NotificationException(INVALID_NOTIFICATION, "Invalid notification data"));

        }

        return Mono.just(notification)
                .flatMap(notification1 -> {
                    notification1.setStatus(NotificationStatus.SENT);
                    notification1.setCreatedAt(java.time.LocalDateTime.now());
                    notification1.setMessage("Notification sent successfully to store " + notification1.getStoreId());
                    return notificationGateway.saveNotification(notification1);
                })
                .onErrorResume(error -> {
                    notification.setStatus(NotificationStatus.FAILED);
                    notification.setMessage("Failed to send notification: " + error.getMessage());
                    return notificationGateway.saveNotification(notification);
                });
    }

    public Flux<Notification> getPendingNotifications() {
        return notificationGateway.findPendingNotifications()
                .switchIfEmpty(Mono.error(new NotificationException(
                        NOT_FOUND, "No pending notifications found")))
                .onErrorMap(e -> {
                    if (e instanceof NotificationException) return e;
                    return new NotificationException("FETCH_ERROR", "Error retrieving notifications", e);
                });
    }

    public Mono<Void> markNotificationAsSent(UUID notificationId) {
        return Mono.justOrEmpty(notificationId)
                .switchIfEmpty(Mono.error(new NotificationException(
                        INVALID_NOTIFICATION, "Notification ID cannot be null")))
                .flatMap(id -> notificationGateway.markAsSent(notificationId))
                .onErrorMap(e -> {
                    if (e instanceof NotificationException) return e;
                    return new NotificationException(UPDATE_ERROR, "Error marking notification as sent", e);
                });
    }
}
