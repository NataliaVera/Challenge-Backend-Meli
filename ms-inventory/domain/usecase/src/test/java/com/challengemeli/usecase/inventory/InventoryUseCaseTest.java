package com.challengemeli.usecase.inventory;

import com.challengemeli.model.exception.InvalidInputException;
import com.challengemeli.model.exception.ResourceNotFoundException;
import com.challengemeli.model.productinventory.ProductInventory;
import com.challengemeli.model.productinventory.gateways.ProductInventoryGateway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class InventoryUseCaseTest {

    @Mock
    private ProductInventoryGateway inventoryGateway;
    @InjectMocks
    private InventoryUseCase inventoryUseCase;

    @Test
    void registerInventory_ShouldRegisterSuccessfully() {
        ProductInventory inventory = new ProductInventory(
                UUID.randomUUID(), UUID.randomUUID(), UUID.randomUUID(), 10, LocalDateTime.now());

        when(inventoryGateway.registerInventory(any(ProductInventory.class)))
                .thenReturn(Mono.just(inventory));

        StepVerifier.create(inventoryUseCase.registerInventory(inventory))
                .expectNext(inventory)
                .verifyComplete();

        verify(inventoryGateway, times(1)).registerInventory(any(ProductInventory.class));
    }

    @Test
    void registerInventory_ShouldThrowInvalidInputException_WhenInventoryIsNull() {
        StepVerifier.create(inventoryUseCase.registerInventory(null))
                .expectError(InvalidInputException.class)
                .verify();

        verify(inventoryGateway, never()).registerInventory(any());
    }

    @Test
    void findInventoryById_ShouldReturnInventory() {
        UUID inventoryId = UUID.randomUUID();
        ProductInventory inventory = new ProductInventory(
                inventoryId, UUID.randomUUID(), UUID.randomUUID(), 10, null);

        when(inventoryGateway.findInventoryById(inventoryId)).thenReturn(Mono.just(inventory));

        StepVerifier.create(inventoryUseCase.findInventoryById(inventoryId))
                .expectNext(inventory)
                .verifyComplete();

        verify(inventoryGateway, times(1)).findInventoryById(inventoryId);
    }

    @Test
    void addStock_ShouldAddStockSuccessfully() {
        UUID storeId = UUID.randomUUID();
        UUID productId = UUID.randomUUID();
        ProductInventory existingInventory = new ProductInventory(UUID.randomUUID(), productId, storeId, 10, null);
        ProductInventory updatedInventory = new ProductInventory(UUID.randomUUID(), productId, storeId, 20, null);

        when(inventoryGateway.findByStoreAndProduct(storeId, productId)).thenReturn(Mono.just(existingInventory));
        when(inventoryGateway.addStock(eq(storeId), eq(productId), any(ProductInventory.class)))
                .thenReturn(Mono.just(updatedInventory));

        StepVerifier.create(inventoryUseCase.addStock(storeId, productId, 10))
                .expectNext(updatedInventory)
                .verifyComplete();

        verify(inventoryGateway).findByStoreAndProduct(storeId, productId);
        verify(inventoryGateway).addStock(eq(storeId), eq(productId), any(ProductInventory.class));
    }

    @Test
    void addStock_ShouldThrowResourceNotFoundException_WhenInventoryNotFound() {
        UUID storeId = UUID.randomUUID();
        UUID productId = UUID.randomUUID();

        when(inventoryGateway.findByStoreAndProduct(storeId, productId)).thenReturn(Mono.empty());

        StepVerifier.create(inventoryUseCase.addStock(storeId, productId, 10))
                .expectError(ResourceNotFoundException.class)
                .verify();

        verify(inventoryGateway).findByStoreAndProduct(storeId, productId);
        verify(inventoryGateway, never()).addStock(any(), any(), any());
    }

    @Test
    void reduceStock_ShouldReduceStockSuccessfully() {
        UUID storeId = UUID.randomUUID();
        UUID productId = UUID.randomUUID();
        ProductInventory existingInventory =
                new ProductInventory(UUID.randomUUID(), productId, storeId, 20, LocalDateTime.now());
        ProductInventory updatedInventory =
                new ProductInventory(existingInventory.getInventoryId(), productId, storeId, 10, LocalDateTime.now());

        when(inventoryGateway.findByStoreAndProduct(storeId, productId))
                .thenReturn(Mono.just(existingInventory));
        when(inventoryGateway.addStock(eq(storeId), eq(productId), any(ProductInventory.class)))
                .thenReturn(Mono.just(updatedInventory));

        StepVerifier.create(inventoryUseCase.reduceStock(storeId, productId, 10))
                .expectNextMatches(result -> result.getTotalStock() == 10)
                .verifyComplete();

        verify(inventoryGateway).findByStoreAndProduct(storeId, productId);
        verify(inventoryGateway).addStock(eq(storeId), eq(productId), any(ProductInventory.class));
    }


    @Test
    void reduceStock_ShouldThrowResourceNotFoundException_WhenInventoryNotFound() {
        UUID storeId = UUID.randomUUID();
        UUID productId = UUID.randomUUID();

        when(inventoryGateway.findByStoreAndProduct(storeId, productId)).thenReturn(Mono.empty());

        StepVerifier.create(inventoryUseCase.reduceStock(storeId, productId, 10))
                .expectError(ResourceNotFoundException.class)
                .verify();

        verify(inventoryGateway).findByStoreAndProduct(storeId, productId);
        verify(inventoryGateway, never()).reduceStock(any(), any(), any());
    }

}