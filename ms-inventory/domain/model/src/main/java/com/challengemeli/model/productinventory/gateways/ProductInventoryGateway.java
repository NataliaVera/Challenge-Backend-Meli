package com.challengemeli.model.productinventory.gateways;

import com.challengemeli.model.productinventory.ProductInventory;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;
    
public interface ProductInventoryGateway {
    Mono<ProductInventory> registerInventory(ProductInventory productInventory);
    Mono<ProductInventory> getInventoryByStore(UUID storeId);
    Mono<ProductInventory> findByStoreAndProduct (UUID storeId,UUID productId);
    Mono<ProductInventory> updateStock(UUID productId, ProductInventory productInventory);
    Flux<ProductInventory> findAllProductInventory();
    Mono<Void> deleteProductInventory(UUID productInventory);
}
