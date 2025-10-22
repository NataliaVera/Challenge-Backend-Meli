package com.challengemeli.r2dbc;

import com.challengemeli.model.product.Product;
import com.challengemeli.model.product.gateways.ProductGateway;
import com.challengemeli.r2dbc.data.ProductData;
import com.challengemeli.r2dbc.helper.AdapterOperations;
import com.challengemeli.r2dbc.helper.ReactiveAdapterOperations;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Repository
public class ProductRepositoryAdapter extends AdapterOperations<Product, ProductData, UUID, ProductRepository>
        implements ProductGateway {
    public ProductRepositoryAdapter(ProductRepository repository, ObjectMapper mapper) {
        super(repository, mapper, d -> mapper.map(d, Product.class));
    }

    @Override
    public Mono<Product> createProduct(Product product) {
        ProductData data = toData(product);
        return repository.save(data)
                .map(this::toEntity);

    }

    @Override
    public Mono<Product> findByProductId(UUID id) {
        return repository.findById(id)
                .map(this::toEntity);
    }

    @Override
    public Flux<Product> findAllProducts() {
        return repository.findAll()
                .map(this::toEntity);
    }
}
