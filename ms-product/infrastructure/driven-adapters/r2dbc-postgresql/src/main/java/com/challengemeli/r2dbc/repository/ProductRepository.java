package com.challengemeli.r2dbc.repository;

import com.challengemeli.r2dbc.data.ProductData;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface ProductRepository extends ReactiveCrudRepository<ProductData, UUID> {

    Mono<ProductData> findByProductCode(String productCode);

    Mono<Boolean> existsByProductCode(String productCode);


}
