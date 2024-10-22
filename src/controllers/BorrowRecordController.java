
package controllers;

import utils.DatabaseConnection;

import java.sql.*;
import java.time.LocalDate;
import java.util.Objects;
import java.util.Scanner;

public class BorrowRecordController {
    public static boolean borrowDocument() {
        Connection connection = null;
        PreparedStatement borrowStmt = null;
        PreparedStatement updateStmt = null;
        ResultSet resultSet = null;
        try {
            //Nhập lệnh.
            Scanner scanner = new Scanner(System.in);
            System.out.print("Please enter book id: ");
            int bookId = scanner.nextInt();
            scanner.nextLine();
            System.out.print("Please enter your id: ");
            int memberId = scanner.nextInt();
            scanner.nextLine();
            System.out.print("Please enter the date you returned the book (yyyy-MM-dd format): ");
            String dueDateInput = scanner.nextLine();

            //Kiểm tra xem ngày nhập có hợp lệ không.
            boolean checkValidDate = false;
            while (!checkValidDate) {
                try {
                    dueDateInput = String.valueOf(LocalDate.parse(dueDateInput));
                    checkValidDate = true;
                } catch (java.time.format.DateTimeParseException e) {
                    System.out.println("Invalid date entered! Please re-enter in yyyy-MM-dd format: ");
                    dueDateInput = scanner.nextLine();
                }
            }
            LocalDate dueDate = LocalDate.parse(dueDateInput);

            // Tạo kết nối Database.
            connection = DatabaseConnection.getConnection();
            connection.setAutoCommit(false);

            // Kiểm tra xem sách có tồn tại và có sẵn không
            String checkBookQuery = "SELECT quantity FROM Books WHERE id = ?";
            PreparedStatement checkStmt = connection.prepareStatement(checkBookQuery);
            checkStmt.setInt(1, bookId);
            resultSet = checkStmt.executeQuery();

            if (resultSet.next()) {
                int quantity = resultSet.getInt("quantity");
                if (quantity > 0) {
                    LocalDate borrowDate = LocalDate.now();
                    // Thêm bản ghi mượn vào bảng Borrow_Records
                    String borrowQuery = "INSERT INTO Borrow_Records (id, member_id, borrow_date, due_date, status) VALUES (?, ?, ?, ?, 'borrowed')";
                    borrowStmt = connection.prepareStatement(borrowQuery);
                    borrowStmt.setInt(1, bookId);
                    borrowStmt.setInt(2, memberId);
                    borrowStmt.setDate(3, Date.valueOf(borrowDate));
                    borrowStmt.setDate(4, Date.valueOf(dueDate));
                    borrowStmt.executeUpdate();

                    // Cập nhật lại số lượng sách trong bảng Books
                    String updateQuery = "UPDATE Books SET quantity = quantity - 1 WHERE id = ?";
                    updateStmt = connection.prepareStatement(updateQuery);
                    updateStmt.setInt(1, bookId);
                    updateStmt.executeUpdate();
                    connection.commit();
                    System.out.println("You have successfully borrowed the book!");
                    return true;
                } else {
                    System.out.println("Sorry, the library is out of the book you requested.");
                    return false;
                }
            } else {
                System.out.println("Books do not exist.");
                return false;
            }

        } catch (SQLException e) {
            System.out.println("Error.");
            e.printStackTrace();
            try {
                if (connection != null) {
                    connection.rollback(); // Rollback nếu có lỗi
                }
            } catch (SQLException se) {
                se.printStackTrace();
            }
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (borrowStmt != null) {
                    borrowStmt.close();
                }
                if (updateStmt != null) {
                    updateStmt.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public static boolean returnDocument() {
        PreparedStatement returnStmt = null;
        PreparedStatement updateStmt = null;
        Connection connection = null;
        ResultSet resultSet = null;
        Scanner scanner = new Scanner(System.in);
        try {
            //Nhập lệnh.
            System.out.print("Please enter book ID: ");
            int bookId = scanner.nextInt();
            scanner.nextLine();
            System.out.print("Please enter your ID: ");
            int memberId = scanner.nextInt();

            //Tạo kết nối database.
            connection = DatabaseConnection.getConnection();
            connection.setAutoCommit(false);

            String checkBorrowQuery = "SELECT status FROM Borrow_Records WHERE book_id = ? AND member_id = ?";
            PreparedStatement checkStmt = connection.prepareStatement(checkBorrowQuery);
            checkStmt.setInt(1,bookId);
            checkStmt.setInt(2,memberId);
            resultSet = checkStmt.executeQuery();
            if (resultSet.next()) {
                String status = resultSet.getString("status");
                if (Objects.equals(status, "borrowed")) {
                    LocalDate returnDate = LocalDate.now();

                    String returnQuery = "UPDATE Borrow_Records SET status = 'returned', return_date = ? WHERE book_id = ? AND member_id = ? LIMIT 1";
                    returnStmt = connection.prepareStatement(returnQuery);
                    returnStmt.setDate(1, Date.valueOf(returnDate));
                    returnStmt.setInt(2,bookId);
                    returnStmt.setInt(3,memberId);
                    returnStmt.executeUpdate();

                    // Cập nhật lại số lượng sách trong bảng Books
                    String updateQuery = "UPDATE Books SET quantity = quantity + 1 WHERE book_id = ?";
                    updateStmt = connection.prepareStatement(updateQuery);
                    updateStmt.setInt(1, bookId);
                    updateStmt.executeUpdate();

                    connection.commit();
                    System.out.println("You have successfully returned the book!");
                    return true;
                } else {
                    System.out.println("You have been returned book.");
                    return true;
                }
            } else {
                System.out.println("The book has not yet been borrowed.");
                return false;
            }
        } catch (SQLException e) {
            System.out.println("Error.");
            e.printStackTrace();
            try {
                if (connection != null) {
                    connection.rollback(); // Rollback nếu có lỗi
                }
            } catch (SQLException se) {
                se.printStackTrace();
            }
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (returnStmt != null) {
                    returnStmt.close();
                }
                if (updateStmt != null) {
                    updateStmt.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return false;
    }
}
