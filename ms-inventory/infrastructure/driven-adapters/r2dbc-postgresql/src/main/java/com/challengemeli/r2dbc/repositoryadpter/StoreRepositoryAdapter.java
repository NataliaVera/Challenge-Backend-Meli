package com.challengemeli.r2dbc.repositoryadpter;

import com.challengemeli.model.store.Store;
import com.challengemeli.model.store.gateways.StoreGateway;
import com.challengemeli.r2dbc.repositoryadpter.data.StoreData;
import com.challengemeli.r2dbc.helper.AdapterOperations;
import com.challengemeli.r2dbc.repositoryadpter.repository.StoreRepository;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Repository
public class StoreRepositoryAdapter extends AdapterOperations<Store, StoreData, UUID, StoreRepository>
        implements StoreGateway {
    protected StoreRepositoryAdapter(StoreRepository repository, ObjectMapper mapper) {
        super(repository, mapper, d -> mapper.map(d, Store.class));
    }

    @Override
    public Mono<Store> createStore(Store store) {
        return null;
    }

    @Override
    public Mono<Store> findStoreByCode(String storeCode) {
        return null;
    }

    @Override
    public Mono<Store> findAllStores() {
        return null;
    }

    @Override
    public Mono<Void> deleteStoreById(UUID storeId) {
        return null;
    }
}
