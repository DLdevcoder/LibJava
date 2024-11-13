package controllers.Document;

import controllers.HeaderController;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import models.Book;
import utils.DatabaseConnection;
import utils.GoogleBooksAPI;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AddBookController extends HeaderController {

    @FXML
    private TextField AddBook_TextField;

    @FXML
    private TextField ImageLink_TextField;

    private GoogleBooksAPI googleBooksAPI = new GoogleBooksAPI();

     @FXML
    public void HandleAddBookButton() throws SQLException {
         String isbn = AddBook_TextField.getText().trim();
         String imageLink = ImageLink_TextField.getText().trim();
          if(!isbn.isEmpty()){
              Book book = googleBooksAPI.getBookByISBN(isbn, imageLink);
              if(book != null){
               showAlert("Succcessfully","Book added successfully");
               saveBookToDatabase(book);


              } else{
                  showAlert("Error", "Book not found");
                             }
          } else{
                  showAlert("Error","Please Enter Book Title");          }

    }

    private void saveBookToDatabase(Book book) {
        String sql = "INSERT INTO books (title, author, publication_year, publisher, language, preview_link) VALUES (?, ?, ?, ?, ?, ?)";
        DatabaseConnection databaseConnection = new DatabaseConnection();

        try( Connection connection = DatabaseConnection.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql) ){
           preparedStatement.setString(1, book.getTitle());
           preparedStatement.setString(2, book.getAuthor());
           preparedStatement.setString(3, book.getPublicationYear());
           preparedStatement.setString(4, book.getPublisher());
           preparedStatement.setString(5, book.getLanguage());
           preparedStatement.setString(6, book.getImageLink().getImage().getUrl());

           preparedStatement.executeUpdate();

        }catch (SQLException e){
            e.printStackTrace();
        }

        }
    }






