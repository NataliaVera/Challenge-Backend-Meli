package com.challengemeli.api;

import org.springframework.context.annotation.Configuration;

import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class RouterRest {

    /*@Bean
    public RouterFunction<ServerResponse> routerInventory(ProductInventoryHandler inventoryHandler){
        return route(GET("/api/inventory"), inventoryHandler::getAllInventory)
                .andRoute(GET("/api/inventory/{productId}"), inventoryHandler::getInventoryByProductId)
                .and(route(PUT("/api/inventory/{productId}"), inventoryHandler::updateStock));
    }*/


}
