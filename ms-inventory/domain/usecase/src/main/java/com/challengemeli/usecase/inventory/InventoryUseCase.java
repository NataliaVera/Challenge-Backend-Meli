package com.challengemeli.usecase.inventory;

import com.challengemeli.model.product.gateways.ProductGateway;
import com.challengemeli.model.productinventory.ProductInventory;
import com.challengemeli.model.productinventory.gateways.ProductInventoryGateway;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.sound.sampled.Port;
import java.time.LocalDateTime;
import java.util.UUID;

@RequiredArgsConstructor
public class InventoryUseCase {

    private final ProductInventoryGateway inventoryGateway;
    private final ProductGateway productGateway;

    public Mono<ProductInventory> updateStock(UUID productId, int x){
        return inventoryGateway.findByProductId(productId)
                .flatMap(inv ->{
                    int newStock = Math.max(0, inv.getTotalStock() + x);
                    inv.setTotalStock(newStock);
                    inv.setLastUpdated(LocalDateTime.now());
                    return inventoryGateway.createProductInventory(inv);
                });
    }

    public Flux<ProductInventory> getAllInventory(){
        return inventoryGateway.findAllProductInventory();
    }

    public Mono<ProductInventory> getInventoryByProductId (UUID productId){
        return inventoryGateway.findByProductId(productId);
    }
}
