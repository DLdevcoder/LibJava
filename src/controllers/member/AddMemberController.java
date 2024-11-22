package controllers.member;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import models.Admin;
import models.Member;

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

        if (name.isEmpty() || email.isEmpty() || phone.isEmpty() || address.isEmpty() || password.isEmpty()) {
            showAlert("Error", "Please fill in all fields!");
            refreshForm();
            return;
        }
        Member member = new Member(name, email, phone, address, password);

        Admin admin = new Admin();
        try {
            admin.addMember(name, address, phone, email, password);
        } catch (Exception e) {
            e.printStackTrace();
        }

        showAlert("Success", "Member added successfully!");
        refreshForm();
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

    @FXML
    private void refreshForm() {
        handleCancel();
    }
}
