package controllers.BorrowRecord;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import utils.DatabaseConnection;

import java.sql.*;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;


public class BorrowController extends BorrowAndReturn {
    @FXML
    private TextField documentIdField;
    @FXML
    private TextField memberIdField;
    @FXML
    private TextField quantityField;
    @FXML
    private DatePicker borrowDate;
    @FXML
    private DatePicker dueDate;
    @FXML
    private Label errorDoc;
    @FXML
    private Label errorMem;
    @FXML
    private Label errorQuantity;
    @FXML
    private Label errorDate;

    @FXML
    private void initialize() {
        // Thiết lập borrowDate là ngày hiện tại
        borrowDate.setValue(LocalDate.now());

        // Thiết lập dueDate là một tuần sau ngày hiện tại
        dueDate.setValue(LocalDate.now().plusDays(7));

        documentIdField.setOnAction(event -> fetchDocumentTitle(documentIdField, errorDoc));

        // Xử lý khi người dùng rời khỏi TextField
        documentIdField.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) { // Chỉ thực hiện khi mất focus
                fetchDocumentTitle(documentIdField, errorDoc);
            }
        });

        memberIdField.setOnAction(event -> fetchMemberName(memberIdField, errorMem));

        // Xử lý khi người dùng rời khỏi TextField
        memberIdField.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) { // Chỉ thực hiện khi mất focus
                fetchMemberName(memberIdField, errorMem);
            }
        });

        dueDate.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) { // Chỉ thực hiện khi mất focus
                fetchDateValid();
            }
        });

        borrowDate.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) { // Chỉ thực hiện khi mất focus
                fetchDateValid();
            }
        });

        quantityField.setOnAction(event -> fetchQuantity());

        // Xử lý khi người dùng rời khỏi TextField
        quantityField.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) { // Chỉ thực hiện khi mất focus
                fetchQuantity();
            }
        });
    }

    private void fetchDateValid() {
        LocalDate borDate = borrowDate.getValue();
        LocalDate duDate = dueDate.getValue();
        errorDate.setText("");
        long diffDate = ChronoUnit.DAYS.between(borDate, duDate);
        if (diffDate > 14){
            errorDate.setText("Books can only be borrowed within 14 days, please re-enter!");
        } else if (diffDate < 0){
            errorDate.setText("The book return date cannot be less than the book's borrow date, please re-enter!");
        }
    }

    private void fetchQuantity() {
        String quantity = quantityField.getText();
        // Xóa thông báo cũ (nếu có)
        errorQuantity.setText("");

        if (quantity.isEmpty()) {
            return;
        }
        // Kiểm tra nếu ID không phải số hợp lệ
        if (!quantity.matches("\\d+")) {
            errorQuantity.setText("Invalid quantity! Please enter a valid number.");
            errorQuantity.setStyle("-fx-text-fill: red; -fx-font-weight: bold;"); // Tô đỏ thông báo lỗi
        }
    }

    private boolean checkQuantity(int quantity, int quantityBorrow) {
        if (quantityBorrow <= 0) {
            errorQuantity.setText("The number of documents you want to borrow must be greater than 0");
            return false;
        }
        else if (quantityBorrow > quantity) {
            if (quantity > 1) {
                errorQuantity.setText("The library does not have enough books for you to borrow.\nThere are only "
                        + quantity + " books left. Please re-enter the number of books you want to borrow.");
            } else if (quantity == 1){
                errorQuantity.setText("The library does not have enough documents for you to borrow.\n" +
                                "There are only 1 document left. Please re-enter.");
            } else if (quantity == 0) {
                errorQuantity.setText("The library is out of documents you want to borrow.\nPlease come back later.");
            } else {
                errorQuantity.setText("Oops, there was an error, please enter again.");
            }
            return false;
        }
        return true;
    }

    private boolean checkValidDate() {
        LocalDate borDate = borrowDate.getValue();
        LocalDate duDate = dueDate.getValue();
        long diffDate = ChronoUnit.DAYS.between(borDate, duDate);
        return diffDate <= 14 && diffDate >= 0;
    }

    @FXML
    private void onSubmit() {
        boolean cksDocId = checkDocId(documentIdField);
        boolean cksMemId = checkMemId(memberIdField);
        if (cksDocId && cksMemId) {
            int documentId = Integer.parseInt(documentIdField.getText());
            int memberId = Integer.parseInt(memberIdField.getText());
            int quantityBorrow = Integer.parseInt(quantityField.getText());
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
                    String checkDocQuery = "SELECT quantity FROM documents WHERE id = ?";
                    checkStmt = connection.prepareStatement(checkDocQuery);
                    checkStmt.setInt(1, documentId);
                    resultSet = checkStmt.executeQuery();

                    if (resultSet.next()) {
                        int quantity = resultSet.getInt("quantity");
                        boolean cksQuantity = checkQuantity(quantity, quantityBorrow);
                        boolean cksDate = checkValidDate();
                        if (quantity > 0 && cksQuantity && cksDate) {
                            // Thêm bản ghi mượn vào bảng Borrow_Records
                            String borrowQuery = "INSERT INTO Borrow_Records (document_id, member_id, borrow_date, due_date, status, quantity, quantity_borrow) VALUES (?, ?, ?, ?, 'borrowed', ?, ?)";
                            borrowStmt = connection.prepareStatement(borrowQuery);
                            borrowStmt.setInt(1, documentId);
                            borrowStmt.setInt(2, memberId);
                            borrowStmt.setDate(3, Date.valueOf(borrowDate.getValue()));
                            borrowStmt.setDate(4, Date.valueOf(dueDate.getValue()));
                            borrowStmt.setInt(5, quantityBorrow);
                            borrowStmt.setInt(6, quantityBorrow);
                            borrowStmt.executeUpdate();

                            // Cập nhật lại số lượng sách trong bảng Books
                            String updateQuery = "UPDATE documents SET quantity = quantity - ? WHERE id = ?";
                            updateStmt = connection.prepareStatement(updateQuery);
                            updateStmt.setInt(1, quantityBorrow);
                            updateStmt.setInt(2, documentId);
                            updateStmt.executeUpdate();
                            connection.commit();  // Lưu thay đổi sau khi tất cả lệnh SQL thành công
                            showAlert(Alert.AlertType.INFORMATION, "Success", "You have successfully borrowed the book!");
                            errorDate.setText("");
                            errorDoc.setText("");
                            errorMem.setText("");
                            errorQuantity.setText("");
                        } else if (!cksQuantity) {
                            if (quantity > 1) {
                                showAlert(Alert.AlertType.WARNING, "Unavailable",
                                        "The library does not have enough documents for you to borrow. There are only "
                                        + quantity + " documents left. Please re-enter.");
                            } else if (quantity == 1){
                                showAlert(Alert.AlertType.WARNING, "Unavailable",
                                        "The library does not have enough documents for you to borrow. " +
                                        "There are only 1 document left. Please re-enter.");
                            } else if (quantity == 0) {
                                showAlert(Alert.AlertType.WARNING, "Unavailable",
                                        "The library is out of documents you want to borrow. Please come back later.");
                            } else {
                                showAlert(Alert.AlertType.ERROR, "Error", "Oops, there was an error, please enter again.");
                            }
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
        } else if (!cksDocId){
            showAlert(Alert.AlertType.ERROR, "Error", "Document id must be a positive integer");
        } else {
            showAlert(Alert.AlertType.ERROR, "Error", "Member id must be a positive integer");
        }
    }

}
