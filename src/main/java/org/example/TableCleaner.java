package org.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class TableCleaner {
    // JDBC URL, username, and password of MySQL server
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/";
    private static final String USER = "root";
    private static final String PASSWORD = "Benjamin1*";

    // Database and table names
    private static final String DATABASE_NAME = "calculator";
    private static final String TABLE_NAME = "calculations";

    public static void main(String[] args) {
        try {
            // Establishing connection
            Connection connection = DriverManager.getConnection(JDBC_URL, USER, PASSWORD);

            // Switching to the created database
            connection.setCatalog(DATABASE_NAME);

            // Clearing table
            clearTable(connection);

            // Closing connection
            connection.close();

            System.out.println("Table cleared successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to clear table
    private static void clearTable(Connection connection) throws SQLException {
        try (Statement statement = connection.createStatement()) {
            // Execute the delete statement with a WHERE clause to clear the table
            statement.executeUpdate("DELETE FROM " + TABLE_NAME);
        }
    }
}

