package controllers.Document;

import controllers.HeaderController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import models.Admin;
import models.Book;
import models.GovernmentDocuments;
import models.Theses;
import utils.GoogleBooksAPI;

import java.sql.SQLException;
import java.util.function.BiConsumer;

public class AddBookController extends HeaderController {

    @FXML
    private TextField AddBook_TextField;

    @FXML
    private TextField ImageLink_TextField;

    @FXML
    private TextField EnterBookQuantity_TextField;

    @FXML
    private TextField EnterTheses_TextField;

    @FXML
    private TextField EnterAuthor_TextField;

    @FXML
    private TextField EnterThesesQuantity_TextField;

    @FXML
    private TextField EnterGovermentDoc_TextField;

    @FXML
    private TextField EnterGDAuthor_TextField;

    @FXML
    private TextField EnterGDQuantity_TextField;


    /**
     * Method to handle adding a new book.
     * This method reads input from text fields, validates the input,
     * fetches book information using Google Books API if valid,
     * and saves the book to the database.
     * @throws SQLException If there is an issue accessing the database.
     */
    @FXML
    public void HandleAddBookButton() throws SQLException {
        String isbn = AddBook_TextField.getText().trim();
        String imageLink = ImageLink_TextField.getText().trim();
        int quantity = Integer.parseInt(EnterBookQuantity_TextField.getText().trim());

        if (!isbn.isEmpty()) {
            Book book = GoogleBooksAPI.getBookByISBN(isbn, imageLink, quantity);

            // Kiểm tra nếu sách được tìm thấy
            if (book != null) {
                showAlert("Successfully", "Book added successfully");

                // Lấy thể hiện duy nhất của lớp `Admin` (Singleton pattern)
                Admin admin = Admin.getInstance();

                // Lưu thông tin sách vào cơ sở dữ liệu thông qua `Admin`
                admin.saveBookToDatabase(book);
            } else {
                showAlert("Error", "Book not found");
            }
        } else {
            // Hiển thị thông báo lỗi nếu ISBN bị bỏ trống
            showAlert("Error", "Please Enter Book Title");
        }
    }

    /**
     * Method to handle adding a new thesis (luận văn).
     * This method reads input from text fields, validates the input,
     * and saves the thesis to the database if valid.
     * @param event The action event triggered by the button click.
     */
    public void HandleAddThesesButton(ActionEvent event) {
        String title = EnterTheses_TextField.getText().trim();
        String author = EnterAuthor_TextField.getText().trim();
        int quantity = Integer.parseInt(EnterThesesQuantity_TextField.getText().trim());

        if (!title.isEmpty() && !author.isEmpty()) {
            Theses theses = new Theses(title, author, quantity);

            Admin admin = Admin.getInstance();

            admin.saveThesesToDataBase(theses);

            showAlert("Successfully", "Theses added successfully");
        } else {
            showAlert("Error", "Please Enter Title and Author");
        }
    }

    /**
     * Method to handle adding a new government document.
     * This method reads input from text fields, validates the input,
     * and saves the document to the database if valid.
     * @param event The action event triggered by the button click.
     */
    public void HandleAddGDButton(ActionEvent event) {
        String title = EnterGovermentDoc_TextField.getText().trim();
        String author = EnterGDAuthor_TextField.getText().trim();
        int quantity = Integer.parseInt(EnterGDQuantity_TextField.getText().trim());

        if (!title.isEmpty() && !author.isEmpty()) {
            GovernmentDocuments governmentDocuments = new GovernmentDocuments(title, author, quantity);

            // Lấy thể hiện duy nhất của lớp `Admin` (Singleton pattern)
            Admin admin = Admin.getInstance();

            admin.saveGDToDatabase(governmentDocuments);

            showAlert("Successfully", "GD added successfully");
        } else {
            showAlert("Error", "Please Enter Title and Author");
        }
    }

}






