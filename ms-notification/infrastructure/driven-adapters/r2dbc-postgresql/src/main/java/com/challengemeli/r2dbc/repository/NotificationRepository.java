package com.challengemeli.r2dbc.repository;

import com.challengemeli.r2dbc.data.NotificationData;
import org.springframework.data.repository.query.ReactiveQueryByExampleExecutor;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface NotificationRepository extends ReactiveCrudRepository<NotificationData, UUID> {

    Flux<NotificationData> findByStatus(String status);
    Mono<Void> deleteByStatus(String status);
}
