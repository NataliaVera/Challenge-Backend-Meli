package com.challengemeli.r2dbc;

import com.challengemeli.model.notification.Notification;
import com.challengemeli.model.notification.NotificationStatus;
import com.challengemeli.model.notification.gateways.NotificationGateway;
import com.challengemeli.r2dbc.data.NotificationData;
import com.challengemeli.r2dbc.helper.NotificationMapper;
import com.challengemeli.r2dbc.helper.ReactiveAdapterOperations;
import com.challengemeli.r2dbc.repository.NotificationRepository;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Primary
@Repository
public class NotificationRepositoryAdapter implements NotificationGateway {

    private final NotificationRepository repository;
    public NotificationRepositoryAdapter(NotificationRepository repository) {
        this.repository = repository;
    }

    @Override
    public Mono<Notification> saveNotification(Notification notification) {
        if (notification.getNotificationId() == null) {
            notification.setNotificationId(UUID.randomUUID());
        }

        return repository.save(NotificationMapper.toData(notification))
                .map(NotificationMapper::toDomain);
    }

    @Override
    public Flux<Notification> findPendingNotifications() {
        return repository.findByStatus(NotificationStatus.PENDING.name())
                .map(NotificationMapper::toDomain);
    }

    @Override
    public Mono<Void> markAsSent(UUID notificationId) {
        return repository.findById(notificationId)
                .flatMap(entity -> {
                    entity.setStatus(NotificationStatus.valueOf(NotificationStatus.SENT.name()));
                    return repository.save(entity);
                }).then();
    }
}
