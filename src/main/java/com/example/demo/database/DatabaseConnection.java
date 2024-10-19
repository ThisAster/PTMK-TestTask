package com.example.demo.database;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Configuration
@ConfigurationProperties(prefix = "application")
public class DatabaseConnection {
    @Value("${spring.datasource.url}")
    private String url;
    @Value("${spring.datasource.username}")
    private String username;
    @Value("${spring.datasource.password}")
    private String password;
    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, username, password);
    }
}
