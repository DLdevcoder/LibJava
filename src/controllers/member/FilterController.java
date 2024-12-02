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

    @FXML
    public void initialize() {
        // Có thể thêm các thiết lập mặc định cho controller tại đây
        System.out.println("FindMemberController initialized!");
    }

    @FXML
    private void handleClearButtonAction(ActionEvent event) {
        // Xóa toàn bộ các trường nhập liệu
        nameField.clear();
        addressField.clear();
        phoneField.clear();
        emailField.clear();
        memberDateSearch.setValue(null);
    }

    @FXML
    private void handleSearchButtonAction(ActionEvent event) {
        // Lấy giá trị từ các trường nhập liệu
        String name = nameField.getText();
        String address = addressField.getText();
        String phone = phoneField.getText();
        String email = emailField.getText();
        String membershipDate = (memberDateSearch.getValue() != null)
                ? memberDateSearch.getValue().toString()
                : "";

        Admin admin = Admin.getInstance();
        MemberController mb = new MemberController();
        //MemberController.loadMembers(admin.findMemberwithFilter(name, address, phone, email, membershipDate), new MemberController().getMemberTableView());
        mb.loadMembers(admin.findMemberwithFilter(name, address, phone, email, membershipDate), mb.getMemberTableView());
    }
}
