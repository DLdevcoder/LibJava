package controllers.session;

import controllers.HeaderController;
import controllers.member.SidebarMemberController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import models.Admin;

public class LoginController {
    @FXML
    private TextField emailField;
    @FXML
    private PasswordField passwordField;

    @FXML
    public void handleLogin(ActionEvent event) {
        String email = emailField.getText();
        String password = passwordField.getText();

        // Kiểm tra email và password
        // Nếu đúng thì chuyển

        Admin admin = new Admin();
        if (admin.checkLogin(email, password)) {
            sceneHome(event);
        } else {
            showAlert("Error", "Email or password is incorrect!");
        }
    }

    @FXML
    public void sceneHome(ActionEvent event) {
        HeaderController headerController = new HeaderController();
        headerController.changeScene(event, "/views/members/MemberList.fxml");
        // sau doi
    }

    private void showAlert(String title, String content) {
        HeaderController headerController = new HeaderController();
        headerController.showAlert(title, content);
    }
}
