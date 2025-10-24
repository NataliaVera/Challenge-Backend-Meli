package com.challengemeli.r2dbc.data;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tbl_inventories")
public class ProductInventoryData {

    @Id
    @Column("product_id")
    private UUID productId;

    @Column("total_stock")
    private Integer totalStock;

    @Column("stock_by_store")
    private String stockByStore;

    @Column("last_updated")
    @LastModifiedDate
    private LocalDateTime lastUpdated;
}
