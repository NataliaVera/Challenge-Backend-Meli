package com.challengemeli.model.product.gateways;

import com.challengemeli.model.product.Product;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface ProductGateway {
    Mono<Product> createProduct(Product product);
    Mono<Product> findByProductId(UUID id);
    Flux<Product> findAllProducts();

}
