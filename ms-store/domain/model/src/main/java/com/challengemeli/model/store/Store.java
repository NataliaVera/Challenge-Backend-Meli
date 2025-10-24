package com.challengemeli.model.store;

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
public class Store {

    private UUID storeId;
    private String storeCode;
    private String storeName;
    private String location;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
