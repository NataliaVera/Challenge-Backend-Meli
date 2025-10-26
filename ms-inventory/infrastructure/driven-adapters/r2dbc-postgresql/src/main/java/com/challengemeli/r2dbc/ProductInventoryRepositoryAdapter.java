package com.challengemeli.r2dbc;

import com.challengemeli.model.exception.ResourceNotFoundException;
import com.challengemeli.model.productinventory.ProductInventory;
import com.challengemeli.model.productinventory.gateways.ProductInventoryGateway;
import com.challengemeli.r2dbc.data.ProductInventoryData;
import com.challengemeli.r2dbc.helper.AdapterOperations;
import com.challengemeli.r2dbc.repository.ProductInventoryRepository;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.UUID;

@Repository
public class ProductInventoryRepositoryAdapter extends AdapterOperations<ProductInventory, ProductInventoryData, UUID,
        ProductInventoryRepository> implements ProductInventoryGateway {

    private final InventoryKafkaProducer kafkaProducer;
    public ProductInventoryRepositoryAdapter(ProductInventoryRepository repository,
                                             ObjectMapper mapper,
                                             InventoryKafkaProducer kafkaProducer) {
        super(repository, mapper, d -> mapper.map(d, ProductInventory.class));
        this.kafkaProducer = kafkaProducer;
    }

    @Override
    public Mono<ProductInventory> registerInventory(ProductInventory productInventory) {
        return repository.save(mapper.map(productInventory, ProductInventoryData.class))
                .map(this::toEntity)
                .flatMap(saved -> kafkaProducer.sendInventoryUpdatedEvent(saved).thenReturn(saved));
    }

    @Override
    public Mono<ProductInventory> findInventoryById(UUID inventoryId) {
        return repository.findById(inventoryId)
                .map(this::toEntity)
                .switchIfEmpty(Mono.error(new IllegalArgumentException("Inventory id doesn't exists: "+ inventoryId)));
    }

    @Override
    public Mono<ProductInventory> getInventoryByStore(UUID storeId) {
        return repository.findByStoreId(storeId)
                .map(this::toEntity)
                .switchIfEmpty(Mono.error(new IllegalArgumentException("Store with id" + storeId + " not found")));
    }

    @Override
    public Flux<ProductInventory> findAllProductInventory() {
        return repository.findAll()
                .map(this::toEntity);
    }

    @Override
    public Mono<ProductInventory> findByStoreAndProduct(UUID storeId, UUID productId) {
        return repository.findByStoreIdAndProductId(storeId, productId)
                .map(this::toEntity)
                .switchIfEmpty(Mono.error(
                        new IllegalArgumentException("Store with id" + storeId +" and Product with id"+productId+  " not found")));
    }

    @Override
    public Mono<ProductInventory> addStock(UUID storeId, UUID productId, ProductInventory productInventory) {
        return repository.findByStoreIdAndProductId(storeId,productId)
                .switchIfEmpty(Mono.error(new ResourceNotFoundException("Inventory record not found for storeId:"+storeId+" and productId:"+productId)))
                .flatMap(existing -> {
                    existing.setTotalStock(existing.getTotalStock() + productInventory.getTotalStock());
                    existing.setLastUpdated(LocalDateTime.now());
                    return repository.save(existing);
                })
                .map(this::toEntity)
                .flatMap(updated ->
                        kafkaProducer.sendInventoryUpdatedEvent(updated).thenReturn(updated));
    }

    @Override
    public Mono<ProductInventory> reduceStock(UUID storeId, UUID productId, ProductInventory productInventory) {
         return repository.findByStoreIdAndProductId(storeId,productId)
                .switchIfEmpty(Mono.error(new ResourceNotFoundException("Inventory record not found for storeId:"+storeId+" and productId:"+productId)))
                .flatMap(existing -> {
                    existing.setTotalStock(existing.getTotalStock() - productInventory.getTotalStock());
                    existing.setLastUpdated(LocalDateTime.now());
                    return repository.save(existing);
                })
                 .map(this::toEntity)
                 .flatMap(updated ->
                         kafkaProducer.sendInventoryUpdatedEvent(updated).thenReturn(updated));
    }

    @Override
    public Mono<Void> deleteProductInventory(UUID productInventory) {
        return repository.findById(productInventory)
                .switchIfEmpty(Mono.error(
                        new IllegalArgumentException("Inventory with id " + productInventory + " does not exist")))
                .flatMap(exists -> repository.deleteById(productInventory));
    }
}
