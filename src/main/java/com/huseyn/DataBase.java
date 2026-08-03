package com.huseyn;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import javax.sql.DataSource;

public class DataBase {
    private static final HikariDataSource dataSource ;

    private final static String url =
            System.getenv().getOrDefault("DB_URL", "jdbc:postgresql://localhost:5432/library");
    private final static String user =
            System.getenv().getOrDefault("DB_USER", "postgres");
    private static final String password =
            System.getenv("DB_PASSWORD");
    static {

        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(url);
        config.setUsername(user);
        config.setPassword(password);

        config.setMaximumPoolSize(10);
        config.setMinimumIdle(2);
        config.setConnectionTimeout(30000);
        config.setIdleTimeout(600000);
        config.setPoolName("library-pool");

        dataSource = new HikariDataSource(config);
    }

    public static DataSource getDataSource() {
        return dataSource;
    }
}
