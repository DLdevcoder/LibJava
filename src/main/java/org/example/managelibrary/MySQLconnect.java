package org.example.managelibrary;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySQLconnect {
    private static final String URL = "jdbc:mysql://localhost:3306/LibraryManagement";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    /**
     * Tạo kết nối với mysql
     */
    public static Connection getConnection() {
        Connection connection = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Connection successful!");
        } catch (ClassNotFoundException e) {
            System.out.println("MySQL JDBC Driver not found.");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("Connection failed.");
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println("Failed to connect");
        }
        return connection;
    }

    public static void main(String[] args) {
        getConnection();
    }
}
