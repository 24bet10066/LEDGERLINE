package com.ledgerline.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import javax.sql.DataSource;

public final class Database {
    private static HikariDataSource dataSource;
    private Database() {}
    public static synchronized DataSource get() {
        if (dataSource == null) {
            HikariConfig config = new HikariConfig();
            config.setJdbcUrl(env("LEDGERLINE_DB_URL", "jdbc:mysql://localhost:3306/ledgerline?createDatabaseIfNotExist=true&serverTimezone=UTC"));
            config.setUsername(env("LEDGERLINE_DB_USER", "ledgerline"));
            config.setPassword(env("LEDGERLINE_DB_PASSWORD", ""));
            config.setMaximumPoolSize(10);
            config.setPoolName("ledgerline-pool");
            dataSource = new HikariDataSource(config);
        }
        return dataSource;
    }
    private static String env(String name, String fallback) {
        String value = System.getenv(name);
        return value == null || value.isBlank() ? fallback : value;
    }
}
