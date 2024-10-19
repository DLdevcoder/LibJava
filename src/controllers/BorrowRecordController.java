
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
            System.out.print("Vui lòng nhập id sách: ");
            int bookId = scanner.nextInt();
            scanner.nextLine();
            System.out.print("Vui lòng nhập id của bạn: ");
            int memberId = scanner.nextInt();
            scanner.nextLine();
            System.out.print("Vui lòng nhập ngày bạn trả sách (định dạng yyyy-MM-dd): ");
            String dueDateInput = scanner.nextLine();
            //Kiểm tra xem ngày nhập có hợp lệ không.
            boolean checkValidDate = false;
            while (!checkValidDate) {
                try {
                    dueDateInput = String.valueOf(LocalDate.parse(dueDateInput));
                    checkValidDate = true;
                } catch (java.time.format.DateTimeParseException e) {
                    System.out.println("Ngày không hợp lệ! Vui lòng nhập lại theo định dạng yyyy-MM-dd:");
                    dueDateInput = scanner.nextLine();
                }
            }
            LocalDate dueDate = LocalDate.parse(dueDateInput);

            // Tạo kết nối Database.
            connection = DatabaseConnection.getConnection();
            connection.setAutoCommit(false);

            // Kiểm tra xem sách có tồn tại và có sẵn không
            String checkBookQuery = "SELECT quantity FROM Books WHERE book_id = ?";
            PreparedStatement checkStmt = connection.prepareStatement(checkBookQuery);
            checkStmt.setInt(1, bookId);
            resultSet = checkStmt.executeQuery();

            if (resultSet.next()) {
                int quantity = resultSet.getInt("quantity");

                // Nếu sách có sẵn (số lượng > 0)
                if (quantity > 0) {
                    // Lấy ngày hiện tại cho borrowDate
                    LocalDate borrowDate = LocalDate.now();

                    // Thêm bản ghi mượn vào bảng Borrow_Records
                    String borrowQuery = "INSERT INTO Borrow_Records (book_id, member_id, borrow_date, due_date, status) VALUES (?, ?, ?, ?, 'borrowed')";
                    borrowStmt = connection.prepareStatement(borrowQuery);
                    borrowStmt.setInt(1, bookId);
                    borrowStmt.setInt(2, memberId);
                    borrowStmt.setDate(3, Date.valueOf(borrowDate));  // Sử dụng LocalDate và chuyển đổi sang java.sql.Date
                    borrowStmt.setDate(4, Date.valueOf(dueDate));     // Ngày trả sách
                    borrowStmt.executeUpdate();

                    // Cập nhật lại số lượng sách trong bảng Books
                    String updateQuery = "UPDATE Books SET quantity = quantity - 1 WHERE book_id = ?";
                    updateStmt = connection.prepareStatement(updateQuery);
                    updateStmt.setInt(1, bookId);
                    updateStmt.executeUpdate();

                    // Commit giao dịch (transaction)
                    connection.commit();
                    System.out.println("Bạn đã mượn sách thành công.");
                    return true; // Thành công
                } else {
                    System.out.println("Xin lỗi nhé, thư viện đã hết sách bạn yêu cầu rồi.");
                    return false; // Sách hết
                }
            } else {
                System.out.println("Sách không tồn tại.");
                return false; // Sách không tồn tại
            }

        } catch (SQLException e) {
            System.out.println("Lỗi rồi.");
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
            System.out.print("Vui lòng nhập id sách bạn muốn trả: ");
            int bookId = scanner.nextInt();
            scanner.nextLine();
            System.out.print("Vui lòng nhập id của bạn: ");
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
                    System.out.println("Bạn đã trả sách thành công");
                    return true;
                } else {
                    System.out.println("Bạn đã trả sách rồi");
                    return true;
                }
            } else {
                System.out.println("Không thấy sách được mượn");
                return false;
            }
        } catch (SQLException e) {
            System.out.println("Lỗi rồi.");
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
