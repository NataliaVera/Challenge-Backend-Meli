package com.challengemeli.api;

import com.challengemeli.api.store.StoreHandler;
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
    public RouterFunction<ServerResponse> routerProduct(StoreHandler productHandler) {
        return route(GET("/api/store"), productHandler::getAllStores)
                .andRoute(POST("/api/store"), productHandler::createStore)
                .andRoute(GET("/api/store/{id}"), productHandler::getStoreById)
                .andRoute(GET("/api/store/code/{code}"), productHandler::getStoreByCode)
                .andRoute(PUT("/api/store/update/{id}"), productHandler::updateStore)
                .and(route(DELETE("/api/store/{id}"), productHandler::deleteStore));
    }
}
