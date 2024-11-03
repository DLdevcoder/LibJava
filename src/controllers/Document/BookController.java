package controllers.Document;
import controllers.HeaderController;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import models.Book;
import utils.DatabaseConnection;

import java.sql.*;

import java.util.List;
import java.sql.Connection;
import java.sql.SQLException;

public class BookController extends HeaderController {

     protected static List<Book> books;
     @FXML
     private TableView<Book> Document_Table;

     @FXML
     private TableColumn<Book, String> Book_Title;

     @FXML
     private TableColumn<Book, String> Book_Author;

     @FXML
     private TableColumn<Book, String> Book_Year;

     @FXML
     private TableColumn<Book, String> Book_Publisher;

     @FXML
     private TableColumn<Book, String> Book_Language;

     @FXML
     private TableColumn<Book, ImageView> Book_Cover;





     private ObservableList<Book> bookList;

     public BookController() {
          bookList = FXCollections.observableArrayList();
     }

     @FXML
     private void initialize() {
          Book_Title.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTitle()));
          Book_Author.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAuthor()));
          Book_Year.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getPublicationYear())));
          Book_Publisher.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPublisher()));
          Book_Language.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getLanguage()));
          Book_Cover.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getImageLink()));

               loadBooks();


     }
     private void loadBooks() {
          DatabaseConnection databaseConnection = new DatabaseConnection();
          try(Connection connection = DatabaseConnection.getConnection(); Statement statement = connection.createStatement()) {
               String sql = "SELECT title, author, publication_year, publisher, language, preview_link FROM books";
               ResultSet resultSet = statement.executeQuery(sql);

               while (resultSet.next()) {
                    String title = resultSet.getString("title");
                    String author = resultSet.getString("author");
                    String publicationYear = resultSet.getString("publication_year");
                    String publisher = resultSet.getString("publisher");
                    String language = resultSet.getString("language");
                    String cover = resultSet.getString("preview_link");

                    Image image = new Image(cover);
                    ImageView coverImageView = new ImageView(image);
                    coverImageView.setFitWidth(100); // Đặt chiều rộng
                    coverImageView.setFitHeight(150); // Đặt chiều cao

                    bookList.add(new Book(title, author, publicationYear, publisher, language, coverImageView));
               }
               Document_Table.setItems(bookList);




          } catch (SQLException e) {
              e.printStackTrace();          }
     }









}





