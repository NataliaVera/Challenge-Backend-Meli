package com.challengemeli.kafka.consumer;

import com.challengemeli.model.exception.NotificationException;
import com.challengemeli.model.notification.Notification;
import com.challengemeli.model.notification.NotificationStatus;
import com.challengemeli.usecase.sendnotification.SendNotificationUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.reactive.ReactiveKafkaConsumerTemplate;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import static com.challengemeli.model.exception.NotificationErrorCodes.SAVE_ERROR;
import static com.challengemeli.model.exception.NotificationErrorCodes.UPDATE_ERROR;

@Component
@Log4j2
@RequiredArgsConstructor
public class KafkaConsumer {
    private final ReactiveKafkaConsumerTemplate<String, String> kafkaConsumer;
    private final SendNotificationUseCase notificationUseCase;

    @KafkaListener(topics = "inventory-updated", groupId = "notification-service")
    public void consumeInventoryEvent(ConsumerRecord<String, String> record) {
        String message = record.value();
        log.info("Received message from topic '{}', partition={}, offset={}: {}",
                record.topic(), record.partition(), record.offset(), message);

        Notification notification = new Notification();
        notification.setMessage("Inventory event received: " + message);
        notification.setStatus(NotificationStatus.PENDING);

        notificationUseCase.sendNotification(notification)
                .doOnSubscribe(sub -> log.debug("Processing notification for Kafka event..."))
                .doOnSuccess(n -> log.info("Notification sent successfully for message: {}", message))
                .doOnError(e -> log.error("Error while processing notification: {}", e.getMessage(), e))
                .onErrorResume(e -> {
                    if (e instanceof NotificationException ne) {
                        log.warn("Controlled NotificationException: {}", ne.getMessage());
                        return Mono.error(ne);
                    }
                    return Mono.error(new NotificationException(UPDATE_ERROR,
                            "Unexpected error processing Kafka event: " + message, e));
                })
                .subscribe();
    }
}
