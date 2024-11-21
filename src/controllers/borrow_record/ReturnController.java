package controllers.borrow_record;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import utils.DatabaseConnection;

import java.nio.file.Paths;
import java.sql.*;
import java.time.LocalDate;

public class ReturnController extends BorrowAndReturn {
    @FXML
    private TextField documentIdField;
    @FXML
    private TextField memberIdField;
    @FXML
    private TextField quantityField;
    @FXML
    private DatePicker returnDate;
    @FXML
    private Button returnButton;
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
        returnDate.setValue(LocalDate.now());

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

        quantityField.setOnAction(event -> fetchQuantity());

        // Xử lý khi người dùng rời khỏi TextField
        quantityField.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) { // Chỉ thực hiện khi mất focus
                fetchQuantity();
            }
        });
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

    public boolean checkQuantity(int quantity, int quantityReturn) {
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

    @FXML
    public void onSubmit() {
        errorDate.setText("");
        errorDoc.setText("");
        errorMem.setText("");
        errorQuantity.setText("");
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

                String checkBorrowQuery = "SELECT status, quantity FROM Borrow_Records WHERE document_id = ? AND member_id = ? AND status = 'borrowed'";
                PreparedStatement checkStmt = connection.prepareStatement(checkBorrowQuery);
                checkStmt.setInt(1,documentId);
                checkStmt.setInt(2,memberId);
                resultSet = checkStmt.executeQuery();
                if (resultSet.next()) {
                    int quantity = resultSet.getInt("quantity");
                    String status = resultSet.getString("status");
                    boolean cksQuantity = checkQuantity(quantity, quantityReturn);
                    if (status.equals("borrowed") && cksQuantity) {

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

                    } else if (!cksQuantity) {
                        if (quantity > 1) {
                            showAlert(Alert.AlertType.WARNING, "Invalid", "You have " + quantity +
                                    " documents left to return, please re-enter the number of documents you want to return:");
                        } else if (quantity == 1) {
                            showAlert(Alert.AlertType.WARNING, "Invalid", "You have 1 document left to return, please re-enter the number of documents you want to return:");
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
        }
    }

}
