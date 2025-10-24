package com.challengemeli.api;

import com.challengemeli.api.product.ProductHandler;
import com.challengemeli.api.productinventory.ProductInventoryHandler;
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
    public RouterFunction<ServerResponse> routerProduct(ProductHandler productHandler) {
        return route(GET("/api/products"), productHandler::getAllProducts)
                .andRoute(POST("/api/products"), productHandler::createProduct)
                .andRoute(GET("/api/products/{id}"), productHandler::getProductById)
                .andRoute(GET("/api/products/code/{code}"), productHandler::getProductByCode)
                .andRoute(PUT("/api/products/update/{id}"), productHandler::updateProduct)
                .and(route(DELETE("/api/products/{id}"), productHandler::deleteProduct));
    }

    /*@Bean
    public RouterFunction<ServerResponse> routerInventory(ProductInventoryHandler inventoryHandler){
        return route(GET("/api/inventory"), inventoryHandler::getAllInventory)
                .andRoute(GET("/api/inventory/{productId}"), inventoryHandler::getInventoryByProductId)
                .and(route(PUT("/api/inventory/{productId}"), inventoryHandler::updateStock));
    }*/

    @Bean
    public RouterFunction<ServerResponse> routerStore(StoreHandler storeHandler){
        return route(POST("/api/store"), storeHandler::createStore)
                .andRoute(GET("/api/store/{storeCode}"), storeHandler::getStoreByCode)
                .andRoute(GET("/api/stores"), storeHandler::getAllStores)
                .andRoute(DELETE("/api/store/{id}"), storeHandler::deleteStore);
    }
}
