package com.challengemeli.usecase.store;

import com.challengemeli.model.store.Store;
import com.challengemeli.model.store.gateways.StoreGateway;
import lombok.RequiredArgsConstructor;
import main.java.com.challengemeli.model.exception.InvalidInputException;
import main.java.com.challengemeli.model.exception.ResourceNotFoundException;
import reactor.core.publisher.Mono;

import java.util.UUID;

@RequiredArgsConstructor
public class StoreUseCase {

    private final StoreGateway storeGateway;

    public Mono<Store> registerStore(Store store){
        if (store == null){
            return Mono.error(new InvalidInputException("Store must not be null"));
        }

        validateStoreCode(store.getStoreCode());

        return storeGateway.findByStoreCode(store.getStoreCode())
            .flatMap(exists -> Mono.error(new ResourceAlreadyExistsException("Store with code "+ store.getStoreCode()+ "already exists")))
            .switchIfEmpty(Mono.defer(() -> storeGateway.createStore(store)));
    }

    public Mono<Store> getStoreByCode(String storeCode){
        validateStoreCode(storeCode);
        return storeGateway.findStoreByCode(storeCode)
        .switchIfEmpty(Mono.error(new ResourceNotFoundException("Store with code "+ storeCode + "not found")));
    }

    public Mono<Void> deleteStore(UUID storeId){
        if(storeId == null){
            return Mono.error(new InvalidInputException("Store id is required"));
        }
        return storeGateway.deleteStoreById(storeId);
    }

    private Mono<Void> validateStoreCode(String storeCode){
        if (storeCode == null || storeCode.isBlank){
            return Mono.error(new InvalidInputException("Store code is required"));
        }
    }
}
