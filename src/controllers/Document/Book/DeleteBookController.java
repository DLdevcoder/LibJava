package controllers.Document.Book;

import controllers.Document.DocumentSideBarController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import models.Admin;
import utils.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class DeleteBookController extends DocumentSideBarController {

    @FXML
    private TextField RemoveBook_TextField;

    @FXML
    private Label BookNameLabel;

    /**
     * Initialize the controller and set up listeners.
     */
    @FXML
    public void initialize() {
        // Lắng nghe sự kiện khi người dùng nhập ID và rời khỏi TextField
        RemoveBook_TextField.setOnKeyReleased(event -> updateBookName());
    }

    /**
     * Updates the label with the book's title based on the entered ID.
     */
    private void updateBookName() {
        String id = RemoveBook_TextField.getText().trim();
        if (!id.isEmpty()) {
            String bookTitle = getBookTitleById(id);
            if (bookTitle != null) {
                BookNameLabel.setText("Book Title: " + bookTitle);
                BookNameLabel.setStyle("-fx-text-fill: green;"); // Màu xanh khi tìm thấy
            } else {
                BookNameLabel.setText("Book not found!");
                BookNameLabel.setStyle("-fx-text-fill: red;"); // Màu đỏ khi không tìm thấy
            }
        } else {
            BookNameLabel.setText("");
            BookNameLabel.setStyle(""); // Reset style nếu trống
        }
    }


    /**
     * Handles the removal of a book when the "Remove Book" button is clicked.
     *
     * @param event the ActionEvent triggered by the button click
     */
    public void HandleRemoveBookButton(ActionEvent event) {
        String id = RemoveBook_TextField.getText();
        if (id.isEmpty()) {
            showAlert("Error", "ID cannot be empty");
            return;
        }

        // Tạo hộp thoại xác nhận
        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationAlert.setTitle("Confirm Deletion");
        confirmationAlert.setHeaderText("Are you sure you want to delete this book?");
        confirmationAlert.setContentText("Book ID: " + id);

        // Hiển thị hộp thoại và chờ người dùng phản hồi
        Optional<ButtonType> result = confirmationAlert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            // Nếu người dùng nhấn OK, thực hiện xóa
            Admin admin = Admin.getInstance();
            admin.deleteBook(id);

            // Xóa text field và làm trống label
            RemoveBook_TextField.clear();
            BookNameLabel.setText("");
            showAlert("Successfully", "Book deleted successfully.");
        } else {
            showAlert("Cancelled", "Deletion cancelled.");
        }
    }

    /**
     * Retrieves the title of a book from the database by its ID.
     *
     * @param id The ID of the book to retrieve.
     * @return The title of the book if found, or null otherwise.
     */
    private String getBookTitleById(String id) {
        String query = "SELECT title FROM books WHERE id = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                // Trả về tiêu đề sách
                return resultSet.getString("title");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // Trả về null nếu không tìm thấy
    }

    /**
     * Utility method to show alerts.
     *
     * @param title   The title of the alert.
     * @param message The content of the alert.
     */

}
