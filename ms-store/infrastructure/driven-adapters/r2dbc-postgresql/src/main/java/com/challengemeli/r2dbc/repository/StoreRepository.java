package com.challengemeli.r2dbc.repository;

import com.challengemeli.r2dbc.data.StoreData;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface StoreRepository extends ReactiveCrudRepository<StoreData, UUID> {

    Mono<StoreData> findByStoreCode(String storeCode);
    Mono<Boolean> existsByStoreCode (String storeCode);
}
