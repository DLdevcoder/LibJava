package controllers.Document;

import controllers.HeaderController;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import models.Admin;
import models.Book;
import utils.GoogleBooksAPI;

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
                  Admin admin  = new Admin();
                  admin.saveBookToDatabase(book);


              } else{
                  showAlert("Error", "Book not found");
                             }
          } else{
                  showAlert("Error","Please Enter Book Title");          }

    }


    }






