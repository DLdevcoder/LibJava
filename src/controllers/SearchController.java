package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import models.Book;
import utils.GoogleBooksAPI;

import java.util.List;

public class SearchController extends HeaderController{
    @FXML
    private TableView<Book> DocumentsTable;

    @FXML
    private TableColumn<Book, String> DocISBN;

    @FXML
    private TableColumn<Book, String> DocTitle;

    @FXML
    private TableColumn<Book, String> DocAuthor;

    @FXML
    private TableColumn<Book, String> DocYear;

    @FXML
    private TableColumn<Book, Button> DocCopy;

    private GoogleBooksAPI googleBooksAPI;
    private ObservableList<Book> bookList;

    @FXML
    public void initialize() {
        DocISBN.setCellValueFactory(new PropertyValueFactory<>("isbn"));
        DocTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        DocAuthor.setCellValueFactory(new PropertyValueFactory<>("author"));
        DocYear.setCellValueFactory(new PropertyValueFactory<>("publicationYear"));

        DocCopy.setCellFactory(col -> new TableCell<Book, Button>() {
            @Override
            protected void updateItem(Button button, boolean empty) {
                super.updateItem(button, empty);
                if (empty) {
                    setGraphic(null);  // Không có gì khi cell trống
                } else {
                    Button copy = new Button("Copy ISBN");
                    copy.setStyle(
                            "-fx-background-color: #4CAF50; " + // Màu nền xanh lá
                                    "-fx-text-fill: white; " +          // Màu chữ trắng
                                    "-fx-font-weight: bold; " +         // Chữ đậm
                                    "-fx-padding: 5 10; " +             // Khoảng cách trong nút
                                    "-fx-border-radius: 5; " +          // Bo góc nút
                                    "-fx-background-radius: 5; " +      // Bo góc nền
                                    "-fx-cursor: hand;"                 // Hiển thị bàn tay khi hover
                    );
                    copy.setOnAction(event -> {
                        // Lấy Book từ dòng hiện tại và sao chép ISBN
                        Book book = getTableRow().getItem();
                        if (book != null) {
                            copyISBN(book);
                            showAlert("Success","ISBN copied");

                        }
                    });
                    setGraphic(copy);
                }
            }
        });


    }



    public SearchController() {
        bookList = FXCollections.observableArrayList();
    }

    public void performSearch(String query) {
        if (query == null || query.isEmpty()) {
            showAlert("Error", "Please enter a valid search term");
            return;
        }

        // Gọi phương thức getBooksByQuery để lấy danh sách sách từ Google Books API
        List<Book> books = GoogleBooksAPI.getBooksByQuery(query);

        // Nếu danh sách sách không rỗng, cập nhật TableView
        if (books != null && !books.isEmpty()) {
            ObservableList<Book> observableBooks = FXCollections.observableArrayList(books);
            DocumentsTable.setItems(observableBooks);  // Cập nhật dữ liệu vào TableView
        } else {
            // Thông báo nếu không tìm thấy sách
            showAlert("Error", "No books found with the query");
        }
    }


    private void copyISBN(Book book) {
        if (book != null) {
            String isbn = book.getIsbn();
            try {
                Clipboard clipboard = Clipboard.getSystemClipboard();
                ClipboardContent content = new ClipboardContent();
                content.putString(isbn);
                clipboard.setContent(content);
            } catch (Exception e) {
                showAlert("Error","Error copying ISBN");
            }
        }
    }




}
