package com.challengemeli.r2dbc.repositoryadpter;

import com.challengemeli.model.product.Product;
import com.challengemeli.model.product.gateways.ProductGateway;
import com.challengemeli.model.productinventory.ProductInventory;
import com.challengemeli.model.productinventory.gateways.ProductInventoryGateway;
import com.challengemeli.r2dbc.repositoryadpter.data.ProductInventoryData;
import com.challengemeli.r2dbc.helper.AdapterOperations;
import com.challengemeli.r2dbc.repositoryadpter.repository.ProductInventoryRepository;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Repository
public class ProductInventoryRepositoryAdapter extends AdapterOperations<ProductInventory, ProductInventoryData, UUID,
        ProductInventoryRepository> implements ProductInventoryGateway {
    public ProductInventoryRepositoryAdapter(ProductInventoryRepository repository, ObjectMapper mapper) {
        super(repository, mapper, d -> mapper.map(d, ProductInventory.class));
    }

    @Override
    public Mono<ProductInventory> createProductInventory(ProductInventory productInventory){
            return null;
    }

    @Override
    public Mono<ProductInventory> findByProductId(UUID productId){
        return null;
    }

    @Override
    public Mono<ProductInventory> updateStock(UUID productId, ProductInventory productInventory) {
        return null;
    }

    @Override
    public Flux<ProductInventory> findAllProductInventory(){
        return null;
    }

    @Override
    public Mono<Void> deleteProductInventory(UUID productInventory) {
        return null;
    }
}
