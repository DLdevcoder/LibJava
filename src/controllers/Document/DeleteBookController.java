package controllers.Document;

import controllers.HeaderController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import models.Admin;
import utils.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Optional;

public class DeleteBookController extends DocumentSideBarController {

    @FXML
    private TextField RemoveBook_TextField;

    /**
     * Handles the removal of a book when the "Remove Book" button is clicked.
     * Validates the input, and if the ID is provided, deletes the book from the database.
     *
     * @param event the ActionEvent triggered by the button click
     */
    public void HandleRemoveBookButton(ActionEvent event) {
        String id = RemoveBook_TextField.getText();
        if (id.isEmpty()) {
            showAlert("Error", "ID can not be empty");
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
        } else {
            showAlert("Cancelled", "Deletion cancelled.");
        }
    }
}
