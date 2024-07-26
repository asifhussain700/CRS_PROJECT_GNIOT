package com.gniot.crs.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;

public class DBUtils {

    private static final String PROPERTIES_FILE = "./config.properties";

    public static Connection getConnection() {
        Connection connection = null;
        try (FileInputStream fis = new FileInputStream(PROPERTIES_FILE)) {
            Properties props = new Properties();
            props.load(fis);

            String url = props.getProperty("spring.datasource.url");
            String user = props.getProperty("spring.datasource.username");
            String password = props.getProperty("spring.datasource.password");

            System.out.println("Connecting to database...");
            System.out.println("URL: " + url);
            System.out.println("User: " + user);

            Class.forName(props.getProperty("spring.datasource.driver-class-name")); // Load the driver class

            connection = DriverManager.getConnection(url, user, password);
            if (connection != null) {
                System.out.println("Connected to database successfully!");
            } else {
                System.out.println("Failed to connect to database!");
            }
        } catch (IOException | SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return connection;
    }

    public static void closeResources(PreparedStatement statement, Connection connection) {
        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}