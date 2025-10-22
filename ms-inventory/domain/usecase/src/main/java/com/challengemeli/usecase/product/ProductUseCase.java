package com.challengemeli.usecase.product;

import com.challengemeli.model.product.Product;
import com.challengemeli.model.product.gateways.ProductGateway;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@RequiredArgsConstructor
public class ProductUseCase {

    private final ProductGateway productGateway;

    public Mono<Product> createProduct(Product product) {
        if (product.getProductId() == null) {
            product.setProductId(UUID.randomUUID());
        }
        return productGateway.createProduct(product);
    }

    public Flux<Product> getAllProducts() {
        return productGateway.findAllProducts();
    }


    public Mono<Product> getProductById(UUID id) {
        return productGateway.findByProductId(id);
    }

}
