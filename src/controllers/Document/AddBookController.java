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

public class AddBookController extends HeaderController {

    @FXML
    private TextField AddBook_TextField;

    @FXML
    private TextField ImageLink_TextField;

    @FXML
    private TextField EnterTheses_TextField;

    @FXML
    private TextField EnterAuthor_TextField;

    @FXML
    private TextField EnterGovermentDoc_TextField;

    @FXML
    private TextField EnterGDAuthor_TextField;

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


    public void HandleAddThesesButton(ActionEvent event) {
         String title = EnterTheses_TextField.getText().trim();
         String author = EnterAuthor_TextField.getText().trim();
         if(!title.isEmpty() && !author.isEmpty()){
             Theses theses = new Theses(title, author);
             Admin admin  = new Admin();
             admin.saveThesesToDataBase(theses);
             showAlert("Succcessfully","Theses added successfully");
         }
         else{
             showAlert("Error","Please Enter Title and Author");
         }
    }


    public void HandleAddGDButton(ActionEvent event) {
         String title = EnterGovermentDoc_TextField.getText().trim();
         String author = EnterGDAuthor_TextField.getText().trim();
         if(!title.isEmpty() && !author.isEmpty()){
             GovernmentDocuments governmentDocuments = new GovernmentDocuments(title, author);
             Admin admin  = new Admin();
             admin.saveGDToDatabase(governmentDocuments);
             showAlert("Succcessfully","GD added successfully");

         }
         else{
             showAlert("Error","Please Enter Title and Author");
         }

    }
}






