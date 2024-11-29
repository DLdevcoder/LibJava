package controllers.Home;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
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
            DatabaseConnection databaseConnection = new DatabaseConnection();
            try (Connection connection = DatabaseConnection.getConnection();
                 Statement statement = connection.createStatement()) {

                String sql = "SELECT id, title, author, thumbnail, average_rating FROM books ORDER BY average_rating DESC";
                ResultSet resultSet = statement.executeQuery(sql);

                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String title = resultSet.getString("title");
                    String author = resultSet.getString("author");
                    String cover = resultSet.getString("thumbnail");
                    if (cover == null || cover.isEmpty()) {
                        cover = "https://via.placeholder.com/120x160"; // Ảnh mặc định
                    }
                    double rating = resultSet.getDouble("average_rating");

                    VBox bookCard = createBookCard(id, title, author, cover, rating);
                    javafx.application.Platform.runLater(() -> bookTilePane.getChildren().add(bookCard));
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }).start();
    }

    private VBox createBookCard(int id, String title, String author, String cover, double rating) {
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
        authorLabel.setStyle("-fx-font-size: 12px; -fx-text-fill: #555;");

        Label ratingLabel = new Label("Đánh giá: " + rating + "/5");
        ratingLabel.setStyle("-fx-font-size: 12px; -fx-text-fill: #888;");

        card.getChildren().addAll(bookImage, titleLabel, authorLabel, ratingLabel);

        // Thêm sự kiện click vào card
        card.setOnMouseClicked(event -> openBookDetailsTab(id, title, author, cover, rating));

        return card;
    }
    @FXML
    private TabPane tabPane;
    private void openBookDetailsTab(int id, String title, String author, String cover, double rating) {
        // Kiểm tra nếu tab với tên sách đã có sẵn, nếu có thì chuyển sang tab đó
        TabPane tabPane = new TabPane();  // TabPane có thể đã có sẵn trong layout của bạn

        for (Tab tab : tabPane.getTabs()) {
            if (tab.getText().equals(title)) {
                tabPane.getSelectionModel().select(tab);
                return;
            }
        }

        // Nếu không, tạo một tab mới với thông tin sách
        Tab bookTab = new Tab();
        bookTab.setText(title);

        VBox bookDetailView = new VBox();
        bookDetailView.setSpacing(10);
        bookDetailView.setStyle("-fx-padding: 20; -fx-background-color: #fff;");

        // Tạo các Label cho các thông tin chi tiết
        Label titleLabel = new Label("Tiêu đề: " + title);
        titleLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        Label authorLabel = new Label("Tác giả: " + author);
        authorLabel.setStyle("-fx-font-size: 14px;");

        Label ratingLabel = new Label("Đánh giá: " + rating + "/5");
        ratingLabel.setStyle("-fx-font-size: 14px;");

        // Thêm ảnh bìa vào ImageView
        ImageView bookImage = new ImageView();
        try {
            bookImage.setImage(new Image(cover, 300, 400, false, true));
        } catch (Exception e) {
            bookImage.setImage(new Image("https://via.placeholder.com/300x400")); // Ảnh mặc định
        }
        bookImage.setFitWidth(300);
        bookImage.setFitHeight(400);

        // Thêm các Label và ảnh bìa vào VBox
        bookDetailView.getChildren().addAll(bookImage, titleLabel, authorLabel, ratingLabel);

        // Đặt VBox vào tab
        bookTab.setContent(bookDetailView);

        // Thêm tab vào TabPane và chọn tab này
        tabPane.getTabs().add(bookTab);
        tabPane.getSelectionModel().select(bookTab);
    }


}