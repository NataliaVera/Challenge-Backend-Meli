package com.challengemeli.r2dbc;

import com.challengemeli.r2dbc.data.ProductData;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import java.util.UUID;

public interface ProductRepository extends ReactiveCrudRepository<ProductData, UUID> {

}
