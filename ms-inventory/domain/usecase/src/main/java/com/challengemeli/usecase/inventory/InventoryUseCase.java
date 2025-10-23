package com.challengemeli.usecase.inventory;

import com.challengemeli.model.product.gateways.ProductGateway;
import com.challengemeli.model.productinventory.ProductInventory;
import com.challengemeli.model.productinventory.gateways.ProductInventoryGateway;
import lombok.RequiredArgsConstructor;
import main.java.com.challengemeli.model.exception.InvalidInputException;
import main.java.com.challengemeli.model.exception.ResourceAlreadyExistsException;
import main.java.com.challengemeli.model.exception.ResourceNotFoundException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.sound.sampled.Port;
import java.time.LocalDateTime;
import java.util.UUID;

@RequiredArgsConstructor
public class InventoryUseCase {

    private final ProductInventoryGateway inventoryGateway;
    private final ProductGateway productGateway;

    public Mono<ProductInventory> createProductInventory(ProductInventory productInventory){
        if (productInventory == null) return Mono.error(new InvalidInputException("Product inventory must not be null"));

        UUID productId = productInventory.getProductId();

        return validateProductId(productId)
            .then(productGateway.findByProductId(productId)
                .switchIfEmpty(Mono.error(new ResourceNotFoundException("Product with id "+ productId+ "doesn't exists"))))
            .then(inventoryGateway.findByProductId(productId)
                .flatmap(exists -> Mono.error(new ResourceAlreadyExistsException("Inventory for product id "+productId+" already exists")))
                .switchIfEmpty(Mono.defer(() ->{
                    productInventory.setLastUpdated(LocalDateTime.now());
                    return inventoryGateway.createProductInventory(productInventory);
                })));
    }

    
      public Mono<ProductInventory> getInventoryByProductId (UUID productId){
        validateProductId(productId);
        return inventoryGateway.findByProductId(productId);
    }
    
    public Mono<ProductInventory> updateStock(UUID productId, ProductInventory productInventory){
        validateProductId(productId);
        return inventoryGateway.findByProductId(productId)
                .flatMap(inv ->{
                    int newStock = Math.max(0, inv.getTotalStock() + x);
                    inv.setTotalStock(newStock);
                    inv.setLastUpdated(LocalDateTime.now());
                    return inventoryGateway.createProductInventory(inv);
                });
    }

    public Flux<ProductInventory> findAllProductInventory(){
        return inventoryGateway.findAllProductInventory();
    }

    public Mono<Void> deleteProductInventory(UUID productInventory){
        validateProductId(productInventory);
        return inventoryGateway.deleteProductInventory(productInventory);
    }

    private Mono<Void> validateProductId (UUID productId){
        if (productId == null) return Mono.error(new InvalidInputException("Product id is required"));
    }
}
