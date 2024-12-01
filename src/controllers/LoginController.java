package controllers;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import models.Admin;

public class LoginController extends HeaderController {
    public static Admin admin;
    @FXML
    private TextField emailField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private ImageView backgroundImage;

    public void initialize() {
        // Đợi cho đến khi scene của backgroundImage được gán trước khi thiết lập liên kết kích thước
        backgroundImage.sceneProperty().addListener((obs, oldScene, newScene) -> {
            if (newScene != null) {
                // Liên kết kích thước của ảnh với kích thước cửa sổ
                backgroundImage.fitWidthProperty().bind(newScene.widthProperty());
                backgroundImage.fitHeightProperty().bind(newScene.heightProperty());

            }
        });
    }



    @FXML
    public void handleLogin(ActionEvent event) {
        String email = emailField.getText();
        String password = passwordField.getText();

        // Kiểm tra email và password
        // Nếu đúng thì chuyển

        if (email.isEmpty() || password.isEmpty()) {
            showAlert("Error", "Please fill in all fields!");
            return;
        }
        Admin admin = Admin.getInstance();
        if (admin.checkLogin(email, password)) {
            admin = Admin.getAdminByLogin(email, password);
            sceneHome(event);
        } else {
            showAlert("Error", "Email or password is incorrect!");
            emailField.setText("");
            passwordField.setText("");
        }
    }

    public void sceneHome(ActionEvent event) {
        try {
            HeaderController headerController = new HeaderController();
            headerController.changeScene(event, "/views/members/MemberList.fxml");
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Error", "Failed to load the home scene.");
        }
    }

}