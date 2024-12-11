package controllers.Document.Book;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import controllers.Document.DocumentSideBarController;
import controllers.Document.QRCodeWindowController;
import controllers.Document.Search.SearchByTitleStrategy;
import controllers.Document.Search.SearchDocumentController;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.image.WritableImage;
import javafx.stage.Stage;
import models.Book;
import utils.DatabaseConnection;

import java.io.IOException;
import java.sql.*;
import java.util.List;

public class BookController extends DocumentSideBarController {

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
          searchButton.setOnAction(event -> performSearch());
          clearButton.setOnAction(event -> clearSearch());

          // Set up row click listener
          Document_Table.setOnMouseClicked(event -> {
               if (event.getClickCount() == 2) {
                    Book selectedBook = Document_Table.getSelectionModel().getSelectedItem();
                   try{
                         showQRCodeWindow(selectedBook.getThumbnail()); // Hiển thị QR từ link trong `thumbnail`

                    } catch (NullPointerException e) {
                        showAlert("Error","This featured is not updated yet");
                        e.printStackTrace();
                   }
               }
          });
     }

     private void loadBooks() {
          new Thread(() -> {
               try (Connection connection = DatabaseConnection.getConnection();
                    Statement statement = connection.createStatement()) {
                    String sql = "SELECT id, title, author, publication_year, publisher, language, preview_link, thumbnail FROM books";
                    ResultSet resultSet = statement.executeQuery(sql);

                    while (resultSet.next()) {
                         int id = resultSet.getInt("id");
                         String title = resultSet.getString("title");
                         String author = resultSet.getString("author");
                         String publicationYear = resultSet.getString("publication_year");
                         String publisher = resultSet.getString("publisher");
                         String language = resultSet.getString("language");
                         String cover = resultSet.getString("preview_link");
                         String thumbnail = resultSet.getString("thumbnail");

                         Image image = new Image(cover);
                         ImageView coverImageView = new ImageView(image);
                         coverImageView.setFitWidth(100);
                         coverImageView.setFitHeight(150);

                         Book book = new Book(id, title, author, publicationYear, publisher, language, coverImageView,thumbnail);

                         Platform.runLater(() -> {
                              bookList.add(book);
                              Document_Table.setItems(bookList);
                         });


                    }
               }catch (SQLException e) {
                    e.printStackTrace();
               }
          }).start();
     }

     /**
      * Displays a QR code in a separate modal window.
      * The QR code represents the link to the selected book.
      *
      * @param url the QR code image to be displayed
      */
     private void showQRCodeWindow(String url) {
          try {
               // Tạo mã QR từ URL
               QRCodeWriter qrCodeWriter = new QRCodeWriter();
               BitMatrix bitMatrix = qrCodeWriter.encode(url, BarcodeFormat.QR_CODE, 300, 300);

               WritableImage qrImage = new WritableImage(300, 300);
               for (int x = 0; x < 300; x++) {
                    for (int y = 0; y < 300; y++) {
                         qrImage.getPixelWriter().setColor(x, y, bitMatrix.get(x, y) ? Color.BLACK : Color.WHITE);
                    }
               }

               // Tải FXML của cửa sổ QR
               FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/books/QRCodeWindow.fxml"));
               Stage stage = new Stage();
               stage.setScene(new Scene(loader.load()));

               // Truyền mã QR vào QRCodeWindowController
               QRCodeWindowController controller = loader.getController();
               controller.setQRCodeImage(qrImage);

               stage.setTitle("QR Code Viewer");
               stage.show();
          } catch (WriterException | IOException e) {
               e.printStackTrace();
          }
     }


     /**
      * Executes a search for books based on the keyword entered in the search field
      * and updates the table to display the search results.
      */
     @FXML
     private void performSearch() {
          String keyword = searchField.getText();
          searchController.setSearchStrategy(new SearchByTitleStrategy());
          List<Book> result = searchController.executeSearch(bookList, keyword);
          Document_Table.setItems(FXCollections.observableArrayList(result));
     }
     /**
      * Clears the search field and resets the table to display all books.
      */
     @FXML
     private void clearSearch() {
          searchField.clear();
          Document_Table.setItems(bookList);
     }
}
