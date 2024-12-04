package controllers.BorrowRecord;

import javafx.scene.control.Alert;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import utils.DatabaseConnection;

import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public interface BorrowAndReturn {

    /**
     * Kiểm tra và truy vấn tên tài liệu từ ID tài liệu.
     * @param documentIdField
     * @param errorDoc
     */
    static void fetchDocumentTitle(TextField documentIdField, Label errorDoc) {
        String documentIdText = documentIdField.getText();
        errorDoc.setText(""); // Xóa thông báo cũ

        if (documentIdText.isEmpty()) {
            return;
        }

        if (!documentIdText.matches("\\d+")) {
            errorDoc.setText("Invalid document ID! Please enter a valid number.");
            errorDoc.setStyle("-fx-text-fill: red; -fx-font-weight: bold;");
            return;
        }

        int documentId = Integer.parseInt(documentIdText);

        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "SELECT title FROM documents WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, documentId);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                String title = resultSet.getString("title");
                errorDoc.setText("Document Title: " + title);
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

    /**
     * Kiểm tra và truy vấn tên thành viên từ ID thành viên.
     * @param memberIdField
     * @param errorMem
     */
    static void fetchMemberName(TextField memberIdField, Label errorMem) {
        String memberIdText = memberIdField.getText();
        errorMem.setText(""); // Xóa thông báo cũ

        if (memberIdText.isEmpty()) {
            return;
        }

        if (!memberIdText.matches("\\d+")) {
            errorMem.setText("Invalid member ID! Please enter a valid number.");
            errorMem.setStyle("-fx-text-fill: red; -fx-font-weight: bold;");
            return;
        }

        int memberId = Integer.parseInt(memberIdText);

        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "SELECT name FROM members WHERE member_id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, memberId);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                String name = resultSet.getString("name");
                errorMem.setText("Member name: " + name);
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

    /**
     * Kiểm tra document ID.
     * @param documentIdField
     * @return
     */
    default boolean checkDocId(TextField documentIdField) {
        String input = documentIdField.getText();
        if (!input.isEmpty()) {
            try {
                int res = Integer.parseInt(input);
                return res > 0;
            } catch (Exception e) {
                return false;
            }
        }
        return false;
    }

    /**
     * Kiểm tra memberID.
     * @param memberIdField
     * @return
     */
    default boolean checkMemId(TextField memberIdField) {
        String input = memberIdField.getText();
        if (!input.isEmpty()) {
            try {
                int res = Integer.parseInt(input);
                return res > 0;
            } catch (Exception e) {
                return false;
            }
        }
        return false;
    }

    /**
     * Hàm tiện ích để hiển thị các thông báo.
     * @param alertType
     * @param title
     * @param message
     */
    default void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.getStylesheets().add(Paths.get("src/resources/stylesheet/Alert.css").toUri().toString());
        dialogPane.getStyleClass().add("dialog-pane");
        alert.showAndWait();
    }

    /**
     * Thông báo số lượng nhập có hợp lệ không.
     */
    default void fetchQuantity(TextField quantityField, Label errorQuantity) {
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
}
