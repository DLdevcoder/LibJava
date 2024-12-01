package controllers.Home;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.Scene;
import models.Book;
import utils.DatabaseConnection;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

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
        card.setStyle("-fx-padding: 10; -fx-background-color: #ffffff; -fx-border-color: #ddd; -fx-border-radius: 5; -fx-background-radius: 5;");
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

    private void openBookDetailsWindow(int id, String title, String cover, String author, int publicationYear, int pageCount, String description, double rating) {
        // Tạo một cửa sổ mới (Stage)
        Stage detailStage = new Stage();
        detailStage.setTitle(title);  // Tên cửa sổ là tên sách

        // Tạo một VBox để chứa thông tin chi tiết sách
        VBox bookDetailView = new VBox();
        bookDetailView.setSpacing(10);
        bookDetailView.setStyle("-fx-padding: 20; -fx-background-color: #fff;");

        // Thêm ảnh bìa vào ImageView
        ImageView bookImage = new ImageView();
        try {
            bookImage.setImage(new Image(cover, 300, 400, false, true));
        } catch (Exception e) {
            bookImage.setImage(new Image("https://via.placeholder.com/300x400")); // Ảnh mặc định
        }
        bookImage.setFitWidth(300);
        bookImage.setFitHeight(400);

        // Tạo các Label cho các thông tin chi tiết
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

        // Thêm các thông tin vào VBox
        bookDetailView.getChildren().addAll(bookImage, titleLabel, authorLabel, publicationYearLabel, pageCountLabel, descriptionLabel, ratingLabel);

        // Tạo một Scene cho cửa sổ mới
        Scene scene = new Scene(bookDetailView, 500, 650);
        detailStage.setScene(scene);  // Đặt Scene cho cửa sổ mới

        // Hiển thị cửa sổ mới
        detailStage.show();
    }
}
