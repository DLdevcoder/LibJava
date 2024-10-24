
package controllers;

import utils.DatabaseConnection;

import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.util.Objects;
import java.util.Scanner;

public class BorrowRecordController {
    /**
     * Kiểm tra id được nhập.
     */
    public static int checkValidId(Scanner scanner) {
        int res = 0;
        boolean checkValidId = false;
        while (!checkValidId) {
            String input = scanner.next();
            try {
                res = Integer.parseInt(input);
                if (res > 0) {
                    checkValidId = true;
                } else {
                    System.out.print("ID must be greater than 0. Please try again: ");
                }
            } catch (NumberFormatException e) {
                System.out.print("Invalid ID. Please try again: ");
            }
        }
        return res;
    }

    /**
     * Kiểm tra ngay được nhập
     */
    public static String checkValidDate(Scanner scanner, LocalDate curDate) {
        String dateInput = scanner.next();
        boolean isValidDate = false;

        while (!isValidDate) {
            try {
                LocalDate date = LocalDate.parse(dateInput);
                long diffDate = ChronoUnit.DAYS.between(curDate, date);
                if (diffDate <= 14 && diffDate >=0) {
                    isValidDate = true;
                } else if (diffDate > 14){
                    System.out.print("Books can only be borrowed within 14 days, please re-enter: ");
                    dateInput = scanner.next();
                } else {
                    System.out.print("The book return date cannot be less than the book's borrow date, please re-enter: ");
                    dateInput = scanner.next();
                }
            } catch (DateTimeParseException e) {
                System.out.print("Invalid date entered! Please re-enter in yyyy-MM-dd format: ");
                dateInput = scanner.next();
            }
        }

        return dateInput;
    }

    public static int checkQuantity(int quantity, int quantityBorrow, Scanner scanner) {
        if (quantityBorrow > quantity) {
            if (quantity > 1) {
                System.out.print("The library does not have enough books for you to borrow. There are only "
                        + quantity + " books left. Please re-enter the number of books you want to borrow: ");
            } else {
                System.out.print("The library does not have enough books for you to borrow. " +
                        "There are only 1 books left. Please re-enter the number of books you want to borrow: ");
            }
            quantityBorrow = scanner.nextInt();
        }
        return quantityBorrow;
    }

    /**
     * Mượn tài liệu.
     */
    public static boolean borrowDocument() {
        Connection connection = null;
        PreparedStatement borrowStmt = null;
        PreparedStatement updateStmt = null;
        ResultSet resultSet = null;
        try {
            //Nhập lệnh.
            Scanner scanner = new Scanner(System.in);
            System.out.print("Please enter book id: ");
            int bookId = checkValidId(scanner);
            scanner.nextLine();
            System.out.print("Please enter your id: ");
            int memberId = checkValidId(scanner);
            scanner.nextLine();
            System.out.print("Please enter the number of books you want to borrow: ");
            int quantityBorrow = scanner.nextInt();
            LocalDate borrowDate = LocalDate.now();
            System.out.print("Please enter the date you returned the book (yyyy-MM-dd format): ");
            String dueDateInput = checkValidDate(scanner, borrowDate);
            LocalDate dueDate = LocalDate.parse(dueDateInput);
            // Tạo kết nối Database.
            connection = DatabaseConnection.getConnection();
            connection.setAutoCommit(false);

            //Kiểm tra xem id người dùng có hợp lệ không.
            String checkMemberId = "SELECT member_id FROM members WHERE member_id = ?";
            PreparedStatement checkStmt = connection.prepareStatement(checkMemberId);
            checkStmt.setInt(1, memberId);
            resultSet = checkStmt.executeQuery();
            if (resultSet.next()) {
                // Kiểm tra xem sách có tồn tại và có sẵn không
                String checkBookQuery = "SELECT quantity FROM Books WHERE id = ?";
                checkStmt = connection.prepareStatement(checkBookQuery);
                checkStmt.setInt(1, bookId);
                resultSet = checkStmt.executeQuery();

                if (resultSet.next()) {
                    int quantity = resultSet.getInt("quantity");
                    if (quantity > 0) {
                        quantityBorrow = checkQuantity(quantity, quantityBorrow, scanner);
                        // Thêm bản ghi mượn vào bảng Borrow_Records
                        String borrowQuery = "INSERT INTO Borrow_Records (document_id, member_id, borrow_date, due_date, status, quantity) VALUES (?, ?, ?, ?, 'borrowed', ?)";
                        borrowStmt = connection.prepareStatement(borrowQuery);
                        borrowStmt.setInt(1, bookId);
                        borrowStmt.setInt(2, memberId);
                        borrowStmt.setDate(3, Date.valueOf(borrowDate));
                        borrowStmt.setDate(4, Date.valueOf(dueDate));
                        borrowStmt.setInt(5, quantityBorrow);
                        borrowStmt.executeUpdate();

                        // Cập nhật lại số lượng sách trong bảng Books
                        String updateQuery = "UPDATE Books SET quantity = quantity - ? WHERE id = ?";
                        updateStmt = connection.prepareStatement(updateQuery);
                        updateStmt.setInt(1, quantityBorrow);
                        updateStmt.setInt(2, bookId);
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
            } else {
                System.out.println("Sorry, we couldn't find your id.");
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

    /**
     * Trả tài liệu.
     */
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
            System.out.print("Please enter the number of books you want to return: ");
            int quantityReturn = scanner.nextInt();
            //Tạo kết nối database.
            connection = DatabaseConnection.getConnection();
            connection.setAutoCommit(false);

            String checkBorrowQuery = "SELECT status, quantity FROM Borrow_Records WHERE document_id = ? AND member_id = ? AND status = 'borrowed'";
            PreparedStatement checkStmt = connection.prepareStatement(checkBorrowQuery);
            checkStmt.setInt(1,bookId);
            checkStmt.setInt(2,memberId);
            resultSet = checkStmt.executeQuery();
            if (resultSet.next()) {
                int quantity = resultSet.getInt("quantity");
                String status = resultSet.getString("status");
                if (Objects.equals(status, "borrowed")) {
                    if (quantityReturn > quantity) {
                        while (quantityReturn > quantity && quantityReturn > 0) {
                            if (quantity > 1) {
                                System.out.print("You have " + quantity +
                                        " books left to return, please re-enter the number of books you want to return:");
                            } else if (quantity == 1) {
                                System.out.print("You have 1 book left to return, please re-enter the number of books you want to return:");
                            }
                            quantityReturn = scanner.nextInt();
                        }
                    }
                    LocalDate returnDate = LocalDate.now();

                    String returnQuery = "UPDATE Borrow_Records SET status = ?, return_date = ?, quantity = ? WHERE document_id = ? AND member_id = ? AND status = 'borrowed' LIMIT 1";
                    returnStmt = connection.prepareStatement(returnQuery);
                    if (quantity == quantityReturn) {
                        returnStmt.setString(1, "returned");
                    } else {
                        returnStmt.setString(1, "borrowed");
                    }
                    returnStmt.setDate(2, Date.valueOf(returnDate));
                    returnStmt.setInt(3, quantity - quantityReturn);
                    returnStmt.setInt(4,bookId);
                    returnStmt.setInt(5,memberId);
                    returnStmt.executeUpdate();

                    // Cập nhật lại số lượng sách trong bảng Books
                    String updateQuery = "UPDATE Books SET quantity = quantity + ? WHERE id = ?";
                    updateStmt = connection.prepareStatement(updateQuery);
                    updateStmt.setInt(1, quantityReturn);
                    updateStmt.setInt(2, bookId);
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
