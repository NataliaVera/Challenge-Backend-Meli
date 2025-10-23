package com.challengemeli.r2dbc.repositoryadpter.data;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tbl_products")
public class ProductData {

    @Id
    @Column("product_id")
    private UUID productId;

    @Column("product_code")
    private String productCode;

    @Column("product_name")
    private String productName;

    private String description;

    @Column("product_price")
    private Double productPrice;

    @Column("product_category")
    private String productCategory;

    @Column("created_at")
    @CreatedDate
    private LocalDateTime createdAt;

    @Column("updated_at")
    @LastModifiedDate
    private LocalDateTime updatedAt;
}
