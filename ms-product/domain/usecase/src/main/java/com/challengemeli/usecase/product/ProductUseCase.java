package com.challengemeli.usecase.product;

import com.challengemeli.model.product.Product;
import com.challengemeli.model.product.exception.InvalidInputException;
import com.challengemeli.model.product.exception.ResourceAlreadyExistsException;
import com.challengemeli.model.product.exception.ResourceNotFoundException;
import com.challengemeli.model.product.gateways.ProductGateway;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.UUID;

@RequiredArgsConstructor
public class ProductUseCase {

    private final ProductGateway productGateway;

    public Mono<Product> registerProduct(Product product) {
        if(product == null){
            return Mono.error(new InvalidInputException("Product must not be null"));
        }

        return validateProductCode(product.getProductCode())
                .then(productGateway.existsProductByCode(product.getProductCode()))
                .flatMap(exists -> {
                    if (exists) {
                        return Mono.error(new ResourceAlreadyExistsException
                                ("Product with code " + product.getProductCode() + " already exists"));
                    }
                    return productGateway.createProduct(product);
                });
    }

    public Flux<Product> getAllProducts() {
        return productGateway.findAllProducts();
    }


    public Mono<Product> getProductById(UUID productId) {
        return validateProductId(productId)
                .then(productGateway.findByProductId(productId))
                .switchIfEmpty(Mono.error(new ResourceNotFoundException
                        ("Product with id "+productId + " not found")));
    }

    public Mono<Product> getProductByCode(String productCode) {
        return validateProductCode(productCode)
                .then(productGateway.findByProductCode(productCode))
                .switchIfEmpty(Mono.error(new ResourceNotFoundException
                        ("Product with code "+productCode + " not found")));
    }

    public Mono<Product> updateProduct(UUID productId, Product product){
        return validateProductId(productId)
                .then(Mono.defer(() -> {
                    if (product == null) {
                        return Mono.error(new InvalidInputException("Product must not be null"));
                    }
                    return productGateway.findByProductId(productId)
                            .switchIfEmpty(Mono.error(
                                    new ResourceNotFoundException("Product with id " + productId + " not found")))
                            .flatMap(existing ->
                                    productGateway.findByProductCode(product.getProductCode())
                                            .flatMap( product1 -> {
                                                if (!product1.getProductId().equals(productId)) {
                                                    return Mono.error(new ResourceAlreadyExistsException(
                                                            "Product with code " + product.getProductCode() + " already exists"));
                                                }
                                                return Mono.just(existing);
                                            })
                                            .switchIfEmpty(Mono.just(existing))
                                            .flatMap(validated -> {
                                                validated.setProductName(product.getProductName());
                                                validated.setDescription(product.getDescription());
                                                validated.setProductCategory(product.getProductCategory());
                                                validated.setProductPrice(product.getProductPrice());
                                                validated.setUpdatedAt(LocalDateTime.now());
                                                return productGateway.updateProduct(productId, validated);
                                            })
                            );
                }));
    }

    public Mono<Void> deleteProductById(UUID productId){
        validateProductId(productId);
        return productGateway.deleteProductById(productId);
    }

    private Mono<Void> validateProductId(UUID productId){
        if (productId == null){
            return Mono.error(new InvalidInputException("Product id is required"));
        }
        return Mono.empty();
    }

    private Mono<Void> validateProductCode(String productCode){
        if (productCode == null || productCode.isBlank()){
            return Mono.error(new InvalidInputException("Product code is required"));
        }
        return Mono.empty();
    }
}
