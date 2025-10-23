package com.challengemeli.api.productinventory;

import com.challengemeli.model.productinventory.ProductInventory;
import com.challengemeli.usecase.inventory.InventoryUseCase;
import lombok.RequiredArgsConstructor;
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

    public Mono<ServerResponse> getAllInventory(ServerRequest request){
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(inventoryUseCase.getAllInventory(), ProductInventory.class);
    }

    public Mono<ServerResponse> getInventoryByProductId(ServerRequest request){
        UUID productId = UUID.fromString(request.pathVariable("productId"));
        return inventoryUseCase.getInventoryByProductId(productId)
                .flatMap(inventory -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(inventory))
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> updateStock(ServerRequest request){
        UUID productId = UUID.fromString(request.pathVariable("productId"));
        return null;
    }
}
