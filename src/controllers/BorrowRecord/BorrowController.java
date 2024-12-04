
package controllers.BorrowRecord;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import utils.DatabaseConnection;

import java.sql.*;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;


public class BorrowController extends SidebarController implements BorrowAndReturn {
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

    Connection connection = null;
    ResultSet resultSet = null;
    PreparedStatement checkStmt = null;

    /**
     * Hiển thị mượn sách.
     */
    @FXML
    private void initialize() {
        // Thiết lập borrowDate là ngày hiện tại
        borrowDate.setValue(LocalDate.now());

        // Thiết lập dueDate là một tuần sau ngày hiện tại
        dueDate.setValue(LocalDate.now().plusDays(7));

        documentIdField.setOnAction(event -> BorrowAndReturn.fetchDocumentTitle(documentIdField, errorDoc));

        // Xử lý khi người dùng rời khỏi TextField
        documentIdField.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) { // Chỉ thực hiện khi mất focus
                BorrowAndReturn.fetchDocumentTitle(documentIdField, errorDoc);
            }
        });

        memberIdField.setOnAction(event -> BorrowAndReturn.fetchMemberName(memberIdField, errorMem));

        // Xử lý khi người dùng rời khỏi TextField
        memberIdField.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) { // Chỉ thực hiện khi mất focus
                BorrowAndReturn.fetchMemberName(memberIdField, errorMem);
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

        quantityField.setOnAction(event -> fetchQuantity(quantityField, errorQuantity));

        // Xử lý khi người dùng rời khỏi TextField
        quantityField.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) { // Chỉ thực hiện khi mất focus
                fetchQuantity(quantityField, errorQuantity);
            }
        });
    }

    /**
     * Thông báo ngày có hợp lệ không.
     */
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

    /**
     * Kiểm tra số lượng nhập có hợp lệ không.
     */
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

    /**
     * Kiểm tra ngày có hợp lệ không.
     */
    private boolean checkValidDate() {
        LocalDate borDate = borrowDate.getValue();
        LocalDate duDate = dueDate.getValue();
        long diffDate = ChronoUnit.DAYS.between(borDate, duDate);
        return diffDate <= 14 && diffDate >= 0;
    }

    /**
     * Kiểm tra người dùng có được phép mượn sách không.
     */
    private boolean validBorrow(int memberId, int docId) throws SQLException {
        String query = "SELECT COUNT(*) FROM borrow_records WHERE member_id = ? AND document_id = ? AND status = 'borrowed'";
        try {
            PreparedStatement checkStmt = connection.prepareStatement(query);
            checkStmt.setInt(1, memberId);
            checkStmt.setInt(2, docId);
            resultSet = checkStmt.executeQuery();
            if (resultSet.next()) {
                int count = resultSet.getInt(1); // Lấy giá trị COUNT(*)
                return count == 0;
            }
        } catch (SQLException e) {
            throw new SQLException(e);
        }
        return false;
    }

    /**
     * Kiểm tra người dùng có tồn tại không.
     */
    private boolean validMember(int memberId) throws SQLException {
        try {
            String checkMemberId = "SELECT member_id FROM members WHERE member_id = ?";
            checkStmt = connection.prepareStatement(checkMemberId);
            checkStmt.setInt(1, memberId);
            resultSet = checkStmt.executeQuery();
            return resultSet.next();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Mượn sách.
     */
    @FXML
    private void onSubmit() {
        boolean cksDocId = checkDocId(documentIdField);
        boolean cksMemId = checkMemId(memberIdField);
        if (cksDocId && cksMemId) {
            int documentId = Integer.parseInt(documentIdField.getText());
            int memberId = Integer.parseInt(memberIdField.getText());
            int quantityBorrow = Integer.parseInt(quantityField.getText());
            PreparedStatement borrowStmt = null;
            PreparedStatement updateStmt = null;
            try {
                connection = DatabaseConnection.getConnection();
                connection.setAutoCommit(false);  // Tắt auto-commit
                // Kiểm tra member_id có tồn tại hay không
                boolean validMem = validMember(memberId);
                boolean validBorrowDoc = validBorrow(memberId, documentId);
                if (validMem) {
                    // Kiểm tra xem sách có tồn tại và có sẵn không
                    String checkDocQuery = "SELECT quantity FROM documents WHERE id = ?";
                    checkStmt = connection.prepareStatement(checkDocQuery);
                    checkStmt.setInt(1, documentId);
                    resultSet = checkStmt.executeQuery();
                    if (resultSet.next()) {
                        if (validBorrowDoc) {
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
                                } else if (quantity == 1) {
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
                            showAlert(Alert.AlertType.WARNING, "Invalid Borrow", "This document is being borrowed by you.");
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
