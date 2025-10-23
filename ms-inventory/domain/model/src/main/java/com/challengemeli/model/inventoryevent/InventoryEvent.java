package com.challengemeli.model.inventoryevent;

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
public class InventoryEvent {

    private UUID eventId;
    private String eventType;
    private String sourceStoreCode;
    private String payload;
    private LocalDateTime createdAt;
}
