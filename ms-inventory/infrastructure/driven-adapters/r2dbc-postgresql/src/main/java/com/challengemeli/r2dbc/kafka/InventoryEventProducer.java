package com.challengemeli.r2dbc.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.reactive.ReactiveKafkaProducerTemplate;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Slf4j
@Component
@RequiredArgsConstructor
public class InventoryEventProducer {

    private final ReactiveKafkaProducerTemplate<String, String> kafkaProducerTemplate;

    public Mono<Void> sendInventoryUpdatedEvent(String message) {
        return kafkaProducerTemplate.send("inventory-updated", message)
                .doOnSuccess(result -> log.info("Sent Kafka event to topic {}: {}", result.recordMetadata().topic(), message))
                .doOnError(e -> log.error("Failed to send Kafka event: {}", e.getMessage()))
                .then();
    }

}