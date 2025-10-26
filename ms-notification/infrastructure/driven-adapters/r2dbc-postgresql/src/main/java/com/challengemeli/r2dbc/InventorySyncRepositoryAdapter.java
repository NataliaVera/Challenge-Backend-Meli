package com.challengemeli.r2dbc;

import com.challengemeli.model.inventorysyncevent.InventorySyncEvent;
import com.challengemeli.model.inventorysyncevent.gateways.InventorySyncEventGateway;
import com.challengemeli.r2dbc.data.InventorySyncData;
import com.challengemeli.r2dbc.helper.InventorySyncMapper;
import com.challengemeli.r2dbc.repository.InventorySyncRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.r2dbc.core.DatabaseClient;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@RequiredArgsConstructor
public class InventorySyncRepositoryAdapter implements InventorySyncEventGateway {
    private final InventorySyncRepository repository;
    private final DatabaseClient databaseClient;


    @Override
    public Mono<Void> syncWithCentral(InventorySyncEvent event) {
        InventorySyncData entity = new InventorySyncData();
        entity.setStoreId(event.getStoreId());
        entity.setProductId(event.getProductId());
        entity.setQuantity(event.getQuantity());
        entity.setLastUpdated(LocalDateTime.now());

        String upsert = "INSERT INTO inventory_sync (store_id, product_id, quantity, last_updated) " +
                "VALUES(:storeId, :productId, :quantity, :lastUpdated) " +
                "ON CONFLICT (store_id, product_id) DO UPDATE SET quantity = :quantity, last_updated = :lastUpdated";


        return databaseClient.sql(upsert)
                .bind("storeId", event.getStoreId())
                .bind("productId", event.getProductId())
                .bind("quantity", event.getQuantity())
                .bind("lastUpdated", LocalDateTime.now())
                .then()
                .onErrorMap(e -> e); // bubble up
    }


    @Override
    public Mono<InventorySyncEvent> upsertInventorySync(InventorySyncEvent inventorySync) {
        InventorySyncData e = InventorySyncMapper.toEntity(inventorySync);

        String upsert = "INSERT INTO inventory_sync (store_id, product_id, quantity, last_updated) " +
                "VALUES(:storeId, :productId, :quantity, :lastUpdated) " +
                "ON CONFLICT (store_id, product_id) DO UPDATE SET quantity = :quantity, last_updated = :lastUpdated";


        return databaseClient.sql(upsert)
                .bind("storeId", e.getStoreId())
                .bind("productId", e.getProductId())
                .bind("quantity", e.getQuantity())
                .bind("lastUpdated", e.getLastUpdated())
                .then()
                .thenReturn(inventorySync);
    }

}

