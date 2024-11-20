package controllers.borrow_record;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import utils.DatabaseConnection;

import java.nio.file.Paths;
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

        documentIdField.setOnAction(event -> fetchDocumentTitle());

        // Xử lý khi người dùng rời khỏi TextField
        documentIdField.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) { // Chỉ thực hiện khi mất focus
                fetchDocumentTitle();
            }
        });

        memberIdField.setOnAction(event -> fetchMemberName());

        // Xử lý khi người dùng rời khỏi TextField
        memberIdField.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) { // Chỉ thực hiện khi mất focus
                fetchMemberName();
            }
        });

        quantityBorrowField.setOnAction(event -> fetchQuantity());

        // Xử lý khi người dùng rời khỏi TextField
        quantityBorrowField.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) { // Chỉ thực hiện khi mất focus
                fetchQuantity();
            }
        });
    }

    //Xử lý documentIdField
    private void fetchDocumentTitle() {
        String documentIdText = documentIdField.getText();
        // Xóa thông báo cũ (nếu có)
        errorDoc.setText("");

        if (documentIdText.isEmpty()) {
            return;
        }
        // Kiểm tra nếu ID không phải số hợp lệ
        if (!documentIdText.matches("\\d+")) {
            errorDoc.setText("Invalid document ID! Please enter a valid number.");
            errorDoc.setStyle("-fx-text-fill: red; -fx-font-weight: bold;"); // Tô đỏ thông báo lỗi
            return;
        }

        int documentId = Integer.parseInt(documentIdText);

        try (Connection connection = DatabaseConnection.getConnection()) {
            // Truy vấn tên tài liệu từ bảng documents
            String query = "SELECT title FROM documents WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, documentId);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                String title = resultSet.getString("title");
                errorDoc.setText("Document Title: " + title); // Hiển thị tên tài liệu
                errorDoc.setStyle("-fx-text-fill: green; -fx-font-weight: bold;");
            } else {
                errorDoc.setText("Document not found!");
                errorDoc.setStyle("-fx-text-fill: orange; -fx-font-weight: bold;");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            errorDoc.setText("Error fetching document title.");
            errorDoc.setStyle("-fx-text-fill: red; -fx-font-weight: bold;");
        }
    }

    //Xử lý memberIdField
    private void fetchMemberName() {
        String memberIdText = memberIdField.getText();
        // Xóa thông báo cũ (nếu có)
        errorMem.setText("");

        if (memberIdText.isEmpty()) {
            return;
        }
        // Kiểm tra nếu ID không phải số hợp lệ
        if (!memberIdText.matches("\\d+")) {
            errorMem.setText("Invalid document ID! Please enter a valid number.");
            errorMem.setStyle("-fx-text-fill: red; -fx-font-weight: bold;"); // Tô đỏ thông báo lỗi
            return;
        }

        int memberId = Integer.parseInt(memberIdText);

        try (Connection connection = DatabaseConnection.getConnection()) {
            // Truy vấn tên tài liệu từ bảng documents
            String query = "SELECT name FROM members WHERE member_id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, memberId);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                String name = resultSet.getString("name");
                errorMem.setText("Member name: " + name); // Hiển thị tên tài liệu
                errorMem.setStyle("-fx-text-fill: green; -fx-font-weight: bold;");
            } else {
                errorMem.setText("Member not found!");
                errorMem.setStyle("-fx-text-fill: orange; -fx-font-weight: bold;");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            errorMem.setText("Error fetching member name.");
            errorMem.setStyle("-fx-text-fill: red; -fx-font-weight: bold;");
        }
    }

    private void fetchQuantity() {
        String quantity = quantityBorrowField.getText();
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

    // Phương thức kiểm tra ID hợp lệ
    public boolean checkDocId() {
        String input = documentIdField.getText();
        if (!input.isEmpty()) {
            int res = Integer.parseInt(input);
            return res > 0;
        }
        return false;
    }

    public boolean checkMemId() {
        String input = memberIdField.getText();
        if (!input.isEmpty()) {
            int res = Integer.parseInt(input);
            return res > 0;
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
                errorQuantity.setText("The library does not have enough books for you to borrow.\nThere are only "
                        + quantity + " books left. Please re-enter the number of books you want to borrow.");
            } else {
                errorQuantity.setText("The library does not have enough books for you to borrow.\n" +
                        "There are only 1 books left. Please re-enter the number of books you want to borrow.");
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
                            if (quantity > 1) {
                                showAlert(Alert.AlertType.WARNING, "Unavailable", "The library does not have enough documents for you to borrow. There are only "
                                        + quantity + " documents left. Please re-enter.");
                            } else {
                                showAlert(Alert.AlertType.WARNING, "Unavailable", "The library does not have enough documents for you to borrow. " +
                                        "There are only 1 document left. Please re-enter.");
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
        }
    }

    // Hàm tiện ích để hiển thị các thông báo
    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.getStylesheets().add(Paths.get("src/resources/stylesheet/Alert.css").toUri().toString());
        dialogPane.getStyleClass().add("dialog-pane");
        alert.showAndWait();
    }

}
