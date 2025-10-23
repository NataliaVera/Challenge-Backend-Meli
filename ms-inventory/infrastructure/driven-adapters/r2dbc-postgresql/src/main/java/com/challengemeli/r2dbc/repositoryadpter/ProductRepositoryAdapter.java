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

import java.util.UUID;

@Repository
public class ProductRepositoryAdapter extends AdapterOperations<Product, ProductData, UUID, ProductRepository>
        implements Product {
    public ProductRepositoryAdapter(ProductRepository repository, ObjectMapper mapper) {
        super(repository, mapper, d -> mapper.map(d, Product.class));
    }

    @Override
    public Mono<Product> createProduct(Product product) {
        if (product == null){
             return Mono.error(new IllegalArgumentException("Product cannot be null")); 
        }

        String productCode = product.getProductCode();
        
        return repository.findByProductCode(productCode)
        .flatMap(exists -> Mono.error(new IllegalArgumentException("Product with code " + productCode + " already exists")))
        .switchIfEmpty(Mono.defer(() -> {
            ProductData productData = toData(product);
            return repository.createProduct(productData)
            .map(this::toEntity);
        }));

    }

    @Override
    public Mono<Product> findByProductId(UUID id) {
        return repository.findById(id)
                .map(this::toEntity)
                .onErrorMap(ex -> new RuntimeException("No existe el Id del Producto:"+ id));
    }

    @Override
    public Mono<Product> findByProductCode(String productCode) {
        return repository.getProductByCode(productCode)
                .map(productData -> Product.builder()
                        .productName(productData.getProductName()))
                .switchIfEmpty(Mono.error(new RuntimeException("No se encontro el codigo del Producto:"+productCode)));
    }

    @Override
    public Flux<Product> findAllProducts() {
        return repository.findAll()
                .map(this::toEntity);
    }

    @Override
    public Mono<Void> deleteProductById(UUID productId) {
        return repository.findByProductId(productId)
        .switchIfEmpty(Mono.error(new IllegalArgumentException("Product with id " + productId + " does not exist")))
        .flatMap(exists -> repository.deleteById(productId));
    }
}
