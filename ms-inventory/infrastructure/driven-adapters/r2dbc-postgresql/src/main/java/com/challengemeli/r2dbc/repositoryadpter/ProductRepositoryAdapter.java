package com.challengemeli.r2dbc.repositoryadpter;

import com.challengemeli.model.product.Product;
import com.challengemeli.model.product.gateways.ProductGateway;
import com.challengemeli.r2dbc.repositoryadpter.repository.ProductRepository;
import com.challengemeli.r2dbc.repositoryadpter.data.ProductData;
import com.challengemeli.r2dbc.helper.AdapterOperations;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.UUID;

@Repository
public class ProductRepositoryAdapter extends AdapterOperations<Product, ProductData, UUID, ProductRepository>
        implements ProductGateway {
    public ProductRepositoryAdapter(ProductRepository repository, ObjectMapper mapper) {
        super(repository, mapper, d -> mapper.map(d, Product.class));
    }

    @Override
    public Mono<Product> createProduct(Product product) {
        if (product == null){
             return Mono.error(new IllegalArgumentException("Product cannot be null")); 
        }

        String productCode = product.getProductCode();

        return repository.existsByProductCode(productCode)
                .flatMap(exists -> {
                    if (exists) {
                        return Mono.error(new IllegalArgumentException("Product with code " + product.getProductCode() +
                                " already exists"));
                    }
                    product.setCreatedAt(LocalDateTime.now());
                    product.setUpdatedAt(LocalDateTime.now());
                    return repository.save(toData(product))
                            .map(this::toEntity);
                });
    }

    @Override
    public Mono<Product> updateProduct(UUID productId, Product product) {
        if (product == null) {
            return Mono.error(new IllegalArgumentException("Product cannot be null"));
        }

        return repository.findById(productId)
                .switchIfEmpty(Mono.error(new IllegalArgumentException("Product with id " + productId + " does not exist")))
                .flatMap(existing -> {
                    ProductData productData = toData(product);
                    productData.setProductId(productId);

                    return repository.save(productData)
                            .map(this::toEntity);
                });
    }

    @Override
    public Mono<Product> findByProductId(UUID id) {
        return repository.findById(id)
                .map(this::toEntity)
                .onErrorMap(ex -> new IllegalArgumentException("Product id doesn't exists: "+ id));
    }

    @Override
    public Mono<Product> findByProductCode(String productCode) {
        return repository.findByProductCode(productCode)
                .map(this::toEntity)
                .switchIfEmpty(Mono.error(new IllegalArgumentException("Product with code " + productCode + " not found")));
    }

    @Override
    public Flux<Product> findAllProducts() {
        return repository.findAll()
                .map(this::toEntity);
    }

    @Override
    public Mono<Void> deleteProductById(UUID productId) {
        return repository.findById(productId)
                .switchIfEmpty(Mono.error(new IllegalArgumentException
                        ("Product with id " + productId + " does not exist")))
                .flatMap(exists -> repository.deleteById(productId));
    }

    @Override
    public Mono<Boolean> existsProductByCode(String productCode) {
        return repository.existsByProductCode(productCode);
    }
}
