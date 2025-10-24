package com.challengemeli.api.store;

import com.challengemeli.api.helper.HandleException;
import com.challengemeli.model.store.Store;
import com.challengemeli.usecase.store.StoreUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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
    private final HandleException errorHandler;

    public Mono<ServerResponse> createStore (ServerRequest request){
        return request.bodyToMono(Store.class)
                .flatMap(storeUseCase::registerStore)
                .flatMap(store -> ServerResponse.status(HttpStatus.CREATED)
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(store))
                .onErrorResume(errorHandler::handleException);
    }

    public Mono<ServerResponse> getStoreByCode(ServerRequest request){
        String storeCode = request.pathVariable("storeCode");
        return storeUseCase.getStoreByCode(storeCode)
                .flatMap(store -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(store)
                        .switchIfEmpty(ServerResponse.notFound().build()))
                .onErrorResume(errorHandler::handleException);
    }

    public Mono<ServerResponse> getAllStores(ServerRequest request) {
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(storeUseCase.getAllStores(), Store.class)
                .onErrorResume(errorHandler::handleException);
    }

    public Mono<ServerResponse> deleteStore(ServerRequest request){
        UUID storeId = UUID.fromString(request.pathVariable("storeId"));
        return storeUseCase.deleteStore(storeId)
                .then(ServerResponse.noContent().build())
                .onErrorResume(errorHandler::handleException);
    }
}
