package controllers.Document.Book;

import controllers.Document.DocumentSideBarController;
import javafx.fxml.FXML;
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

    /**
     * Handles the event of adding a review for a book.
     * This method validates input fields, checks if the book and member exist in the database,
     * and saves the review if all conditions are met.
     * @throws SQLException If there is an issue accessing the database.
     */
    @FXML
    public void HandleAddReviewButton() throws SQLException {
        // Lấy giá trị từ các trường nhập liệu
        String bookId = AddBookId_TextField.getText();
        LocalDateTime now = LocalDateTime.now();        // Thời gian hiện tại
        String comment = AddComment_TextField.getText();
        String rating = AddRating_TextField.getText();
        String memberId = AddMemberId_TextField.getText();

        // Kiểm tra nếu tất cả các trường không bị bỏ trống
        if (!bookId.isEmpty() && !comment.isEmpty() && !rating.isEmpty() && !memberId.isEmpty()) {
            // Kiểm tra sách và thành viên có tồn tại trong database hay không
            if (!checkBookExists(Integer.parseInt(bookId)) || !checkMemberExists(Integer.parseInt(memberId))) {
                // Hiển thị thông báo lỗi nếu không tồn tại
                showAlert("Error", "Book or member does not exist");
                return;
            }
            Book book = new Book(Integer.parseInt(bookId));
            Member member = new Member(Integer.parseInt(memberId));

            Review review = new Review(
                    book,
                    comment,
                    Date.valueOf(now.toLocalDate()), // Chuyển đổi `LocalDateTime` sang `Date`
                    Double.parseDouble(rating),      // Chuyển đổi `rating` sang kiểu `double`
                    member
            );

            // Lưu đánh giá vào database thông qua đối tượng `Member`
            member.saveReviewToDatabase(review);

            // Hiển thị thông báo thành công
            showAlert("Success", "Successfully Added Review");
        } else {
            // Hiển thị thông báo nếu có trường bị bỏ trống
            showAlert("Empty Fields", "Empty Fields");
        }
    }

    /**
     * Checks if a record exists in the database for a given query and ID.
     * This is a utility method for performing existence checks in the database.
     * @param query The SQL query to execute, should use a placeholder for the ID.
     * @param id The ID to check for in the database.
     * @return True if the record exists, otherwise false.
     * @throws SQLException If there is an issue accessing the database.
     */
    private static boolean checkExists(String query, int id) throws SQLException {
        try (Connection connection = DatabaseConnection.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt(1) > 0;
                }
            }

        }
        return false;


    }

    /**
     * Checks if a book exists in the database by its ID.
     * This method uses the `checkExists` utility to perform the check.
     * @param bookId The ID of the book to check.
     * @return True if the book exists, otherwise false.
     * @throws SQLException If there is an issue accessing the database.
     */
    public static boolean checkBookExists(int bookId) throws SQLException {
        String query = "SELECT COUNT(*) FROM books WHERE id = ?";
        return checkExists(query, bookId);

    }

    /**
     * Checks if a member exists in the database by their ID.
     * This method uses the `checkExists` utility to perform the check.
     * @param memberId The ID of the member to check.
     * @return True if the member exists, otherwise false.
     * @throws SQLException If there is an issue accessing the database.
     */
    public static boolean checkMemberExists(int memberId) throws SQLException {
        String query = "SELECT COUNT(*) FROM members WHERE member_id = ?";
        return checkExists(query, memberId);

    }



}




