package com.challengemeli.r2dbc.repositoryadpter;

import com.challengemeli.model.product.Product;
import com.challengemeli.model.product.gateways.ProductGateway;
import com.challengemeli.model.productinventory.ProductInventory;
import com.challengemeli.r2dbc.repositoryadpter.data.ProductInventoryData;
import com.challengemeli.r2dbc.helper.AdapterOperations;
import com.challengemeli.r2dbc.repositoryadpter.repository.ProductInventoryRepository;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Repository
public class ProductInventoryRepositoryAdapter extends AdapterOperations<ProductInventory, ProductInventoryData, UUID,
        ProductInventoryRepository> implements ProductGateway {
    public ProductInventoryRepositoryAdapter(ProductInventoryRepository repository, ObjectMapper mapper) {
        super(repository, mapper, d -> mapper.map(d, ProductInventory.class));
    }

    @Override
    public Mono<Product> createProduct(Product product) {
        return null;
    }

    @Override
    public Mono<Product> findByProductId(UUID id) {
        return null;
    }

    @Override
    public Mono<Product> findByProductCode(String productCode) {
        return null;
    }

    @Override
    public Flux<Product> findAllProducts() {
        return null;
    }

    @Override
    public Mono<Void> deleteProductById(UUID productId) {
        return null;
    }
}
