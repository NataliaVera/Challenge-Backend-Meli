package com.challengemeli.api;

import com.challengemeli.api.helper.HandleException;
import com.challengemeli.model.product.Product;
import com.challengemeli.usecase.product.ProductUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class ProductHandler {

    private final ProductUseCase productUseCase;
    private final HandleException errorHandler;

    public Mono<ServerResponse> createProduct(ServerRequest request) {
        return request.bodyToMono(Product.class)
                .flatMap(productUseCase::registerProduct)
                .flatMap(product -> ServerResponse.status(HttpStatus.CREATED)
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(product))
                .onErrorResume(errorHandler::handleException);
    }

    public Mono<ServerResponse> getAllProducts(ServerRequest request) {
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(productUseCase.getAllProducts(), Product.class)
                .onErrorResume(errorHandler::handleException);
    }

    public Mono<ServerResponse> getProductById(ServerRequest request) {
        UUID id = UUID.fromString(request.pathVariable("id"));
        return productUseCase.getProductById(id)
                .flatMap(product -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(product))
                .onErrorResume(errorHandler::handleException);
    }

    public Mono<ServerResponse> getProductByCode(ServerRequest request) {
        String code = request.pathVariable("code");
        return productUseCase.getProductByCode(code)
                .flatMap(product -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(product))
                .onErrorResume(errorHandler::handleException);
    }

    public Mono<ServerResponse> updateProduct(ServerRequest request){
        UUID productId = UUID.fromString(request.pathVariable("id"));
        return request.bodyToMono(Product.class)
                .flatMap(product -> productUseCase.updateProduct(productId, product))
                .flatMap(updatedProduct -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(updatedProduct))
                .onErrorResume(errorHandler::handleException);
    }


    public Mono<ServerResponse> deleteProduct(ServerRequest request){
        UUID productId = UUID.fromString(request.pathVariable("id"));
        return productUseCase.deleteProductById(productId)
                .then(ServerResponse.noContent().build());
    }
}

