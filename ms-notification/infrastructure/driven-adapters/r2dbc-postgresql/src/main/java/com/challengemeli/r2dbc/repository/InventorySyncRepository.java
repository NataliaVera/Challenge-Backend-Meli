package com.challengemeli.r2dbc.repository;

import com.challengemeli.r2dbc.data.InventorySyncData;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface InventorySyncRepository extends ReactiveCrudRepository<InventorySyncData, UUID> {

    @Query("SELECT * FROM inventory_sync WHERE store_id = :storeId AND product_id = :productId")
    Mono<InventorySyncData> findByStoreIdAndProductId(UUID storeId, UUID productId);
}
