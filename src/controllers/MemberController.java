package controllers;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import models.Member;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.cell.PropertyValueFactory;
import models.Admin;

public class MemberController extends HeaderController {
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

    private ObservableList<Member> memberList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("memberId"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        passwordColumn.setCellValueFactory(new PropertyValueFactory<>("password"));
        phoneColumn.setCellValueFactory(new PropertyValueFactory<>("phone"));
        addressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));
        membershipDateColumn.setCellValueFactory(new PropertyValueFactory<>("membershipDate"));

        // Lấy danh sách thành viên từ database
        Admin admin = new Admin();
        memberTableView.setItems(FXCollections.observableArrayList(admin.getMembers()));
    }

}