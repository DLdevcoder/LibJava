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

public class AddDocumentController extends DocumentSideBarController {

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

    @FXML
    private TextField EnterDegree_TextField;

    @FXML
    private TextField EnterInstitution_TextField;

    @FXML
    private TextField EnterYear_TextField;

    @FXML
    private TextField EnterGDType_TextField;

    @FXML
    private TextField EnterGDYear_TextField;

    @FXML
    private TextField EnterGDDescription_TextField;



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
                clearBookFields();
                showAlert("Error", "Book not found");

            }
        } else {
            // Hiển thị thông báo lỗi nếu ISBN bị bỏ trống
            showAlert("Error", "Please Enter Book Title");
            clearBookFields();
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
        String degree = EnterDegree_TextField.getText().trim();
        String year = EnterYear_TextField.getText().trim();
        String istitution = EnterInstitution_TextField.getText().trim();

        int quantity = Integer.parseInt(EnterThesesQuantity_TextField.getText().trim());

        if (!title.isEmpty() && !author.isEmpty()) {
            Theses theses = new Theses(title, author, quantity, degree, istitution, year);

            Admin admin = Admin.getInstance();

            admin.saveThesesToDataBase(theses);

            showAlert("Successfully", "Theses added successfully");
            clearThesesFields();
        } else {
            showAlert("Error", "Please Enter Title and Author");
            clearThesesFields();
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
        String type = EnterGDType_TextField.getText().trim();
        String year = EnterGDYear_TextField.getText().trim();
        String description = EnterGDDescription_TextField.getText().trim();


        int quantity = Integer.parseInt(EnterGDQuantity_TextField.getText().trim());

        if (!title.isEmpty() && !author.isEmpty()) {
            GovernmentDocuments governmentDocuments = new GovernmentDocuments(title, author, quantity,type, year, description);

            // Lấy thể hiện duy nhất của lớp `Admin` (Singleton pattern)
            Admin admin = Admin.getInstance();

            admin.saveGDToDatabase(governmentDocuments);

            showAlert("Successfully", "GD added successfully");
            clearGDFields();
        } else {
            showAlert("Error", "Please Enter Title and Author");
            clearGDFields();
        }
    }
    private void clearBookFields() {
        AddBook_TextField.clear();
        ImageLink_TextField.clear();
        EnterBookQuantity_TextField.clear();
    }

    private void clearThesesFields() {
        EnterTheses_TextField.clear();
        EnterAuthor_TextField.clear();
        EnterDegree_TextField.clear();
        EnterYear_TextField.clear();
        EnterInstitution_TextField.clear();
        EnterThesesQuantity_TextField.clear();
    }

    private void clearGDFields() {
        EnterGovermentDoc_TextField.clear();
        EnterGDAuthor_TextField.clear();
        EnterGDType_TextField.clear();
        EnterGDYear_TextField.clear();
        EnterGDDescription_TextField.clear();
        EnterGDQuantity_TextField.clear();
    }



}






