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
        if (!email.matches("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}")) {
            showAlert("Error", "Invalid email format!");
            refreshForm();
            return;
        }
        if (!phone.matches("[0-9]+")) {
            showAlert("Error", "Phone number must be a number!");
            refreshForm();
            return;
        }
        if (!name.matches("[a-zA-Z ]+")) {
            showAlert("Error", "Name must contain only letters and spaces!");
            refreshForm();
            return;
        }
        if (password.length() < 6) {
            showAlert("Error", "Password must be at least 6 characters long!");
            refreshForm();
            return;
        }

        Member member = new Member(name, email, phone, address, password);

        Admin admin = Admin.getInstance();
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
