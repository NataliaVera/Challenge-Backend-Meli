package com.challengemeli.r2dbc.helper;

import com.challengemeli.model.inventorysyncevent.InventorySyncEvent;
import com.challengemeli.r2dbc.data.InventorySyncData;

public class InventorySyncMapper {

    private InventorySyncMapper() {}

    public static InventorySyncData toEntity(InventorySyncEvent event) {
        InventorySyncData inventorySyncData = new InventorySyncData();
        inventorySyncData.setStoreId(event.getStoreId());
        inventorySyncData.setProductId(event.getProductId());
        inventorySyncData.setQuantity(event.getQuantity());
        inventorySyncData.setLastUpdated(event.getLastUpdated());
        return inventorySyncData;
    }


    public static InventorySyncEvent toDomain(InventorySyncData data) {
        return InventorySyncEvent.builder()
                .storeId(data.getStoreId())
                .productId(data.getProductId())
                .quantity(data.getQuantity())
                .lastUpdated(data.getLastUpdated())
                .build();
    }
}
