package com.challengemeli.api;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.DELETE;
import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RequestPredicates.PUT;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class RouterRest {
    @Bean
    public RouterFunction<ServerResponse> routerProduct(ProductHandler productHandler) {
        return route(GET("/api/products"), productHandler::getAllProducts)
                .andRoute(POST("/api/products"), productHandler::createProduct)
                .andRoute(GET("/api/products/{id}"), productHandler::getProductById)
                .andRoute(GET("/api/products/code/{code}"), productHandler::getProductByCode)
                .andRoute(PUT("/api/products/update/{id}"), productHandler::updateProduct)
                .and(route(DELETE("/api/products/{id}"), productHandler::deleteProduct));
    }
}
