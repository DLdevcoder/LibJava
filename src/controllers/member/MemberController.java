package controllers.member;

import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.HBox;
import javafx.util.Callback;
import models.Member;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.cell.PropertyValueFactory;
import models.Admin;

public class MemberController extends SidebarMemberController {
    @FXML
    private TextField nameSearchField;
    @FXML
    private TableView<Member> memberTableView; // TableView hiển thị danh sách thành viên
    @FXML
    private TableColumn<Member, Integer> idColumn;
    @FXML
    private TableColumn<Member, String> nameColumn;
    @FXML
    private TableColumn<Member, String> emailColumn;
    @FXML
    private TableColumn<Member, String> passwordColumn;
    @FXML
    private TableColumn<Member, String> phoneColumn;
    @FXML
    private TableColumn<Member, String> addressColumn;
    @FXML
    private TableColumn<Member, String> membershipDateColumn;
    @FXML
    private TableColumn<Member, String> actionColumn; // Cột cho nút

    public static ObservableList<Member> memberList = FXCollections.observableArrayList();
    private boolean isEditing = false;
    private void setEditing(boolean value) {
        isEditing = value;
    }
    private boolean isEditing() {
        return isEditing;
    }
    @FXML
    public void initialize() {
        memberTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        memberTableView.setEditable(true);

        Admin admin = new Admin();

        // Thiết lập các cell factories cho từng cột
        idColumn.setCellValueFactory(new PropertyValueFactory<>("memberId"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        passwordColumn.setCellValueFactory(new PropertyValueFactory<>("password"));
        phoneColumn.setCellValueFactory(new PropertyValueFactory<>("phone"));
        addressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));
        membershipDateColumn.setCellValueFactory(new PropertyValueFactory<>("membershipDate"));

        actionColumn.setCellFactory(new Callback<TableColumn<Member, String>, TableCell<Member, String>>() {
            @Override
            public TableCell<Member, String> call(TableColumn<Member, String> param) {
                return createActionCell(admin);
            }
        });
        // Lấy danh sách thành viên từ database và thêm vào TableView
        memberList.setAll(admin.getMembers());
        memberTableView.setItems(memberList);

        // Chỉnh sửa trực tiếp trong TableView

        setUpEditCommitHandlers(admin);

    }

    // udpate
    private void setUpEditCommitHandlers(Admin admin) {
        nameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        nameColumn.setOnEditCommit(event -> updateMember(event.getRowValue(), "name", event.getNewValue(), admin));

        emailColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        emailColumn.setOnEditCommit(event -> updateMember(event.getRowValue(), "email", event.getNewValue(), admin));

        passwordColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        passwordColumn.setOnEditCommit(event -> updateMember(event.getRowValue(), "password", event.getNewValue(), admin));

        phoneColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        phoneColumn.setOnEditCommit(event -> updateMember(event.getRowValue(), "phone", event.getNewValue(), admin));

        addressColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        addressColumn.setOnEditCommit(event -> updateMember(event.getRowValue(), "address", event.getNewValue(), admin));
    }

    private void updateMember(Member member, String property, String newValue, Admin admin) {
        switch (property) {
            case "name":
                member.setName(newValue);
                break;
            case "email":
                member.setEmail(newValue);
                break;
            case "password":
                member.setPassword(newValue);
                break;
            case "phone":
                member.setPhone(newValue);
                break;
            case "address":
                member.setAddress(newValue);
                break;
        }

        int memberId = member.getMemberId();
        String name = member.getName();
        String email = member.getEmail();
        String password = member.getPassword();
        String phone = member.getPhone();
        String address = member.getAddress();
        admin.updateMember(memberId, name, address, phone, email, password);

    }

    // tao nut sua, xoa
    private TableCell<Member, String> createActionCell(Admin admin) {
        return new TableCell<Member, String>() {
            private final Button deleteButton = new Button("Delete");
            private final Button updateButton = new Button("them sau");
            private final HBox hbox = new HBox(10, deleteButton, updateButton);

            {
                // Xử lý sự kiện khi nhấn nút
                deleteButton.setOnAction(event -> {
                    Member selectedMember = getTableView().getItems().get(getIndex());
                    try {
                        admin.removeMember(selectedMember.getMemberId());
                        memberList.setAll(admin.getMembers());
                        showAlert("Success", "Member deleted successfully!");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
                updateButton.setOnAction(event -> {
                    //pass
                });
            }

            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null); // Không hiển thị gì nếu ô trống
                } else {
                    setGraphic(hbox);
                }
            }
        };
    }

    // Hàm xử lý khi nhấn nút "Tìm kiếm"
    @FXML
    private void handleSearch() {
        String name = nameSearchField.getText();
        Admin admin = new Admin();
        memberList.setAll(admin.findMemberbyName(name));
    }

}


