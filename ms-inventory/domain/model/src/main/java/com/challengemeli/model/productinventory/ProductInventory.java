package com.challengemeli.model.productinventory;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class ProductInventory {

    private UUID productId;
    private Integer totalStock;
    private String stockByStore;
    private LocalDateTime lastUpdated;
}
