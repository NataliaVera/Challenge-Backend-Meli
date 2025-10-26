package com.challengemeli.r2dbc.helper;

import com.challengemeli.model.notification.Notification;
import com.challengemeli.r2dbc.data.NotificationData;

public class NotificationMapper {

    public static NotificationData toData(Notification notification){
        return new NotificationData(
                notification.getNotificationId(),
                notification.getStoreId(),
                notification.getProductId(),
                notification.getMessage(),
                notification.getStatus(),
                notification.getCreatedAt()
        );
    }

    public static Notification toDomain(NotificationData data){
        return new Notification(
                data.getNotificationId(),
                data.getStoreId(),
                data.getProductId(),
                data.getEventType(),
                data.getStatus(),
                data.getCreatedAt()
        );
    }
}
