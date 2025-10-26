package com.challengemeli.usecase.processinventorysync;

import com.challengemeli.model.exception.NotificationException;
import com.challengemeli.model.inventorysyncevent.InventorySyncEvent;
import com.challengemeli.model.inventorysyncevent.gateways.InventorySyncEventGateway;
import com.challengemeli.model.notification.Notification;
import com.challengemeli.model.notification.NotificationStatus;
import com.challengemeli.model.notification.gateways.NotificationGateway;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

import static com.challengemeli.model.exception.NotificationErrorCodes.INVALID_NOTIFICATION;

@RequiredArgsConstructor
public class ProcessInventorySyncUseCase {

    private final NotificationGateway notificationGateway;
    private final InventorySyncEventGateway inventorySyncEvent;

    public Mono<Notification> processSync(InventorySyncEvent event){
        if(event == null || event.getStoreId()==null || event.getProductId() == null){
            return Mono.error(new NotificationException(INVALID_NOTIFICATION, "Invalid InventorySyncEvent data"));
        }

        return inventorySyncEvent.syncWithCentral(event)
                .flatMap(syncResult -> {
                    Notification notification = Notification.builder()
                            .storeId(event.getStoreId())
                            .productId(event.getProductId())
                            .message("Inventory sync completed successfully" + event.getProductId())
                            .status(NotificationStatus.SENT)
                            .createdAt(LocalDateTime.now())
                            .build();
                    return notificationGateway.saveNotification(notification);
                })
                .onErrorResume(error -> {
                    Notification failedNotification = Notification.builder()
                            .storeId(event.getStoreId())
                            .productId(event.getProductId())
                            .message("Failed to sync: " + error.getMessage())
                            .status(NotificationStatus.FAILED)
                            .createdAt(LocalDateTime.now())
                            .build();
                    return notificationGateway.saveNotification(failedNotification);
                });
    }

    public Mono<Notification> upsertInventorySync(InventorySyncEvent inventorySync) {
        if (inventorySync == null || inventorySync.getStoreId() == null || inventorySync.getProductId() == null) {
            return Mono.error(new NotificationException(INVALID_NOTIFICATION, "Invalid InventorySync data"));
        }

        return inventorySyncEvent.upsertInventorySync(inventorySync)
                .flatMap(result -> {
                    Notification notification = Notification.builder()
                            .storeId(inventorySync.getStoreId())
                            .productId(inventorySync.getProductId())
                            .message("Inventory upsert completed successfully for product " + inventorySync.getProductId())
                            .status(NotificationStatus.SENT)
                            .createdAt(LocalDateTime.now())
                            .build();

                    return notificationGateway.saveNotification(notification);
                })
                .onErrorResume(error -> {
                    Notification failedNotification = Notification.builder()
                            .storeId(inventorySync.getStoreId())
                            .productId(inventorySync.getProductId())
                            .message("Failed to upsert inventory: " + error.getMessage())
                            .status(NotificationStatus.FAILED)
                            .createdAt(LocalDateTime.now())
                            .build();

                    return notificationGateway.saveNotification(failedNotification);
                });
    }
}
