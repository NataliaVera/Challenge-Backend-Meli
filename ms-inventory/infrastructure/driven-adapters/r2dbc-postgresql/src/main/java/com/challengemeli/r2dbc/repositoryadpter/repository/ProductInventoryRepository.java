package com.challengemeli.r2dbc.repositoryadpter.repository;

import com.challengemeli.r2dbc.repositoryadpter.data.ProductInventoryData;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import java.util.UUID;

public interface ProductInventoryRepository extends ReactiveCrudRepository<ProductInventoryData, UUID> {

}
