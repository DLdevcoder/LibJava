package controllers;
import com.mysql.cj.protocol.Resultset;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import models.Book;
import models.Member;
import utils.DatabaseConnection;

import java.net.URL;
import java.sql.*;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Scanner;
import java.sql.Connection;
import java.sql.PreparedStatement;
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
          loadBooks();


     }
     private void loadBooks() {
          DatabaseConnection databaseConnection = new DatabaseConnection();
          try(Connection connection = DatabaseConnection.getConnection(); Statement statement = connection.createStatement()) {
               String sql = "SELECT title, author, publication_year, publisher, language FROM books";
               ResultSet resultSet = statement.executeQuery(sql);

               while (resultSet.next()) {
                    String title = resultSet.getString("title");
                    String author = resultSet.getString("author");
                    String publicationYear = resultSet.getString("publication_year");
                    String publisher = resultSet.getString("publisher");
                    String language = resultSet.getString("language");
                    bookList.add(new Book(title, author, publicationYear, publisher, language));
               }
               Document_Table.setItems(bookList);




          } catch (SQLException e) {
              e.printStackTrace();          }
     }









}





