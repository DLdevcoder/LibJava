package controllers.Document;

import controllers.HeaderController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import utils.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DeleteBookController extends HeaderController {

    @FXML
    private TextField RemoveBook_TextField;

    @FXML
    private Button RemoveBook_Button;

    public void HandleRemoveBookButton(ActionEvent event) {
        String id = RemoveBook_TextField.getText();
        if (id.isEmpty()) {
            showAlert("Error","ID can not be empty");
            return;
        }

        String sql = "DELETE FROM books WHERE id = ?";
        DatabaseConnection databaseConnection = new DatabaseConnection();

        try (Connection connection = databaseConnection.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, id);
            int affectRows = preparedStatement.executeUpdate();
            if (affectRows > 0) {
                showAlert("Successfully","Book deleted successfully");
            } else {
                showAlert("Error","Book could not be deleted");
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }



    }
}
