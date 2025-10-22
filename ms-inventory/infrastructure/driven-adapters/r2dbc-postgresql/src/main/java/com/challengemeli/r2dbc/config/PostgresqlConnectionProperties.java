package com.challengemeli.r2dbc.config;


import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "adapters.postgres")
public class PostgresqlConnectionProperties {

    @Value("${adapters.postgres.host}")
    private String host;
    @Value("${adapters.postgres.port}")
    private Integer port;
    @Value("${adapters.postgres.database}")
    private String database;
    @Value("${adapters.postgres.schema}")
    private String schema;
    @Value("${adapters.postgres.username}")
    private String username;
    @Value("${adapters.postgres.password}")
    private String password;
}
