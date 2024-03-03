package org.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Main {
    // JDBC URL, username, and password of MySQL server
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/";
    private static final String USER = "root";
    private static final String PASSWORD = "Benjamin1*";

    // Database and table names
    private static final String DATABASE_NAME = "calculator";
    private static final String TABLE_NAME = "calculations";

    // SQL statement to create table
    private static final String CREATE_TABLE_SQL = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (" +
            "id INT AUTO_INCREMENT PRIMARY KEY," +
            "number1 INT NOT NULL," +
            "number2 INT NOT NULL," +
            "operation VARCHAR(255) NOT NULL," +
            "result DOUBLE" +
            ")";

    public static void main(String[] args) {
        try {
            // Establishing connection
            Connection connection = DriverManager.getConnection(JDBC_URL, USER, PASSWORD);

            // Creating database if not exists
            createDatabase(connection);

            // Switching to the created database
            connection.setCatalog(DATABASE_NAME);

            // Creating table
            createTable(connection);

            // Closing connection
            connection.close();

            System.out.println("Database and table created successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to create database if not exists
    private static void createDatabase(Connection connection) throws SQLException {
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate("CREATE DATABASE IF NOT EXISTS " + DATABASE_NAME);
        }
    }

    // Method to create table if not exists
    private static void createTable(Connection connection) throws SQLException {
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(CREATE_TABLE_SQL);
        }
    }
}
