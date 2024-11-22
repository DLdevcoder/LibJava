package controllers.Document;

import controllers.HeaderController;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import models.Review;
import utils.DatabaseConnection;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class AddReviewsController extends HeaderController {

    @FXML
    private TextField AddBookId_TextField;

    @FXML
    private TextField AddComment_TextField;

    @FXML
    private TextField AddRating_TextField;

    @FXML
    private TextField AddMemberId_TextField;

    @FXML
    public void HandleAddReviewButton() throws SQLException {
        String bookId = AddBookId_TextField.getText();
        LocalDateTime now = LocalDateTime.now();
        String comment = AddComment_TextField.getText();
        String rating = AddRating_TextField.getText();
        String memberId = AddMemberId_TextField.getText();

        if(!bookId.isEmpty() && !comment.isEmpty() && !rating.isEmpty() && !memberId.isEmpty()){
            Review review = new Review(Integer.parseInt(bookId), comment, Date.valueOf(now.toLocalDate()), Double.parseDouble(rating), Integer.parseInt(memberId));
            saveReviewToDatabase(review);
            showAlert("Success", "Successfully Added Review");


        }
        else{
            showAlert("Empty Fields", "Empty Fields");
        }




    }

    private void saveReviewToDatabase(Review review) {
        String sql = "INSERT INTO reviews(book_id, review_date, comment, rating, member_id) VALUES(?,?,?,?)";
        DatabaseConnection databaseConnection = new DatabaseConnection();

        try(Connection connection = databaseConnection.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, review.getBookid()); // book_id
            preparedStatement.setDate(2, review.getReviewDate()); // review_date (java.sql.Date)
            preparedStatement.setString(3, review.getReviewText()); // comment
            preparedStatement.setDouble(4, review.getRating()); // rating
            preparedStatement.setInt(5, review.getMemberid()); // member_id


            preparedStatement.executeUpdate();

        } catch(SQLException e) {
            e.printStackTrace();
        }




    }



}
