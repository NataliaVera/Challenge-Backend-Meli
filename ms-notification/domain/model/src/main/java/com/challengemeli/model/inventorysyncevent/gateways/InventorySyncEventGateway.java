package com.challengemeli.model.inventorysyncevent.gateways;

import com.challengemeli.model.inventorysyncevent.InventorySyncEvent;
import reactor.core.publisher.Mono;

public interface InventorySyncEventGateway {
    Mono<Void> syncWithCentral(InventorySyncEvent inventorySyncEvent);
    Mono<InventorySyncEvent> upsertInventorySync(InventorySyncEvent event);
}
