package controllers.Document;

import controllers.HeaderController;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import models.Book;
import models.Member;
import models.Review;
import utils.DatabaseConnection;

import java.sql.*;
import java.text.SimpleDateFormat;

public class BookReviewController extends HeaderController {

    @FXML
    private TableView<Review> ReviewTable;

    @FXML
    private TableColumn<Review, String> BookId;

    @FXML
    private TableColumn<Review, String> BookTitle;

    @FXML
    private TableColumn<Review, String> BookReviews;

    @FXML
    private TableColumn<Review, String> ReviewDate;

    @FXML
    private TableColumn<Review, String> BookReviewer;

    private ObservableList<Review> ReviewData;

    public BookReviewController() {
        ReviewData = FXCollections.observableArrayList();
    }

    @FXML
    private void initialize() {
        // Liên kết các cột với dữ liệu
        BookId.setCellValueFactory(cellData -> cellData.getValue().getBook() != null
                ? new SimpleStringProperty(String.valueOf(cellData.getValue().getBookId()))
                : new SimpleStringProperty(""));
        BookTitle.setCellValueFactory(cellData -> cellData.getValue().getBook() != null
                ? new SimpleStringProperty(cellData.getValue().getBookTitle())
                : new SimpleStringProperty(""));
        BookReviews.setCellValueFactory(new PropertyValueFactory<>("ReviewText"));
        ReviewDate.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getReviewDate().toString())); // Hiển thị Date dưới dạng String
        BookReviewer.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getReviewerName()));

        // Gọi hàm load dữ liệu
        loadReview();
    }

    private void loadReview() {
        new Thread(() -> {
            DatabaseConnection databaseConnection = new DatabaseConnection();
            try (Connection connection = databaseConnection.getConnection();
                 Statement statement = connection.createStatement()) {

                String sql = "select b.id, b.title, r.comment, r.review_date, m.name " +
                        "from reviews r " +
                        "inner join books b on r.book_id = b.id " +
                        "inner join members m on r.member_id = m.member_id";

                ResultSet resultSet = statement.executeQuery(sql);

                ObservableList<Review> data = FXCollections.observableArrayList();

                while (resultSet.next()) {
                    // Lấy thông tin từ database
                    String id = resultSet.getString("id");
                    String title = resultSet.getString("title");
                    String comment = resultSet.getString("comment");
                    String reviewDateStr = resultSet.getString("review_date");
                    String reviewer = resultSet.getString("name");

                    // Chuyển đổi ngày sang java.sql.Date
                    Date reviewDate = convertStringToSqlDate(reviewDateStr);

                    // Tạo Book và Member
                    Book book = new Book(Integer.parseInt(id), title);
                    Member member = new Member(reviewer);

                    // Thêm dữ liệu vào danh sách
                    data.add(new Review(book, comment, reviewDate, member));
                }

                // Cập nhật TableView trên luồng giao diện
                javafx.application.Platform.runLater(() -> ReviewTable.setItems(data));

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }).start();
    }

    // Hàm chuyển đổi String sang java.sql.Date
    private Date convertStringToSqlDate(String dateStr) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            java.util.Date utilDate = format.parse(dateStr);
            return new Date(utilDate.getTime());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
