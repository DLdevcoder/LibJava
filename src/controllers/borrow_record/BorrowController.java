package controllers.borrow_record;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import utils.DatabaseConnection;

import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.util.Locale;


public class BorrowController extends SidebarController {
    @FXML
    private TextField documentIdField;
    @FXML
    private TextField memberIdField;
    @FXML
    private TextField quantityBorrowField;
    @FXML
    private DatePicker borrowDate;
    @FXML
    private DatePicker dueDate;
    @FXML
    private Button borrowButton;
    @FXML
    private Label errorDoc;
    @FXML
    private Label errorMem;
    @FXML
    private Label errorQuantity;
    @FXML
    private Label errorDate;

    @FXML
    public void initialize() {
        // Thiết lập borrowDate là ngày hiện tại
        borrowDate.setValue(LocalDate.now());

        // Thiết lập dueDate là một tuần sau ngày hiện tại
        dueDate.setValue(LocalDate.now().plusDays(7));
    }

    // Phương thức kiểm tra ID hợp lệ
    public boolean checkDocId() {
        String input = documentIdField.getText();
        try {
            if (!input.isEmpty()) {
                int res = Integer.parseInt(input);
                if (res > 0) {
                    errorDoc.setText("");
                    return true;
                } else {
                    errorDoc.setText("ID must be greater than 0.");
                }
            }
        } catch (NumberFormatException e) {
            errorDoc.setText("Invalid ID. Please enter a number.");
        }
        return false;
    }

    public boolean checkMemId() {
        String input = memberIdField.getText();
        try {
            if (!input.isEmpty()) {
                int res = Integer.parseInt(input);
                if (res > 0) {
                    errorMem.setText("");
                    return true;
                } else {
                    errorMem.setText("ID must be greater than 0.");
                }
            }
        } catch (NumberFormatException e) {
            errorMem.setText("Invalid ID. Please enter a number.");
        }
        return false;
    }

    public boolean checkQuantity(int quantity, int quantityBorrow) {
        if (quantityBorrow <= 0) {
            errorQuantity.setText("The number of documents you want to borrow must be greater than 0");
            return false;
        }
        else if (quantityBorrow > quantity) {
            if (quantity > 1) {
                errorQuantity.setText("The library does not have enough books for you to borrow. There are only "
                        + quantity + " books left. Please re-enter the number of books you want to borrow: ");
            } else {
                errorQuantity.setText("The library does not have enough books for you to borrow. " +
                        "There are only 1 books left. Please re-enter the number of books you want to borrow: ");
            }
            return false;
        }
        return true;
    }

    public boolean checkValidDate() {
        LocalDate borDate = borrowDate.getValue();
        LocalDate duDate = dueDate.getValue();
        long diffDate = ChronoUnit.DAYS.between(borDate, duDate);
        if (diffDate <= 14 && diffDate >=0) {
            return true;
        } else if (diffDate > 14){
            errorDate.setText("Books can only be borrowed within 14 days, please re-enter!");
        } else {
            errorDate.setText("The book return date cannot be less than the book's borrow date, please re-enter!");
        }
        return false;
    }

    @FXML
    public void onSubmit() {
        errorDate.setText("");
        errorDoc.setText("");
        errorMem.setText("");
        errorQuantity.setText("");
        boolean cksDocId = checkDocId();
        boolean cksMemId = checkMemId();
        if (cksDocId && cksMemId) {
            int documentId = Integer.parseInt(documentIdField.getText());
            int memberId = Integer.parseInt(memberIdField.getText());
            int quantityBorrow = Integer.parseInt(quantityBorrowField.getText());
            Connection connection = null;
            PreparedStatement borrowStmt = null;
            PreparedStatement updateStmt = null;
            ResultSet resultSet = null;

            try {
                connection = DatabaseConnection.getConnection();
                connection.setAutoCommit(false);  // Tắt auto-commit

                // Kiểm tra member_id có tồn tại hay không
                String checkMemberId = "SELECT member_id FROM members WHERE member_id = ?";
                PreparedStatement checkStmt = connection.prepareStatement(checkMemberId);
                checkStmt.setInt(1, memberId);
                resultSet = checkStmt.executeQuery();

                if (resultSet.next()) {
                    // Kiểm tra xem sách có tồn tại và có sẵn không
                    String checkBookQuery = "SELECT quantity FROM Books WHERE id = ?";
                    checkStmt = connection.prepareStatement(checkBookQuery);
                    checkStmt.setInt(1, documentId);
                    resultSet = checkStmt.executeQuery();

                    if (resultSet.next()) {
                        int quantity = resultSet.getInt("quantity");
                        boolean cksQuantity = checkQuantity(quantity, quantityBorrow);
                        boolean cksDate = checkValidDate();
                        if (quantity > 0 && cksQuantity && cksDate) {
                            // Thêm bản ghi mượn vào bảng Borrow_Records
                            String borrowQuery = "INSERT INTO Borrow_Records (document_id, member_id, borrow_date, due_date, status, quantity) VALUES (?, ?, ?, ?, 'borrowed', ?)";
                            borrowStmt = connection.prepareStatement(borrowQuery);
                            borrowStmt.setInt(1, documentId);
                            borrowStmt.setInt(2, memberId);
                            borrowStmt.setDate(3, Date.valueOf(borrowDate.getValue()));
                            borrowStmt.setDate(4, Date.valueOf(dueDate.getValue()));
                            borrowStmt.setInt(5, quantityBorrow);
                            borrowStmt.executeUpdate();

                            // Cập nhật lại số lượng sách trong bảng Books
                            String updateQuery = "UPDATE Books SET quantity = quantity - ? WHERE id = ?";
                            updateStmt = connection.prepareStatement(updateQuery);
                            updateStmt.setInt(1, quantityBorrow);
                            updateStmt.setInt(2, documentId);
                            updateStmt.executeUpdate();

                            connection.commit();  // Lưu thay đổi sau khi tất cả lệnh SQL thành công
                            showAlert(Alert.AlertType.INFORMATION, "Success", "You have successfully borrowed the book!");
                            errorQuantity.setText("");
                            errorMem.setText("");
                            errorDoc.setText("");
                        } else if (!cksQuantity) {
                            showAlert(Alert.AlertType.WARNING, "Unavailable", "Sorry, the library is out of the book you requested.");
                        } else if (!cksDate) {
                            showAlert(Alert.AlertType.WARNING, "Invalid Date", "Sorry, you cannot borrow documents for longer than 2 weeks.");
                        }
                    } else {
                        showAlert(Alert.AlertType.ERROR, "Not Found", "Books do not exist.");
                    }
                } else {
                    showAlert(Alert.AlertType.ERROR, "Not Found", "Sorry, we couldn't find your member ID.");
                }
            } catch (SQLException e) {
                if (connection != null) {
                    try {
                        connection.rollback();  // Hoàn tác thay đổi nếu có lỗi
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                }
                e.printStackTrace();
                showAlert(Alert.AlertType.ERROR, "Error", "An error occurred: " + e.getMessage());
            } finally {
                // Đóng các PreparedStatement và ResultSet để giải phóng tài nguyên
                try {
                    if (resultSet != null) resultSet.close();
                    if (borrowStmt != null) borrowStmt.close();
                    if (updateStmt != null) updateStmt.close();
                    if (connection != null) connection.setAutoCommit(true);  // Bật lại auto-commit
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    // Hàm tiện ích để hiển thị các thông báo
    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

}
