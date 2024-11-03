package controllers;
import com.mysql.cj.log.Log;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import models.Admin;
import models.Member;
import java.sql.*;
import java.util.List;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class AdminController extends HeaderController{
    @FXML
    private TextField nameField;
    @FXML
    private TextField emailField;
    @FXML
    private TextField phoneField;
    @FXML
    private TextField addressField;
    @FXML
    private TextField createDateField;

    @FXML
    public void initialize() {
        nameField.setEditable(false);
        emailField.setEditable(false);
        addressField.setEditable(false);
        phoneField.setEditable(false);
        createDateField.setEditable(false);

        Admin admin = LoginController.admin;
        if ( admin == null) {
            showAlert("Error", "Please login first!");
            return;
        }
        nameField.setText(admin.getName());
        emailField.setText(admin.getEmail());
        phoneField.setText(admin.getPhone());
        addressField.setText(admin.getAddress());
        createDateField.setText(admin.getCreateDate());
    }




}