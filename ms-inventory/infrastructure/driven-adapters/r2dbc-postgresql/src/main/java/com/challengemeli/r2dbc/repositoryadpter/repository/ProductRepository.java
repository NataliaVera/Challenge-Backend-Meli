package com.challengemeli.r2dbc.repositoryadpter.repository;

import com.challengemeli.r2dbc.repositoryadpter.data.ProductData;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface ProductRepository extends ReactiveCrudRepository<ProductData, UUID> {

    @Query("SELECT * FROM tbl_products WHERE product_code = :productCode")
    Mono<ProductData> findByProductCode(String productCode);

    Mono<Boolean> existsByProductCode(String productCode);


}
