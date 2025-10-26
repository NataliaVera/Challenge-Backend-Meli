package com.challengemeli.r2dbc;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.reactive.ReactiveKafkaProducerTemplate;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Log4j2
@Component
@RequiredArgsConstructor
public class InventoryKafkaProducer {

    private final ReactiveKafkaProducerTemplate<String, String> kafkaProducer;
    private final ObjectMapper objectMapper;

    @Value("${kafka.topic.inventory-updated}")
    private String topic;

    public Mono<Void> sendInventoryUpdatedEvent(Object event) {
        try {
            String message = objectMapper.writeValueAsString(event);
            log.info("Sending message to Kafka topic [{}]: {}", topic, message);

            return kafkaProducer.send(topic, message)
                    .doOnSuccess(result ->
                            log.info("Event sent successfully. Offset: {}", result.recordMetadata().offset()))
                    .doOnError(error ->
                            log.error("Error sending event to Kafka: {}", error.getMessage(), error))
                    .then();
        } catch (Exception e) {
            log.error("Serialization error: {}", e.getMessage(), e);
            return Mono.error(e);
        }
    }
}
