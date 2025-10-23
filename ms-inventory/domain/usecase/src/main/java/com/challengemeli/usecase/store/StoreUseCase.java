package com.challengemeli.usecase.store;

import com.challengemeli.model.store.Store;
import com.challengemeli.model.store.gateways.StoreGateway;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

import java.util.UUID;

@RequiredArgsConstructor
public class StoreUseCase {

    private final StoreGateway storeGateway;

    public Mono<Store> registerStore(Store store){
        return storeGateway.createStore(store);
    }

    public Mono<Store> getStoreByCode(String storeCode){
        return storeGateway.findStoreByCode(storeCode);
    }

    public Mono<Void> deleteStore(UUID storeId){
        return storeGateway.deleteStoreById(storeId);
    }
}
