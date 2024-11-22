package controllers.Document;

import controllers.HeaderController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import models.Admin;
import utils.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DeleteBookController extends HeaderController {

    @FXML
    private TextField RemoveBook_TextField;
    public void HandleRemoveBookButton(ActionEvent event) {
        String id = RemoveBook_TextField.getText();
        if (id.isEmpty()) {
            showAlert("Error","ID can not be empty");
            return;
        }
        Admin admin = new Admin();
        admin.deleteBook(id);





    }

}
