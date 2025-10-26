package com.challengemeli.model.inventorysyncevent;
import lombok.Builder;
import lombok.AllArgsConstructor;
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
public class InventorySyncEvent {

    private UUID storeId;
    private UUID productId;
    private Integer quantity;
    private LocalDateTime lastUpdated;
}
