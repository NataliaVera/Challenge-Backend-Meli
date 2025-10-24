package com.challengemeli.r2dbc.repositoryadpter;

import com.challengemeli.model.store.Store;
import com.challengemeli.model.store.gateways.StoreGateway;
import com.challengemeli.r2dbc.helper.AdapterOperations;
import com.challengemeli.r2dbc.repositoryadpter.data.StoreData;
import com.challengemeli.r2dbc.repositoryadpter.repository.StoreRepository;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.UUID;

@Repository
public class StoreRepositoryAdapter extends AdapterOperations<Store, StoreData, UUID, StoreRepository>
        implements StoreGateway {
    protected StoreRepositoryAdapter(StoreRepository repository, ObjectMapper mapper) {
        super(repository, mapper, d -> mapper.map(d, Store.class));
    }

    @Override
    public Mono<Store> createStore(Store store) {
        if (store == null){
            return Mono.error(new IllegalArgumentException("Store cannot be null"));
        } 
        
        String storeCode = store.getStoreCode();

        return repository.existsByStoreCode(storeCode)
                .flatMap(storeCodeExists -> {
                    if (storeCodeExists){
                        return Mono.error(
                                new IllegalArgumentException("Store with code " + storeCode + " already exists"));
                    }
                    store.setCreatedAt(LocalDateTime.now());
                    store.setUpdatedAt(LocalDateTime.now());
                    return repository.save(toData(store))
                            .map(this::toEntity);
                });
    }

    @Override
    public Mono<Store> findStoreByCode(String storeCode) {
        return repository.findByStoreCode(storeCode)
                .map(this::toEntity)
                .switchIfEmpty(Mono.error(
                        new IllegalArgumentException("Store with code " + storeCode + " already exists")));
    }

    @Override
    public Flux<Store> findAllStores() {
        return repository.findAll()
                .map(this::toEntity);
    }

    @Override
    public Mono<Void> deleteStoreById(UUID storeId) {
        return repository.findById(storeId)
            .switchIfEmpty(Mono.error(new IllegalArgumentException("Store with id " + storeId + " does not exist")))
            .flatMap(exists -> repository.deleteById(storeId));
    }

    @Override
    public Mono<Boolean> existsStoreByCode(String storeCode) {
        return repository.existsByStoreCode(storeCode);
    }
}
