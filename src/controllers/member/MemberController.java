package controllers.member;

import javafx.application.Platform;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.concurrent.Task;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Callback;
import models.Member;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.cell.PropertyValueFactory;
import models.Admin;
import models.Review;

import java.util.List;

public class MemberController extends SidebarMemberController {
    @FXML
    private TextField nameSearchField;
    @FXML
    public TableView<Member> memberTableView = new TableView<>(); // TableView hiển thị danh sách thành viên
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
    @FXML
    private ProgressIndicator loadingIndicator = new ProgressIndicator();

    public static ObservableList<Member> memberList = FXCollections.observableArrayList();

    /**
     * Khởi tạo controller
     */
    @FXML
    public void initialize() {
        memberTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        memberTableView.setEditable(true);

        Admin admin = Admin.getInstance();

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

        loadMembers(admin.getMembers(), memberTableView);
        // Chỉnh sửa trực tiếp trong TableView

        setUpEditCommitHandlers(admin);

    }

    @FXML
    public void loadMembers(List<Member> members, TableView<Member> memberTableView) {
        Task<ObservableList<Member>> loadMembersTask = new Task<>() {
            @Override
            protected ObservableList<Member> call() throws Exception {
                return FXCollections.observableArrayList(members);
            }

            @Override
            protected void succeeded() {
                super.succeeded();
                Platform.runLater(() -> {
                    // Ẩn thanh quay và cập nhật dữ liệu
                    loadingIndicator.setVisible(false);
                    memberList.setAll(getValue());
                    memberTableView.setItems(memberList);
                });
            }

            @Override
            protected void failed() {
                super.failed();
                Platform.runLater(() -> {
                    loadingIndicator.setVisible(false);
                    // Có thể thêm thông báo lỗi khác, nếu cần
                });
            }
        };

        // Hiển thị thanh quay khi bắt đầu tải
        loadingIndicator.setVisible(true);

        // Chạy task trên một luồng nền
        new Thread(loadMembersTask).start();
    }

    public TableView<Member> getMemberTableView() {
        return memberTableView;
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

    /**
     * Cập nhật thông tin thành viên
     * @param member Thành viên cần cập nhật
     * @param property Thuộc tính cần cập nhật
     * @param newValue Giá trị mới
     * @param admin Đối tượng Admin
     */
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

    /**
     * Tạo một TableCell chứa nút xóa và nút xem danh sách bình luận
     * @param admin Đối tượng Admin
     * @return TableCell chứa nút xóa và nút xem danh sách bình luận
     */
    private TableCell<Member, String> createActionCell(Admin admin) {
        return new TableCell<Member, String>() {
            private final Button deleteButton = new Button("Delete");
            private final Button reviewList = new Button("Review List");
            private final HBox hbox = new HBox(10, deleteButton, reviewList);

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
                reviewList.setOnAction(event -> {
                    Member selectedMember = getTableView().getItems().get(getIndex());
                    try {
                        showReviewList(selectedMember);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
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

    /**
     * Hiển thị danh sách bình luận của một thành viên
     * @param member Thành viên cần xem bình luận
     */
    private void showReviewList(Member member) {
        // Lấy danh sách bình luận của thành viên
        List<Review> reviews = member.getReviews();

        // Tạo một TableView để hiển thị các bình luận
        TableView<Review> reviewTable = new TableView<>();
        TableColumn<Review, String> bookColumn = new TableColumn<>("Book");
        TableColumn<Review, String> commentColumn = new TableColumn<>("Comment");
        TableColumn<Review, Integer> ratingColumn = new TableColumn<>("Rating");
        TableColumn<Review, String> dateColumn = new TableColumn<>("Date");

        bookColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getBook().getTitle()));
        commentColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getReviewText()));
        ratingColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty((int) cellData.getValue().getRating()).asObject());
        dateColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getReviewDate().toString()));

        reviewTable.getColumns().addAll(bookColumn, commentColumn, ratingColumn, dateColumn);
        reviewTable.getItems().setAll(reviews);

        // Tạo cửa sổ mới hiển thị danh sách bình luận
        Stage reviewStage = new Stage();
        VBox vbox = new VBox(reviewTable);
        Scene scene = new Scene(vbox, 450, 300);
        scene.getStylesheets().add(getClass().getResource("/stylesheet/MemberList.css").toExternalForm());
        reviewStage.setScene(scene);
        reviewStage.setTitle("Reviews of " + member.getName());
        reviewStage.show();
    }
}


