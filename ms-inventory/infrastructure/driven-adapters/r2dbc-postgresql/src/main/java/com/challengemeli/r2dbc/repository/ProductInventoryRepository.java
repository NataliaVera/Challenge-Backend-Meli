package com.challengemeli.r2dbc.repository;

import com.challengemeli.r2dbc.data.ProductInventoryData;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import java.util.UUID;

public interface ProductInventoryRepository extends ReactiveCrudRepository<ProductInventoryData, UUID> {

}
