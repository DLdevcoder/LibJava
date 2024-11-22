package controllers.Document;

import controllers.HeaderController;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import models.Book;
import models.Member;
import models.Review;
import utils.DatabaseConnection;

import java.io.IOException;
import java.sql.*;
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
            if(!checkBookExists(Integer.parseInt(bookId)) || !checkMemberExists(Integer.parseInt(memberId))){
                showAlert("Error","Book or member does not exist");
                return;
            }
            Book book = new Book(Integer.parseInt(bookId));
            Member member = new Member(Integer.parseInt(memberId));
            Review review = new Review(book, comment, Date.valueOf(now.toLocalDate()), Double.parseDouble(rating), member);

            member.saveReviewToDatabase(review);

            showAlert("Success", "Successfully Added Review");


        }
        else{
            showAlert("Empty Fields", "Empty Fields");
        }




    }

    private static boolean checkExists(String query, int id) throws SQLException {
        DatabaseConnection databaseConnection = new DatabaseConnection();
        try (Connection connection = databaseConnection.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt(1) > 0;
                }
            }

        }
        return false;


    }

    public static boolean checkBookExists(int bookId) throws SQLException {
        String query = "SELECT COUNT(*) FROM books WHERE id = ?";
        return checkExists(query, bookId);

    }

    public static boolean checkMemberExists(int memberId) throws SQLException {
        String query = "SELECT COUNT(*) FROM members WHERE member_id = ?";
        return checkExists(query, memberId);

    }









    }




