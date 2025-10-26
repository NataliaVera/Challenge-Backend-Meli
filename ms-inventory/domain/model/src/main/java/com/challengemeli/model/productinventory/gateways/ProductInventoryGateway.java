package com.challengemeli.model.productinventory.gateways;

import com.challengemeli.model.productinventory.ProductInventory;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;
    
public interface ProductInventoryGateway {
    Mono<ProductInventory> registerInventory(ProductInventory productInventory);
    Mono<ProductInventory> findInventoryById(UUID inventoryId);
    Mono<ProductInventory> getInventoryByStore(UUID storeId);
    Flux<ProductInventory> findAllProductInventory();
    Mono<ProductInventory> findByStoreAndProduct (UUID storeId,UUID productId);
    Mono<ProductInventory> addStock(UUID storeId, UUID productId, ProductInventory productInventory);
    Mono<ProductInventory> reduceStock(UUID storeId, UUID productId, ProductInventory productInventory);
    Mono<Void> deleteProductInventory(UUID productInventory);
}
