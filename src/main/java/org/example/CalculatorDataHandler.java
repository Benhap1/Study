package org.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CalculatorDataHandler {
    // JDBC URL, username, and password of MySQL server
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/calculator";
    private static final String USER = "root";
    private static final String PASSWORD = "Benjamin1*";

    // SQL statement to insert data
    private static final String INSERT_DATA_SQL = "INSERT INTO calculations (number1, number2, operation, result) VALUES (?, ?, ?, ?)";

    // Method to save data to database
    public static void saveData(int number1, int number2, String operation, double result) {
        try {
            // Establishing connection
            Connection connection = DriverManager.getConnection(JDBC_URL, USER, PASSWORD);

            // Preparing statement for data insertion
            try (PreparedStatement preparedStatement = connection.prepareStatement(INSERT_DATA_SQL)) {
                preparedStatement.setInt(1, number1);
                preparedStatement.setInt(2, number2);
                preparedStatement.setString(3, operation);
                preparedStatement.setDouble(4, result);

                // Executing insertion
                preparedStatement.executeUpdate();
            }

            // Closing connection
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
