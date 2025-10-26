package com.challengemeli.usecase.inventory;

import com.challengemeli.model.exception.InvalidInputException;
import com.challengemeli.model.exception.ResourceNotFoundException;
import com.challengemeli.model.productinventory.ProductInventory;
import com.challengemeli.model.productinventory.gateways.ProductInventoryGateway;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.UUID;

@RequiredArgsConstructor
public class InventoryUseCase {

    private final ProductInventoryGateway inventoryGateway;

    public Mono<ProductInventory> registerInventory(ProductInventory productInventory){
        if(productInventory == null){
            return Mono.error(new InvalidInputException("Inventory must not be null"));
        }

        return validateStoreIdAndProductId(productInventory.getStoreId(), productInventory.getProductId())
                .then(inventoryGateway.registerInventory(productInventory));
    }

    public Mono<ProductInventory> findInventoryById(UUID inventoryId) {
        return validateInventoryId(inventoryId)
                .then(inventoryGateway.findInventoryById(inventoryId));
    }

    public Mono<ProductInventory> getInventoryByStore(UUID storeId){
        if(storeId == null){
            return Mono.error(new InvalidInputException("Store id is required"));
        }
        return inventoryGateway.getInventoryByStore(storeId);
    }
    public Flux<ProductInventory> findAllProductInventory(){
        return inventoryGateway.findAllProductInventory();
    }
    public Mono<ProductInventory> findByStoreAndProduct (UUID storeId,UUID productId){
        return validateStoreIdAndProductId(storeId, productId)
                .then(inventoryGateway.findByStoreAndProduct(storeId, productId));
    }

    public Mono<ProductInventory> addStock(UUID storeId, UUID productId, Integer stockAdd){
        return validateStoreIdAndProductId(storeId, productId)
                .then(validateQuantityStock(stockAdd))
                .then(inventoryGateway.findByStoreAndProduct(storeId, productId))
                .switchIfEmpty(Mono.error(new ResourceNotFoundException("Inventory record not found")))
                .flatMap(existing -> {
                    existing.setTotalStock(existing.getTotalStock() + stockAdd);
                    existing.setLastUpdated(LocalDateTime.now());
                    return inventoryGateway.addStock(existing.getStoreId(), existing.getProductId(), existing);
                });
    }
    public Mono<ProductInventory> reduceStock(UUID storeId, UUID productId, Integer stockReduce){
        return validateStoreIdAndProductId(storeId, productId)
                .then(inventoryGateway.findByStoreAndProduct(storeId, productId))
                .switchIfEmpty(Mono.error(new ResourceNotFoundException("Inventory record not found")))
                .flatMap(existing -> {
                    validateQuantityStock(stockReduce);
                    existing.setTotalStock(existing.getTotalStock() - stockReduce);
                    existing.setLastUpdated(LocalDateTime.now());
                    return inventoryGateway.addStock(existing.getStoreId(), existing.getProductId(), existing);
                });
    }
    public Mono<Void> deleteProductInventory(UUID productInventory){
        return validateInventoryId(productInventory)
                .then(inventoryGateway.deleteProductInventory(productInventory));
    }

    private Mono<Void> validateInventoryId(UUID inventoryId){
        if(inventoryId == null){
            return Mono.error(new InvalidInputException("Inventory id is required"));
        }
        return Mono.empty();
    }
    private Mono<Void> validateStoreIdAndProductId(UUID storeId, UUID productId){
        if(storeId == null || productId == null){
            return Mono.error(new InvalidInputException("Store id and Product id are required"));
        }
        return Mono.empty();
    }

    private Mono<Void> validateQuantityStock (Integer quantityStock){
        if (quantityStock == null || quantityStock <= 0){
            return Mono.error(new InvalidInputException("Quantity must be greather than zero"));
        }
        return Mono.empty();
    }
}
