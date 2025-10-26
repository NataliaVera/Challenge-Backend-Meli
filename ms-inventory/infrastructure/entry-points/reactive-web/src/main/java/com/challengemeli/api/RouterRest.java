package com.challengemeli.api;

import com.challengemeli.api.productinventory.ProductInventoryHandler;
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
    public RouterFunction<ServerResponse> routerInventory(ProductInventoryHandler inventoryHandler){
        return route (POST("/api/inventory"), inventoryHandler::createProductInventory)
                .andRoute(GET("/api/inventory"), inventoryHandler::getAllInventory)
                .andRoute(GET("/api/inventory/{inventoryId}"), inventoryHandler::findInventoryById)
                .andRoute(GET("/api/inventory/store/{storeId}"), inventoryHandler::getInventoryByStore)
                .andRoute(PUT("/api/inventory/add/{storeId}/{productId}"), inventoryHandler::addStock)
                .andRoute(PUT("/api/inventory/reduce/{storeId}/{productId}"), inventoryHandler::reduceStock)
                .andRoute(DELETE("/api/inventory/addStock/{storeId}/{productId}"), inventoryHandler::addStock);
    }


}
