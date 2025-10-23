package com.challengemeli.model.store.gateways;

import com.challengemeli.model.store.Store;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface StoreGateway {
    Mono<Store> createStore(Store store);
    Mono<Store> findStoreByCode(String storeCode);
    Mono<Store> findAllStores();
    Mono<Void> deleteStoreById(UUID storeId);
}
