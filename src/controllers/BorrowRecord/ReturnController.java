package controllers.BorrowRecord;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import utils.DatabaseConnection;

import java.sql.*;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class ReturnController extends SidebarController implements BorrowAndReturn {
    @FXML
    private TextField documentIdField;
    @FXML
    private TextField memberIdField;
    @FXML
    private TextField quantityField;
    @FXML
    private DatePicker returnDate;
    @FXML
    private Label errorDoc;
    @FXML
    private Label errorMem;
    @FXML
    private Label errorQuantity;
    @FXML
    private Label errorDate;

    /**
     * Hiển thị bảng trả sách.
     */
    @FXML
    private void initialize() {
        // Thiết lập borrowDate là ngày hiện tại
        returnDate.setValue(LocalDate.now());

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

        quantityField.setOnAction(event -> fetchQuantity(quantityField, errorQuantity));

        // Xử lý khi người dùng rời khỏi TextField
        quantityField.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) { // Chỉ thực hiện khi mất focus
                fetchQuantity(quantityField, errorQuantity);
            }
        });
    }

    /**
     * Hiển thị thông báo về ngày trả về.
     * @param borrowDate
     * @param returnDate
     * @return
     */
    private boolean checkValidDateReturn(LocalDate borrowDate, LocalDate returnDate) {
        long diffDate = ChronoUnit.DAYS.between(borrowDate, returnDate);
        errorDate.setText("");
        if (diffDate < 0) {
            errorDate.setText("The return date must be greater than the borrow date.");
            return false;
        }
        return true;
    }

    /**
     * kiểm tra số lượng nhập.
     * @param quantity
     * @param quantityReturn
     * @return
     */
    private boolean checkQuantity(int quantity, int quantityReturn) {
        if (quantityReturn > quantity) {
            if (quantity > 1) {
                errorQuantity.setText("You have " + quantity +
                        " books left to return, please re-enter the number of books you want to return.");
            } else if (quantity == 1) {
                errorQuantity.setText("You have 1 book left to return, please re-enter the number of books you want to return.");
            }
            return false;
        }
        return true;
    }

    /**
     * Đưa ra trạng thái trả về.
     * @param dueDate
     * @param returnDate
     * @return
     */
    private String dateReturned(LocalDate dueDate, LocalDate returnDate) {
        long diffDate = ChronoUnit.DAYS.between(dueDate, returnDate);
        if (diffDate == 0) {
            return "returned on time";
        } else if (diffDate > 0) {
            return "returned late";
        } else {
            return "returned soon";
        }
    }

    /**
     * Trả sách.
     */
    @FXML
    private void onSubmit() {
        boolean cksDocId = checkDocId(documentIdField);
        boolean cksMemId = checkMemId(memberIdField);
        if (cksDocId && cksMemId) {
            int documentId = Integer.parseInt(documentIdField.getText());
            int memberId = Integer.parseInt(memberIdField.getText());
            int quantityReturn = Integer.parseInt(quantityField.getText());
            PreparedStatement returnStmt = null;
            PreparedStatement updateStmt = null;
            Connection connection = null;
            ResultSet resultSet = null;

            try {
                connection = DatabaseConnection.getConnection();
                connection.setAutoCommit(false);

                String checkBorrowQuery = "SELECT status, quantity, borrow_date, due_date FROM Borrow_Records WHERE document_id = ? AND member_id = ? AND status = 'borrowed'";
                PreparedStatement checkStmt = connection.prepareStatement(checkBorrowQuery);
                checkStmt.setInt(1,documentId);
                checkStmt.setInt(2,memberId);
                resultSet = checkStmt.executeQuery();
                if (resultSet.next()) {
                    int quantity = resultSet.getInt("quantity");
                    String status = resultSet.getString("status");
                    LocalDate borrowDate = resultSet.getDate("borrow_date").toLocalDate();
                    LocalDate dueDate = resultSet.getDate("due_date").toLocalDate();
                    boolean cksQuantity = checkQuantity(quantity, quantityReturn);
                    boolean cksReturnDate = checkValidDateReturn(borrowDate, returnDate.getValue());
                    if (status.equals("borrowed") && cksQuantity && cksReturnDate) {

                        String returnQuery = "UPDATE Borrow_Records SET status = ?, return_date = ?, quantity = ? WHERE document_id = ? AND member_id = ? AND status = 'borrowed' LIMIT 1";
                        returnStmt = connection.prepareStatement(returnQuery);
                        if (quantity == quantityReturn) {
                            String statusStr = dateReturned(dueDate, returnDate.getValue());
                            returnStmt.setString(1, statusStr);
                        } else {
                            returnStmt.setString(1, "borrowed");
                        }
                        returnStmt.setDate(2, Date.valueOf(returnDate.getValue()));
                        returnStmt.setInt(3, quantity - quantityReturn);
                        returnStmt.setInt(4,documentId);
                        returnStmt.setInt(5,memberId);
                        returnStmt.executeUpdate();

                        // Cập nhật lại số lượng sách trong bảng Books
                        String updateQuery = "UPDATE Books SET quantity = quantity + ? WHERE id = ?";
                        updateStmt = connection.prepareStatement(updateQuery);
                        updateStmt.setInt(1, quantityReturn);
                        updateStmt.setInt(2, documentId);
                        updateStmt.executeUpdate();

                        connection.commit();
                        showAlert(Alert.AlertType.INFORMATION, "Success", "You have successfully returned the book!");
                        errorDate.setText("");
                        errorDoc.setText("");
                        errorMem.setText("");
                        errorQuantity.setText("");

                    } else if (!cksReturnDate) {
                        showAlert(Alert.AlertType.WARNING, "Invalid", "The return date must be greater than the borrow date.");
                    } else if (!cksQuantity) {
                        if (quantity > 1) {
                            showAlert(Alert.AlertType.WARNING, "Invalid", "You have " + quantity +
                                    " documents left to return, please re-enter the number of documents you want to return.");
                        } else if (quantity == 1) {
                            showAlert(Alert.AlertType.WARNING, "Invalid", "You have 1 document left to return, please re-enter the number of documents you want to return.");
                        }
                    } else {
                        showAlert(Alert.AlertType.ERROR,"Error", "You have been returned book.");

                    }
                } else {
                    showAlert(Alert.AlertType.ERROR,"Error","The data you borrowed was not found.");
                }
            } catch (SQLException e) {
                showAlert(Alert.AlertType.ERROR, "Error", "An error occurred: " + e.getMessage());
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
        } else if (!cksDocId){
            showAlert(Alert.AlertType.ERROR, "Error", "Document id must be a positive integer");
        } else {
            showAlert(Alert.AlertType.ERROR, "Error", "Member id must be a positive integer");
        }
    }

}
