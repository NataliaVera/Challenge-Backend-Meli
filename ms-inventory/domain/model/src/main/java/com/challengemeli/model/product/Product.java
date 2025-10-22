package com.challengemeli.model.product;

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
public class Product {
    private UUID productId;
    private String productCode;
    private String productName;
    private String description;
    private Double productPrice;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
