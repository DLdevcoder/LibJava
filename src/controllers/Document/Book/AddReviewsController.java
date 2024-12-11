package controllers.Document.Book;

import controllers.Document.DocumentSideBarController;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import models.Book;
import models.Member;
import models.Review;
import utils.DatabaseConnection;

import java.sql.*;
import java.time.LocalDateTime;

public class AddReviewsController extends DocumentSideBarController {

    @FXML
    private TextField AddBookId_TextField;

    @FXML
    private TextField AddComment_TextField;

    @FXML
    private TextField AddRating_TextField;

    @FXML
    private TextField AddMemberId_TextField;

    @FXML
    private Label BookTitleLabel;  // Label for displaying book title

    @FXML
    private Label MemberNameLabel; // Label for displaying member name

    /**
     * Handles the event of adding a review for a book.
     * This method validates input fields, checks if the book and member exist in the database,
     * and saves the review if all conditions are met.
     * @throws SQLException If there is an issue accessing the database.
     */
    @FXML
    public void HandleAddReviewButton() throws SQLException {
        String bookId = AddBookId_TextField.getText();
        LocalDateTime now = LocalDateTime.now();
        String comment = AddComment_TextField.getText();
        String rating = AddRating_TextField.getText();
        String memberId = AddMemberId_TextField.getText();

        // Kiểm tra nếu tất cả các trường không bị bỏ trống
        if (!bookId.isEmpty() && !comment.isEmpty() && !rating.isEmpty() && !memberId.isEmpty()) {
            // Kiểm tra sách và thành viên có tồn tại trong database hay không
            if (!checkBookExists(Integer.parseInt(bookId)) || !checkMemberExists(Integer.parseInt(memberId))) {
                showAlert("Error", "Book or member does not exist");
                return;
            }
            Book book = new Book(Integer.parseInt(bookId));
            Member member = new Member(Integer.parseInt(memberId));

            // Hiển thị thông tin sách và thành viên
            BookTitleLabel.setText(getBookTitle(book.getId()));
            MemberNameLabel.setText(getMemberName(member.getMemberId()));

            Review review = new Review(
                    book,
                    comment,
                    Date.valueOf(now.toLocalDate()),
                    Double.parseDouble(rating),
                    member
            );

            // Lưu đánh giá vào database thông qua đối tượng `Member`
            member.saveReviewToDatabase(review);

            showAlert("Success", "Successfully Added Review");
        } else {
            showAlert("Empty Fields", "Empty Fields");
        }
    }

    /**
     * Retrieves the book title from the database based on book ID.
     * @param bookId The ID of the book.
     * @return The title of the book.
     * @throws SQLException If there is an issue accessing the database.
     */
    private String getBookTitle(int bookId) throws SQLException {
        String query = "SELECT title FROM books WHERE id = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, bookId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getString("title");
                } else {

                    return "Not Found";
                }
            }
        }
    }

    /**
     * Retrieves the member name from the database based on member ID.
     * @param memberId The ID of the member.
     * @return The name of the member.
     * @throws SQLException If there is an issue accessing the database.
     */
    private String getMemberName(int memberId) throws SQLException {
        String query = "SELECT name FROM members WHERE member_id = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, memberId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getString("name");
                } else {
                    return "Not Found";
                }
            }
        }
    }

    /**
     * Checks if a record exists in the database for a given query and ID.
     * @param query The SQL query to execute, should use a placeholder for the ID.
     * @param id The ID to check for in the database.
     * @return True if the record exists, otherwise false.
     * @throws SQLException If there is an issue accessing the database.
     */
    private static boolean checkExists(String query, int id) throws SQLException {
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                return resultSet.next();
            }
        }
    }

    public static boolean checkBookExists(int bookId) throws SQLException {
        String query = "SELECT COUNT(*) FROM books WHERE id = ?";
        return checkExists(query, bookId);
    }

    public static boolean checkMemberExists(int memberId) throws SQLException {
        String query = "SELECT COUNT(*) FROM members WHERE member_id = ?";
        return checkExists(query, memberId);
    }

    /**
     * Update label colors based on existence check.
     */
    @FXML
    public void updateLabels() throws SQLException {
        String bookId = AddBookId_TextField.getText();
        String memberId = AddMemberId_TextField.getText();

        if (!bookId.isEmpty()) {
            if (checkBookExists(Integer.parseInt(bookId))) {
                // Cập nhật title và màu sắc cho sách nếu tìm thấy
                BookTitleLabel.setText(getBookTitle(Integer.parseInt(bookId)));
                BookTitleLabel.setStyle("-fx-text-fill: green;");
            } else {
                // Cập nhật title và màu sắc cho sách nếu không tìm thấy
                BookTitleLabel.setText("Not Found");
                BookTitleLabel.setStyle("-fx-text-fill: red;");
            }
        }

        if (!memberId.isEmpty()) {
            if (checkMemberExists(Integer.parseInt(memberId))) {
                // Cập nhật name và màu sắc cho thành viên nếu tìm thấy
                MemberNameLabel.setText(getMemberName(Integer.parseInt(memberId)));
                MemberNameLabel.setStyle("-fx-text-fill: green;");
            } else {
                // Cập nhật name và màu sắc cho thành viên nếu không tìm thấy
                MemberNameLabel.setText("Not Found");
                MemberNameLabel.setStyle("-fx-text-fill: red;");
            }
        }
    }
    /**
     * Attach event listeners to the BookId and MemberId text fields for real-time updates.
     */
    @FXML
    public void initialize() {
        AddBookId_TextField.textProperty().addListener((observable, oldValue, newValue) -> {
            try {
                updateLabels();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });

        AddMemberId_TextField.textProperty().addListener((observable, oldValue, newValue) -> {
            try {
                updateLabels();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }
}
