package com.challengemeli.api.productinventory;

import com.challengemeli.api.helper.HandleException;
import com.challengemeli.model.productinventory.ProductInventory;
import com.challengemeli.usecase.inventory.InventoryUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.rsocket.RSocketProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class ProductInventoryHandler {

    private final InventoryUseCase inventoryUseCase;
    private final HandleException errorHandler;

    public Mono<ServerResponse> createProductInventory(ServerRequest request){
        return request.bodyToMono(ProductInventory.class)
                .flatMap(inventoryUseCase::registerInventory)
                .flatMap(inventory -> ServerResponse.status(HttpStatus.CREATED)
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(inventory))
                .onErrorResume(errorHandler::handleException);
    }

    public Mono<ServerResponse> getAllInventory(ServerRequest request){
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(inventoryUseCase.findAllProductInventory(), ProductInventory.class);
    }

    public Mono<ServerResponse> findInventoryById(ServerRequest request){
        UUID inventoryId = UUID.fromString(request.pathVariable("inventoryId"));
        return inventoryUseCase.findInventoryById(inventoryId)
                .flatMap(inventory -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(inventory))
                .onErrorResume(errorHandler::handleException);
    }

    public Mono<ServerResponse> getInventoryByStore(ServerRequest request){
        UUID storeId = UUID.fromString(request.pathVariable("storeId"));
        return inventoryUseCase.getInventoryByStore(storeId)
                .flatMap(inventory -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(inventory))
                .onErrorResume(errorHandler::handleException);
    }

    public Mono<ServerResponse> addStock(ServerRequest request){
        UUID storeId = UUID.fromString(request.pathVariable("storeId"));
        UUID productId = UUID.fromString(request.pathVariable("productId"));
        return request.bodyToMono(ProductInventory.class)
                .flatMap(productInventory ->
                        inventoryUseCase.addStock(storeId, productId, productInventory.getTotalStock()))
                .flatMap(updatedInventory -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(updatedInventory))
                .onErrorResume(errorHandler::handleException);
    }

    public Mono<ServerResponse> reduceStock(ServerRequest request){
        UUID storeId = UUID.fromString(request.pathVariable("storeId"));
        UUID productId = UUID.fromString(request.pathVariable("productId"));
        return request.bodyToMono(ProductInventory.class)
                .flatMap(productInventory ->
                        inventoryUseCase.reduceStock(storeId, productId, productInventory.getTotalStock()))
                .flatMap(updatedInventory -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(updatedInventory))
                .onErrorResume(errorHandler::handleException);
    }

    public Mono<ServerResponse> deleteProductInventory(ServerRequest request){
        UUID inventoryId = UUID.fromString(request.pathVariable("inventoryId"));
        return inventoryUseCase.deleteProductInventory(inventoryId)
                .then(ServerResponse.noContent().build())
                .onErrorResume(errorHandler::handleException);
    }
}
