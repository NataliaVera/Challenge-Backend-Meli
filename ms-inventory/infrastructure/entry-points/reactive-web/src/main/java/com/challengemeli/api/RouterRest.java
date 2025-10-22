package com.challengemeli.api;

import com.challengemeli.api.product.ProductHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class RouterRest {
    @Bean
    public RouterFunction<ServerResponse> routerFunction(ProductHandler handler) {
        return route(GET("/api/products"), handler::getAllProducts)
                .andRoute(POST("/api/products"), handler::createProduct)
                .and(route(GET("/api/products/{id}"), handler::getProductById));
    }
}
