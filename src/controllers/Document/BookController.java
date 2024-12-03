package controllers.Document;

import controllers.HeaderController;
import javafx.application.Platform;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import models.Book;
import utils.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookController extends HeaderController {

     @FXML
     private TextField searchField;

     @FXML
     private Button searchButton;

     @FXML
     private Button clearButton;

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

     @FXML
     private TableColumn<Book, String> Book_ID;

     private ObservableList<Book> bookList;
     private SearchDocumentController searchController;

     public BookController() {
          bookList = FXCollections.observableArrayList();
          searchController = new SearchDocumentController();
     }

     @FXML
     private void initialize() {
          // Initialize columns
          Book_Title.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTitle()));
          Book_Author.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAuthor()));
          Book_Year.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getPublicationYear())));
          Book_Publisher.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPublisher()));
          Book_Language.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getLanguage()));
          Book_Cover.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getImageLink()));
          Book_ID.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getId())));

          loadBooks();

          // Set up button actions
          searchButton.setOnAction(event -> performSearch());
          clearButton.setOnAction(event -> clearSearch());
     }

     private void loadBooks() {
          new Thread(() -> {
               try (Connection connection = DatabaseConnection.getConnection(); Statement statement = connection.createStatement()) {
                    String sql = "SELECT id, title, author, publication_year, publisher, language, preview_link FROM books";
                    ResultSet resultSet = statement.executeQuery(sql);

                    List<Book> books = new ArrayList<>();
                    while (resultSet.next()) {
                         int id = resultSet.getInt("id");
                         String title = resultSet.getString("title");
                         String author = resultSet.getString("author");
                         String publicationYear = resultSet.getString("publication_year");
                         String publisher = resultSet.getString("publisher");
                         String language = resultSet.getString("language");
                         String cover = resultSet.getString("preview_link");

                         Image image = new Image(cover);
                         ImageView coverImageView = new ImageView(image);
                         coverImageView.setFitWidth(100);
                         coverImageView.setFitHeight(150);

                         books.add(new Book(id, title, author, publicationYear, publisher, language, coverImageView));

                    }
                    Platform.runLater(() -> {
                         bookList.setAll(books);
                         Document_Table.setItems(bookList);
                    });

               } catch (SQLException e) {
                    e.printStackTrace();
               }
          }).start();
     }

     @FXML
     private void performSearch() {
          String keyword = searchField.getText();
          searchController.setSearchStrategy(new SearchByTitleStrategy());
          List<Book> result = searchController.executeSearch(bookList, keyword);
          Document_Table.setItems(FXCollections.observableArrayList(result));
     }

     @FXML
     private void clearSearch() {
          searchField.clear();
          Document_Table.setItems(bookList);
     }
}
