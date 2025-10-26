package com.challengemeli.r2dbc;

import com.challengemeli.model.exception.ResourceNotFoundException;
import com.challengemeli.model.productinventory.ProductInventory;
import com.challengemeli.r2dbc.data.ProductInventoryData;
import com.challengemeli.r2dbc.repository.ProductInventoryRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.reactivecommons.utils.ObjectMapper;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.UUID;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductInventoryRepositoryAdapterTest {

    @Mock
    private ProductInventoryRepository repository;

    @Mock
    private ObjectMapper mapper;

    @Mock
    private InventoryKafkaProducer kafkaProducer;

    @InjectMocks
    private ProductInventoryRepositoryAdapter adapter;

    @Test
    void registerInventory_ShouldRegisterSuccessfully() {
        ProductInventory inventory = new ProductInventory(UUID.randomUUID(), UUID.randomUUID(), UUID.randomUUID(), 10, null);
        ProductInventoryData inventoryData = new ProductInventoryData();
        when(mapper.map(inventory, ProductInventoryData.class)).thenReturn(inventoryData);
        when(repository.save(inventoryData)).thenReturn(Mono.just(inventoryData));
        when(mapper.map(inventoryData, ProductInventory.class)).thenReturn(inventory);
        when(kafkaProducer.sendInventoryUpdatedEvent(inventory)).thenReturn(Mono.empty());

        StepVerifier.create(adapter.registerInventory(inventory))
                .expectNext(inventory)
                .verifyComplete();

        verify(repository, times(1)).save(inventoryData);
        verify(kafkaProducer, times(1)).sendInventoryUpdatedEvent(inventory);
    }

    @Test
    void findInventoryById_ShouldReturnInventory() {
        UUID inventoryId = UUID.randomUUID();
        ProductInventoryData inventoryData = new ProductInventoryData();
        ProductInventory inventory = new ProductInventory();
        when(repository.findById(inventoryId)).thenReturn(Mono.just(inventoryData));
        when(mapper.map(inventoryData, ProductInventory.class)).thenReturn(inventory);

        StepVerifier.create(adapter.findInventoryById(inventoryId))
                .expectNext(inventory)
                .verifyComplete();

        verify(repository, times(1)).findById(inventoryId);
    }

    @Test
    void findInventoryById_ShouldThrowException_WhenNotFound() {
        UUID inventoryId = UUID.randomUUID();
        when(repository.findById(inventoryId)).thenReturn(Mono.empty());

        StepVerifier.create(adapter.findInventoryById(inventoryId))
                .expectError(IllegalArgumentException.class)
                .verify();

        verify(repository, times(1)).findById(inventoryId);
    }

    @Test
    void addStock_ShouldThrowException_WhenInventoryNotFound() {
        UUID storeId = UUID.randomUUID();
        UUID productId = UUID.randomUUID();
        ProductInventory inventory = new ProductInventory();

        when(repository.findByStoreIdAndProductId(storeId, productId)).thenReturn(Mono.empty());

        StepVerifier.create(adapter.addStock(storeId, productId, inventory))
                .expectError(ResourceNotFoundException.class)
                .verify();

        verify(repository, times(1)).findByStoreIdAndProductId(storeId, productId);
    }
}