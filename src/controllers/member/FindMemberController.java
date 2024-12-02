package controllers.member;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import models.Admin;
import models.Member;

public class FindMemberController extends SidebarMemberController {
    @FXML
    private TextField idField;
    @FXML
    private Label nameLabel;
    @FXML
    private Label emailLabel;
    @FXML
    private Label phoneLabel;
    @FXML
    private Label addressLabel;
    @FXML
    private Label membershipDateLabel;
    @FXML
    private Label passwordLabel;

    @FXML
    public void handleFindMember() {
        int id = Integer.parseInt(idField.getText());

        Admin admin = Admin.getInstance();
        Member member = admin.findMember(id);
        if (member != null) {
            nameLabel.setText(member.getName());
            emailLabel.setText(member.getEmail());
            phoneLabel.setText(member.getPhone());
            addressLabel.setText(member.getAddress());
            membershipDateLabel.setText(member.getMembershipDate());
            passwordLabel.setText(member.getPassword());
        } else {
            showAlert("Error", "Member not found!");
        }
    }
}
