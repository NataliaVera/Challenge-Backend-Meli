package com.challengemeli.model.productinventory.gateways;

import com.challengemeli.model.productinventory.ProductInventory;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface ProductInventoryGateway {
    Mono<ProductInventory> createProductInventory(ProductInventory productInventory);
    Mono<ProductInventory> findByProductId(UUID productId);
    Flux<ProductInventory> findAllProductInventory();
    Mono<Void> deleteProductInventory(UUID productInventory);
}
