package com.challengemeli.model.inventoryevent.gateways;

import com.challengemeli.model.inventoryevent.InventoryEvent;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface InventoryEventGateway {
    Mono<InventoryEvent> createInventoryEvent(InventoryEvent inventoryEvent);
    Mono<InventoryEvent> findInventoryEventById(UUID eventId);
    Flux<InventoryEvent> findAllInventoryEvents();
}
