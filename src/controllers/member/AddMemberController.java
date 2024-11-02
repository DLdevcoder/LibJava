package controllers.member;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import models.Member;
import models.Admin;

public class AddMemberController extends SidebarMemberController {
    @FXML
    private TextField nameField;
    @FXML
    private TextField emailField;
    @FXML
    private TextField phoneField;
    @FXML
    private TextField addressField;
    @FXML
    private TextField passwordField;

    // Hàm xử lý khi nhấn nút "Thêm"
    @FXML
    private void handleAddMember() {
        String name = nameField.getText();
        String email = emailField.getText();
        String phone = phoneField.getText();
        String address = addressField.getText();
        String password = passwordField.getText();
        // Gọi lớp Admin để thêm thành viên vào hệ thống
        Admin admin = new Admin();
        try {
            admin.addMember(name, address, phone, email, password);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Đóng form sau khi thêm thành viên thành công
        showAlert("Success", "Member added successfully!");
        handleCancel();
    }

    // Hàm xử lý khi nhấn nút "Hủy"
    @FXML
    private void handleCancel() {
        nameField.setText("");
        emailField.setText("");
        phoneField.setText("");
        addressField.setText("");
        passwordField.setText("");
    }
}
