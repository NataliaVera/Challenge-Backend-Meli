package com.challengemeli.r2dbc.repositoryadpter.repository;

import com.challengemeli.r2dbc.repositoryadpter.data.ProductData;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface ProductRepository extends ReactiveCrudRepository<ProductData, UUID> {


    Mono<ProductData> getProductByCode (String productCode);

}
