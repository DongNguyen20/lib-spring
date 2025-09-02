package com.example.quartz.config;

import org.quartz.utils.ConnectionProvider;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class SpringConnectionProvider implements ConnectionProvider {

    public static DataSource springDataSource;

    @Override
    public Connection getConnection() throws SQLException {
        return springDataSource.getConnection();
    }

    @Override
    public void shutdown() {}

    @Override
    public void initialize() {}
}