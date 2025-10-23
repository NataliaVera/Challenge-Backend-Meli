package com.challengemeli.usecase.product;

import com.challengemeli.model.product.Product;
import com.challengemeli.model.product.gateways.ProductGateway;
import lombok.RequiredArgsConstructor;
import main.java.com.challengemeli.model.exception.InvalidInputException;
import main.java.com.challengemeli.model.exception.ResourceAlreadyExistsException;
import main.java.com.challengemeli.model.exception.ResourceNotFoundException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@RequiredArgsConstructor
public class ProductUseCase {

    private final ProductGateway productGateway;

    public Mono<Product> registerProduct(Product product) {
        if(product == null){
            return Mono.error(new InvalidInputException("Product must not be null"));
        }
        validateProductStore(product.getProductCode());
        return productGateway.findByProductCode(product.getProductCode())
            .flatMap(exists -> Mono.error(new ResourceAlreadyExistsException("Store with code "+ product.getProductByCode()+ "already exists")))
            .switchIfEmpty(Mono.defer(() -> productGateway.createProduct(product)));
    }

    public Flux<Product> getAllProducts() {
        return productGateway.findAllProducts();
    }


    public Mono<Product> getProductById(UUID productId) {
        validateProductId(productId);
        return productGateway.findByProductId(productId);
    }

    public Mono<Product> getProductByCode(String productCode) {
        validateProductStore(productCode);
        return productGateway.findByProductCode(productCode)
            .switchIfEmpty(Mono.error(new ResourceNotFoundException("Store with code "+productCode + " not found")));
    }

    public Mono<Void> deleteProductById(UUID productId){
        validateProductId(productId);
        return productGateway.deleteProductById(productId);
    }

    private Mono<Void> validateProductId(UUID productId){
        if (productId == null){
            return Mono.error(new InvalidInputException("Product id is required"));
        }
    }

    private Mono<Void> validateProductStore(String productCode){
        if (productCode == null || productCode.isBlank()){
            return Mono.error(new InvalidInputException("Store code is required"));
        }
    }

}
