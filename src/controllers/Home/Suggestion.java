package controllers.Home;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.Scene;
import models.Book;
import utils.DatabaseConnection;

import java.sql.*;
import java.text.SimpleDateFormat;

public class Suggestion {

    @FXML
    private TilePane bookTilePane;

    @FXML
    private void initialize() {
        loadBooks();
    }

    private void loadBooks() {
        new Thread(() -> {
            try (Connection connection = DatabaseConnection.getConnection();
                 Statement statement = connection.createStatement()) {

                // Truy vấn lấy thông tin sách từ cơ sở dữ liệu
                String sql = "SELECT id, title, author, publication_year, page_count, description, average_rating, preview_link FROM books ORDER BY average_rating DESC limit 10";
                ResultSet resultSet = statement.executeQuery(sql);

                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String title = resultSet.getString("title");
                    String cover = resultSet.getString("preview_link");
                    if (cover == null || cover.isEmpty()) {
                        cover = "https://via.placeholder.com/120x160"; // Ảnh mặc định
                    }
                    String author = resultSet.getString("author");
                    int publicationYear = resultSet.getInt("publication_year");
                    int pageCount = resultSet.getInt("page_count");
                    String description = resultSet.getString("description");
                    double rating = resultSet.getDouble("average_rating");

                    VBox bookCard = createBookCard(id, title, author, cover, publicationYear, pageCount, description, rating);
                    javafx.application.Platform.runLater(() -> bookTilePane.getChildren().add(bookCard));
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }).start();
    }

    private VBox createBookCard(int id, String title, String author, String cover, int publicationYear, int pageCount, String description, double rating) {
        VBox card = new VBox();
        card.setSpacing(10);
        card.setPrefWidth(150);

        // Thêm ảnh bìa vào ImageView
        ImageView bookImage = new ImageView();
        try {
            bookImage.setImage(new Image(cover, 120, 160, false, true));
        } catch (Exception e) {
            bookImage.setImage(new Image("https://via.placeholder.com/120x160")); // Ảnh mặc định
        }
        bookImage.setFitWidth(120);
        bookImage.setFitHeight(160);

        // Tạo các Label cho tên sách, tác giả và đánh giá
        Label titleLabel = new Label(title);
        titleLabel.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");

        Label authorLabel = new Label("Tác giả: " + author);
        authorLabel.setStyle("-fx-font-size: 12px;");

        Label ratingLabel = new Label("Đánh giá: " + rating + "/5");
        ratingLabel.setStyle("-fx-font-size: 12px; -fx-text-fill: #888;");

        card.getChildren().addAll(bookImage, titleLabel, authorLabel, ratingLabel);

        // Thêm sự kiện click vào card
        card.setOnMouseClicked(event -> openBookDetailsWindow(id, title, cover, author, publicationYear, pageCount, description, rating));

        return card;
    }

    private void openBookDetailsWindow(int bookId, String title, String cover, String author, int publicationYear, int pageCount, String description, double rating) {
        // Tạo cửa sổ chi tiết sách
        Stage detailStage = new Stage();
        detailStage.setTitle(title);

        // VBox chứa thông tin chi tiết sách
        VBox bookDetailView = new VBox();
        bookDetailView.setSpacing(10);
        bookDetailView.setStyle("-fx-padding: 20; -fx-background-color: #fff;");

        // Thêm ảnh bìa vào ImageView
        ImageView bookImage = new ImageView();
        try {
            bookImage.setImage(new Image(cover, 200, 300, false, true));
        } catch (Exception e) {
            bookImage.setImage(new Image("https://via.placeholder.com/300x400"));
        }
        bookImage.setFitWidth(200);
        bookImage.setFitHeight(300);

        // Tạo các Label cho các thông tin chi tiết về sách
        Label titleLabel = new Label("Tiêu đề: " + title);
        titleLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
        Label authorLabel = new Label("Tác giả: " + author);
        authorLabel.setStyle("-fx-font-size: 14px;");
        Label publicationYearLabel = new Label("Năm xuất bản: " + publicationYear);
        publicationYearLabel.setStyle("-fx-font-size: 14px;");
        Label pageCountLabel = new Label("Số trang: " + pageCount);
        pageCountLabel.setStyle("-fx-font-size: 14px;");
        Label descriptionLabel = new Label("Mô tả: " + (description != null ? description : "Không có mô tả"));
        descriptionLabel.setStyle("-fx-font-size: 14px;");
        Label ratingLabel = new Label("Đánh giá: " + rating + "/5");
        ratingLabel.setStyle("-fx-font-size: 14px;");

        // Thêm các Label vào VBox
        bookDetailView.getChildren().addAll(bookImage, titleLabel, authorLabel, publicationYearLabel, pageCountLabel, descriptionLabel, ratingLabel);

        // Khu vực hiển thị bình luận
        VBox commentBox = new VBox();
        commentBox.setSpacing(5);
        commentBox.setStyle("-fx-padding: 10;");
        commentBox.setPrefHeight(300); // Chiều cao cố định cho khu vực bình luận

        // Bọc VBox vào một ScrollPane để cho phép cuộn
        ScrollPane commentScrollPane = new ScrollPane(commentBox);
        commentScrollPane.setFitToWidth(true); // Đảm bảo ScrollPane lấp đầy chiều rộng
        commentScrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS); // Luôn hiển thị thanh cuộn dọc

        // Hiển thị các bình luận trước đó
        loadComments(bookId, commentBox);

        // Khu vực nhập bình luận
        TextArea commentInput = new TextArea();
        commentInput.setPromptText("Viết bình luận của bạn...");
        commentInput.setPrefHeight(100);

        // Nút gửi bình luận
        Button submitButton = new Button("Gửi bình luận");
        submitButton.setOnAction(e -> {
            String comment = commentInput.getText();
            if (!comment.trim().isEmpty()) {
                // Lưu bình luận vào cơ sở dữ liệu
                saveComment(bookId, comment);
                commentInput.clear();  // Xóa ô nhập sau khi gửi

                // Thêm bình luận mới vào bảng mà không cần tải lại toàn bộ
                Label newCommentLabel = new Label("Ngày: " + getCurrentDate() + "\n" + comment);
                newCommentLabel.setStyle("-fx-font-size: 12px; -fx-text-fill: #555;");
                commentBox.getChildren().add(newCommentLabel);

                // Hiển thị thông báo đã lưu bình luận
                showConfirmationDialog("Bình luận đã được lưu!");
            }
        });

        // Thêm các phần tử vào VBox chính
        bookDetailView.getChildren().addAll(commentScrollPane, commentInput, submitButton);

        // Tạo một ScrollPane cho toàn bộ VBox (thêm thanh cuộn cho cửa sổ chi tiết)
        ScrollPane mainScrollPane = new ScrollPane(bookDetailView);
        mainScrollPane.setFitToWidth(true);
        mainScrollPane.setFitToHeight(true); // Cho phép cuộn cả chiều cao
        mainScrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS); // Luôn hiển thị thanh cuộn dọc

        // Tạo một Scene cho cửa sổ chi tiết
        Scene scene = new Scene(mainScrollPane, 500, 750); // Tạo scene với scrollPane
        detailStage.setScene(scene);
        detailStage.show();
    }

    private String getCurrentDate() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return format.format(new java.util.Date());
    }

    private void saveComment(int bookId, String comment) {
        int memberId = 1; // Giả sử đây là id của người dùng hiện tại, bạn có thể thay bằng ID thực tế của người dùng

        String sql = "INSERT INTO reviews (book_id, member_id, comment, review_date) VALUES (?, ?, ?, NOW())";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, bookId);
            statement.setInt(2, memberId);
            statement.setString(3, comment);
            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void loadComments(int bookId, VBox commentBox) {
        try (Connection connection = DatabaseConnection.getConnection();
             Statement statement = connection.createStatement()) {

            String sql = "SELECT comment, review_date FROM reviews WHERE book_id = " + bookId + " ORDER BY review_date DESC";
            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                String comment = resultSet.getString("comment");
                String reviewDate = resultSet.getString("review_date");

                // Hiển thị thông tin bình luận
                Label commentLabel = new Label("Ngày: " + reviewDate + "\n" + comment);
                commentLabel.setStyle("-fx-font-size: 12px; -fx-text-fill: #555;");
                commentBox.getChildren().add(commentLabel);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void showConfirmationDialog(String message) {
        // Tạo một Alert kiểu INFORMATION
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Thông báo");
        alert.setHeaderText(null); // Không cần header
        alert.setContentText(message); // Nội dung thông báo

        // Hiển thị Alert
        alert.showAndWait();
    }
}
