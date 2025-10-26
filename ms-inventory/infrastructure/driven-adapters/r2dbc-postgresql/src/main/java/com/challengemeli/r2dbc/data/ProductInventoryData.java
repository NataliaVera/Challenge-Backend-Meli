package com.challengemeli.r2dbc.data;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Getter
@Setter
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tbl_inventories")
public class ProductInventoryData {

    @Id
    @Column("inventory_id")
    private UUID inventoryId;

    @Column("product_id")
    private UUID productId;

    @Column("store_id")
    private UUID storeId;

    @Column("total_stock")
    private Integer totalStock;

    @Column("last_updated")
    @LastModifiedDate
    private LocalDateTime lastUpdated;

}
