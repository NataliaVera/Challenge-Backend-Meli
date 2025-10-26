package com.challengemeli.r2dbc.config;

import com.challengemeli.model.inventorysyncevent.gateways.InventorySyncEventGateway;
import com.challengemeli.model.notification.gateways.NotificationGateway;
import com.challengemeli.r2dbc.InventorySyncRepositoryAdapter;
import com.challengemeli.r2dbc.NotificationRepositoryAdapter;
import com.challengemeli.r2dbc.repository.InventorySyncRepository;
import com.challengemeli.r2dbc.repository.NotificationRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.r2dbc.core.DatabaseClient;

@Configuration
public class ConfigurationBeans {

    @Bean
    public NotificationGateway notificationGateway(NotificationRepository repo) {
        return new NotificationRepositoryAdapter(repo);
    }


    @Bean
    public InventorySyncEventGateway inventorySyncEventGateway(InventorySyncRepository repo, DatabaseClient databaseClient) {
        return new InventorySyncRepositoryAdapter(repo, databaseClient);
    }
}
