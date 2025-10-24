package com.challengemeli.api.store;

import com.challengemeli.model.store.Store;
import com.challengemeli.usecase.store.StoreUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class StoreHandler {

    private final StoreUseCase storeUseCase;

    /*public Mono<ServerResponse> createStore (ServerRequest request){
        return request.bodyToMono(Store.class)
                .flatMap(storeUseCase::)
                .flatMap(store -> ServerResponse.status(201)
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(store));

    }*/

    public Mono<ServerResponse> getStoreByCode(ServerRequest request){
        String storeCode = request.pathVariable("storeCode");
        return storeUseCase.getStoreByCode(storeCode)
                .flatMap(store -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(store)
                        .switchIfEmpty(ServerResponse.notFound().build()));
    }

    public Mono<ServerResponse> deleteStore(ServerRequest request){
        UUID storeId = UUID.fromString(request.pathVariable("storeId"));
        return storeUseCase.deleteStore(storeId)
                .then(ServerResponse.noContent().build());
    }
}
