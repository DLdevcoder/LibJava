package utils;

import java.sql.*;

public class DatabaseConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/LibraryManagement";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    private static Connection connection;

    private DatabaseConnection() {
    }

    public static Connection getConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException var2) {
            ClassNotFoundException e = var2;
            System.out.println("MySQL JDBC Driver not found.");
            e.printStackTrace();
        } catch (SQLException var3) {
            SQLException e = var3;
            System.out.println("Connection failed.");
            e.printStackTrace();
        } catch (Exception var4) {
            System.out.println("Failed to connect");
        }

        return connection;
    }

    private static boolean checkExists(String query, int id) throws SQLException {
        DatabaseConnection databaseConnection = new DatabaseConnection();
        try (Connection connection = databaseConnection.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt(1) > 0;
                }
            }

        }
        return false;


    }

    public static boolean checkBookExists(int bookId) throws SQLException {
        String query = "SELECT COUNT(*) FROM books WHERE id = ?";
        return checkExists(query, bookId);

    }

    public static boolean checkMemberExists(int memberId) throws SQLException {
        String query = "SELECT COUNT(*) FROM members WHERE member_id = ?";
        return checkExists(query, memberId);

    }
}

