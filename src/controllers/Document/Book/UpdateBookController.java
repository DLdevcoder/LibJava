package controllers.Document.Book;

import controllers.Document.DocumentSideBarController;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import utils.DatabaseConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UpdateBookController extends DocumentSideBarController {

    @FXML
    private ChoiceBox<String> choiceBox;
    @FXML
    private TextField inputField;
    @FXML
    private Button updateButton;
    @FXML
    private TextField bookIdField;  // TextField để nhập ID sách
    @FXML
    private Label bookTitleLabel; // Label hiển thị title của sách

    /**
     * The initialize method is called when the controller is initialized.
     * It sets up the options in the ChoiceBox and listens for value changes.
     */
    @FXML
    public void initialize() {
        // Thêm các lựa chọn vào ChoiceBox
        choiceBox.getItems().addAll("Title", "Author", "Public Year", "Publisher", "Language", "Book Cover");

        // Lắng nghe sự thay đổi của ChoiceBox
        choiceBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                // Hiển thị TextField và thay đổi prompt text
                inputField.setVisible(true);
                inputField.setPromptText("Enter new " + newValue.toLowerCase());
                updateButton.setDisable(false); // Kích hoạt nút Update
            } else {
                inputField.setVisible(false); // Ẩn TextField
                updateButton.setDisable(true); // Vô hiệu hóa nút Update
            }
        });

        // Lắng nghe sự kiện khi người dùng nhập ID sách
        bookIdField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.isEmpty()) {
                displayBookTitle(newValue);
            } else {
                bookTitleLabel.setText(""); // Nếu không có ID sách, ẩn title
            }
        });
    }

    /**
     * Hiển thị title của sách dựa trên ID sách nhập vào.
     */
    private void displayBookTitle(String bookId) {
        try {
            String sql = "SELECT title FROM books WHERE id = ?";
            PreparedStatement statement = DatabaseConnection.getConnection().prepareStatement(sql);
            statement.setInt(1, Integer.parseInt(bookId));

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                String title = resultSet.getString("title");
                bookTitleLabel.setText("Book Title: " + title);
                bookTitleLabel.setStyle("-fx-text-fill: green;"); // Đổi màu chữ thành đỏ để nổi bật
            } else {
                bookTitleLabel.setText("No book found with this ID");
                bookTitleLabel.setStyle("-fx-text-fill: red;");
            }

            resultSet.close();
            statement.close();
        } catch (Exception e) {
            e.printStackTrace();
            bookTitleLabel.setText("Error fetching book title");
            bookTitleLabel.setStyle("-fx-text-fill: red;");
        }
    }

    /**
     * Handles the update operation when the Update button is clicked.
     * It validates input, maps the field to the database column, and updates the record.
     */
    @FXML
    private void handleUpdate() {
        String selectedField = choiceBox.getValue();
        String newValue = inputField.getText();
        String bookId = bookIdField.getText();  // Lấy ID sách từ TextField

        if (selectedField == null || newValue.isEmpty() || bookId.isEmpty()) {
            showAlert("Invalid Input", "Please select a field, enter a value, and provide a book ID.");
            return;
        }

        // Kiểm tra xem ID sách có hợp lệ không
        int bookIdInt;
        try {
            bookIdInt = Integer.parseInt(bookId);
        } catch (NumberFormatException e) {
            showAlert("Error", "Invalid book ID.");
            return;
        }

        // Map trường từ ChoiceBox tới tên cột trong database
        String column = getColumnForField(selectedField);

        if (column == null) {
            showAlert("Error", "Invalid field selected.");
            return;
        }

        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationAlert.setTitle("Confirm Update");
        confirmationAlert.setHeaderText("Are you sure you want to update the book?");
        confirmationAlert.setContentText("This action will modify the field \"" + selectedField + "\" of book ID " + bookId);

        // Xử lý người dùng chọn OK hoặc Cancel
        if (confirmationAlert.showAndWait().isPresent() && confirmationAlert.getResult().getButtonData().isDefaultButton()) {
            try {
                // Cập nhật dữ liệu
                String sql = "UPDATE books SET " + column + " = ? WHERE id = ?";
                PreparedStatement statement = DatabaseConnection.getConnection().prepareStatement(sql);
                statement.setString(1, newValue);
                statement.setInt(2, bookIdInt);  // Sử dụng ID sách để cập nhật

                int rowsAffected = statement.executeUpdate();
                if (rowsAffected > 0) {
                    showAlert("Success", "Book updated successfully.");
                    inputField.clear();
                    bookTitleLabel.setText(""); // Clear title label after update
                } else {
                    showAlert("Error", "No book found with the given ID.");
                    inputField.clear();
                }

                statement.close();
            } catch (Exception e) {
                e.printStackTrace();
                showAlert("Error", "Error updating book.");
            }
        }
    }

    /**
     * Maps the field selected in the ChoiceBox to the corresponding database column name.
     *
     * @param field The field selected in the ChoiceBox
     * @return The corresponding database column name, or null if no match is found
     */
    private String getColumnForField(String field) {
        switch (field) {
            case "Title":
                return "title";
            case "Author":
                return "author";
            case "Public Year":
                return "publication_year";
            case "Publisher":
                return "publisher";
            case "Language":
                return "language";
            case "Book Cover":
                return "preview_link";
            default:
                return null;
        }
    }
}
