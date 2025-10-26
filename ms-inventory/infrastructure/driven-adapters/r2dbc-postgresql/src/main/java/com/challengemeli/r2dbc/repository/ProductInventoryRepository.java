package com.challengemeli.r2dbc.repository;

import com.challengemeli.r2dbc.data.ProductInventoryData;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface ProductInventoryRepository extends ReactiveCrudRepository<ProductInventoryData, UUID> {

    Mono<ProductInventoryData> findByStoreIdAndProductId(UUID storeId, UUID productId);
    Mono<ProductInventoryData> findByStoreId(UUID storeId);
}
