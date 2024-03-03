package org.example;

import java.sql.*;

public class DatabaseReader {

    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/calculator";
        String user = "root";
        String password = "Benjamin1*";

        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            String sql = "SELECT * FROM calculations";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);

            // Выводим заголовки столбцов
            ResultSetMetaData metaData = resultSet.getMetaData();
            int columnCount = metaData.getColumnCount();
            for (int i = 1; i <= columnCount; i++) {
                System.out.print(padRight(metaData.getColumnName(i), 10)); // Выравнивание на 10 символов
            }
            System.out.println();

            // Выводим данные из таблицы
            while (resultSet.next()) {
                for (int i = 1; i <= columnCount; i++) {
                    System.out.print(padRight(resultSet.getString(i), 10)); // Выравнивание на 10 символов
                }
                System.out.println();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Метод для добавления пробелов в конце строки для выравнивания
    private static String padRight(String s, int n) {
        return String.format("%-" + n + "s", s);
    }
}

