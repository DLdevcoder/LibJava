package controllers.Document;

import controllers.HeaderController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import models.Review;
import utils.DatabaseConnection;

import java.sql.*;
import java.time.LocalDate;
import java.util.Map;

public class BookReviewController extends HeaderController {
    @FXML
    private TextField BookIdField;

    @FXML
    private TextField ReviewField;

    @FXML
    private TextField ReviewerIdField;

    @FXML
    private TableView<Review> ReviewTable;


    @FXML
    private TableColumn<Review, String> BookTitle;

    @FXML
    private TableColumn<Review, String> BookReviews;

    @FXML
    private TableColumn<Review, String> ReviewDate;

    @FXML
    private TableColumn<Review, String> BookReviewer;

    private Connection connection;

    private ObservableList<Review> reviews = FXCollections.observableArrayList();

    @FXML
    private void initialize() {
        connection = DatabaseConnection.getConnection();
        BookReviews.setCellValueFactory(new PropertyValueFactory<>("reviewText"));
        ReviewDate.setCellValueFactory(new PropertyValueFactory<>("reviewDate"));
        BookReviewer.setCellValueFactory(new PropertyValueFactory<>("reviewerName"));

        ReviewTable.setItems(reviews);




    }

    @FXML
    private void addReview() {
        String bookId = BookIdField.getText();
        String review = ReviewField.getText();
        String reviewerID = ReviewerIdField.getText();
        LocalDate reviewDate = LocalDate.now();

        if(bookId.isEmpty() || review.isEmpty() || reviewerID.isEmpty()) {
            return;
        }

        try{
            String query = "insert into reviews (book_id, comment, member_id, review_date) values (?,?,?,?) ";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, bookId);
            preparedStatement.setString(2, review);
            preparedStatement.setString(3, reviewerID);
            preparedStatement.setDate(4, Date.valueOf(reviewDate));

            BookIdField.clear();
            ReviewField.clear();
            ReviewerIdField.clear();

            showAlert("Successfully", "Successfully Added Review");


            try {
                Stage stage = (Stage) BookIdField.getScene().getWindow();
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/books/BookReview.fxml"));
                loader.setController(this);
                Scene scene = new Scene(loader.load());
                stage.setScene(scene);
            } catch (Exception ex) {
                ex.printStackTrace();

            }

            String q = "select b.id, b.title, r.comment, r.review_date, m.name\n" +
                    "from reviews r inner join books b \n" +
                    "on r.book_id = b.id\n" +
                    "inner join members m \n" +
                    "on r.member_id = m.member_id\n" +
                    "where b.id = ? ";
            PreparedStatement preparedStatement1 = connection.prepareStatement(q);
            preparedStatement1.setString(1, bookId);
            ResultSet resultSet = preparedStatement1.executeQuery();
            reviews.clear();

            while(resultSet.next()) {
                String bookID = resultSet.getString("id");
                String bookTitle = resultSet.getString("title");
                String comment = resultSet.getString("comment");
                LocalDate Date = resultSet.getDate("review_date").toLocalDate();
                String reviewerName = resultSet.getString("name");
                reviews.add(new Review(Integer.parseInt(bookID), bookTitle, comment, Date.toString(), reviewerName));

            }
            ReviewTable.setItems(reviews);




        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }







}

