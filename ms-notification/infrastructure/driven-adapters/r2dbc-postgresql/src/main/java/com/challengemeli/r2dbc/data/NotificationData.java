package com.challengemeli.r2dbc.data;

import com.challengemeli.model.notification.NotificationStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
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
@Table(name = "tbl_notifications")
public class NotificationData {

    @Id
    @Column("notification_id")
    private UUID notificationId;

    @Column("store_id")
    private UUID storeId;

    @Column("product_id")
    private UUID productId;

    @Column("event_type")
    private String eventType;

    private NotificationStatus status;

    @Column("created_at")
    @CreatedDate
    private LocalDateTime createdAt;
}
