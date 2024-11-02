package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import models.Book;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.ResourceBundle;

public class HeaderController implements Initializable {
    protected Parent root;
    protected Stage stage;
    protected Scene scene;
    @FXML
    private TableView<Book> Document_Table;

    @FXML
    private TableColumn<Book, String> Book_Title;

    @FXML
    private TableColumn<Book, String> Book_Author;

    @FXML
    private TableColumn<Book, String> Book_ISBN;

    @FXML
    private TableColumn<Book, Integer> Book_Year;

    @FXML
    private TableColumn<Book, String> Book_Language;

    @FXML
    private TableColumn<Book, String> Book_Publisher;

    private ObservableList<Book>   books = FXCollections.observableArrayList(
            new Book("123", "Harry Potter", "Nhat Huy", 2014, "Kim Dong", "English")
    );




    public void sceneBorrowRecordList(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/views/borrow_records/BorrowRecordList.fxml")));
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            scene.getStylesheets().add(Paths.get("src/resources/BorrowList.css").toUri().toString());
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error loading FXML file.");
        }
    }

    public void BookList(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull((getClass().getResource("/views/books/BookList.fxml"))));
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            scene.getStylesheets().add(Paths.get("src/resources/Frame.css").toUri().toString());

            stage.setScene(scene);
            stage.show();

        } catch(IOException e) {
            e.printStackTrace();
            System.out.println("Error loading FXML file.");
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


        // Set up các cột cho TableView
        Book_Title.setCellValueFactory(new PropertyValueFactory<>("title"));
        Book_Author.setCellValueFactory(new PropertyValueFactory<>("author"));
        Book_ISBN.setCellValueFactory(new PropertyValueFactory<>("isbn"));
        Book_Year.setCellValueFactory(new PropertyValueFactory<>("publicationYear"));
        Book_Publisher.setCellValueFactory(new PropertyValueFactory<>("publisher"));
        Book_Language.setCellValueFactory(new PropertyValueFactory<>("language"));

        // Đặt dữ liệu cho TableView
        Document_Table.setItems(books);
    }
}
