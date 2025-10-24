package com.challengemeli.usecase.store;

import com.challengemeli.model.exception.InvalidInputException;
import com.challengemeli.model.exception.ResourceAlreadyExistsException;
import com.challengemeli.model.exception.ResourceNotFoundException;
import com.challengemeli.model.store.Store;
import com.challengemeli.model.store.gateways.StoreGateway;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@RequiredArgsConstructor
public class StoreUseCase {

    private final StoreGateway storeGateway;

    public Mono<Store> registerStore(Store store){
        if (store == null){
            return Mono.error(new InvalidInputException("Store must not be null"));
        }

        return validateStoreCode(store.getStoreCode())
                .then(storeGateway.existsStoreByCode(store.getStoreCode()))
                .flatMap(exists -> {
                    if (exists) {
                        return Mono.error(new ResourceAlreadyExistsException
                                ("Store with code " + store.getStoreCode() + " already exists"));
                    }
                    return storeGateway.createStore(store);
                });
    }

    public Flux<Store> getAllStores(){
        return storeGateway.findAllStores();
    }

    public Mono<Store> getStoreByCode(String storeCode){
        return validateStoreCode(storeCode)
                .then(storeGateway.findStoreByCode(storeCode))
                        .switchIfEmpty(Mono.error(new ResourceNotFoundException
                                ("Store with code "+ storeCode + "not found")));
    }

    public Mono<Void> deleteStore(UUID storeId){
        if(storeId == null){
            return Mono.error(new InvalidInputException("Store id is required"));
        }
        return storeGateway.deleteStoreById(storeId);
    }

    private Mono<Void> validateStoreCode(String storeCode){
        if (storeCode == null || storeCode.isBlank()){
            return Mono.error(new InvalidInputException("Store code is required"));
        }
        return Mono.empty();
    }
}
