package main.com.quizapp.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseHelper {


    private static final String DB_URL = "jdbc:mysql://localhost:3306/quiz_app"; // Change your database URL if necessary
    private static final String USER = "root";
    private static final String PASSWORD = "root";
    private static Connection connection;


    public static Connection getConnection() throws SQLException {
        if (connection == null) {
            try {

                Class.forName("com.mysql.cj.jdbc.Driver");

                connection = DriverManager.getConnection(DB_URL, USER, PASSWORD);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
                throw new SQLException("MySQL JDBC Driver not found.");
            }
        }
        return connection;
    }


    public static void closeConnection() throws SQLException {
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
    }
}
