package controllers.member;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import models.Admin;

public class FilterController {
    @FXML
    private TextField nameField;

    @FXML
    private TextField addressField;

    @FXML
    private TextField phoneField;

    @FXML
    private TextField emailField;

    @FXML
    private DatePicker memberDateSearch;

    @FXML
    private Button clearButton;

    @FXML
    private Button searchButton;

    /**
     * khởi tạo controller
     */
    @FXML
    public void initialize() {
        System.out.println("FindMemberController initialized!");
    }

    /**
     * Xử lý khi nhấn nút xóa
     * @param event sự kiện
     */
    @FXML
    private void handleClearButtonAction(ActionEvent event) {
        // Xóa toàn bộ các trường nhập liệu
        nameField.clear();
        addressField.clear();
        phoneField.clear();
        emailField.clear();
        memberDateSearch.setValue(null);
    }

    /**
     * Xử lý khi nhấn nút tìm kiếm
     * @param event sự kiện
     */
    @FXML
    private void handleSearchButtonAction(ActionEvent event) {
        String name = nameField.getText();
        String address = addressField.getText();
        String phone = phoneField.getText();
        String email = emailField.getText();
        String membershipDate = (memberDateSearch.getValue() != null)
                ? memberDateSearch.getValue().toString()
                : "";

        Admin admin = Admin.getInstance();
        MemberController mb = new MemberController();
        mb.loadMembers(admin.findMemberwithFilter(name, address, phone, email, membershipDate), mb.getMemberTableView());
    }
}
