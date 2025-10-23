package com.challengemeli.model.product.gateways;

import com.challengemeli.model.product.Product;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface ProductGateway {
    Mono<Product> createProduct(Product product);
    Mono<Product> updateProduct(UUID productId, Product product);
    Mono<Product> findByProductId(UUID productId);
    Mono<Product> findByProductCode(String productCode);
    Flux<Product> findAllProducts();
    Mono<Void> deleteProductById(UUID productId);
    Mono<Boolean> existsProductByCode(String productCode);
}
