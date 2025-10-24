package com.challengemeli.model.store.gateways;

import com.challengemeli.model.store.Store;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface StoreGateway {

    Mono<Store> createStore(Store store);
    Mono<Store> findStoreById(UUID storeId);
    Mono<Store> findStoreByCode(String storeCode);
    Flux<Store> findAllStores();
    Mono<Store> updateStore(UUID storeId, Store store);
    Mono<Void> deleteStoreById(UUID storeId);
    Mono<Boolean> existsStoreByCode(String storeCode);
}
