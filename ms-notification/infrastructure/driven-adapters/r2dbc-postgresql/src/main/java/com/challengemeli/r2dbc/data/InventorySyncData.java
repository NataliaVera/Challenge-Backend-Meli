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
@Table(name = "tbl_inventory_records")
public class InventorySyncData {

    @Id
    private UUID id;

    @Column("store_id")
    private UUID storeId;

    @Column("product_id")
    private UUID productId;

    private Integer quantity;

    @Column("last_updated")
    @LastModifiedDate
    private LocalDateTime lastUpdated;

    @Column("event_id")
    private String eventId;
}
