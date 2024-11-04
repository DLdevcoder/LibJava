package controllers.admin;

import controllers.HeaderController;
import controllers.LoginController;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import models.Admin;

public class ChangePasswordController extends HeaderController {
    @FXML
    private TextField oldPass;
    @FXML
    private TextField newPass;
    @FXML
    private TextField confirmPass;

    @FXML
    public void handleChangePass () {
        String oldPassword = oldPass.getText();
        String newPassword = newPass.getText();
        String confirmPassword = confirmPass.getText();

        if (oldPassword.isEmpty() || newPassword.isEmpty() || confirmPassword.isEmpty()) {
            showAlert("Error", "Please fill all fields!");
            return;
        }

        if (!oldPassword.equals(LoginController.admin.getPassword())) {
            showAlert("Error", "Old password is incorrect!");
            return;
        }

        if (!newPassword.equals(confirmPassword)) {
            showAlert("Error", "New password and confirm password do not match!");
            return;
        }

        LoginController.admin.setPassword(newPassword);
        Admin ad = new Admin();
        ad.changePassword(LoginController.admin.getAdminId(), newPassword);
        showAlert("Success", "Password changed successfully!");
        reset();
    }

    @FXML
    public void handleCancel() {
        oldPass.clear();
        newPass.clear();
        confirmPass.clear();
    }

    public void reset() {
        handleCancel();
    }
}
