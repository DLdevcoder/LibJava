package controllers.Document;

import controllers.HeaderController;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class DocumentSideBarController extends HeaderController {
    public void ScenceBookList(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/views/books/BookList.fxml")));
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void ScenceAddBook(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/views/books/AddBook.fxml")));
            Stage stage = (Stage) ((MenuItem) event.getSource()).getParentPopup().getOwnerWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void ScenceRemoveBook(ActionEvent event) {

        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/views/books/DeleteBook.fxml")));

            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();


        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void HandleAddReviews(ActionEvent event) {
        {
            try{
                Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/views/books/AddReviews.fxml")));
                Stage stage = (Stage) ((MenuItem) event.getSource()).getParentPopup().getOwnerWindow();
                scene = new Scene(root);
                stage.setScene(scene);
                stage.show();

            } catch(Exception e){
                e.printStackTrace();
            }
        }
    }
    public void HandleShowReviews(ActionEvent event) {
        try{
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/views/books/BookReviews.fxml")));
            Stage stage = (Stage) ((MenuItem) event.getSource()).getParentPopup().getOwnerWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();

        } catch(Exception e){
            e.printStackTrace();
        }
    }

    public void ScenceAddTheses(ActionEvent event) {
        try{
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/views/books/AddTheses.fxml")));
            Stage stage = (Stage) ((MenuItem) event.getSource()).getParentPopup().getOwnerWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();

        } catch(Exception e){
            e.printStackTrace();
        }
    }

    public void ScenceAddGovermentDoc(ActionEvent event) {
        try{
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/views/books/AddGovermentDoc.fxml")));
            Stage stage = (Stage) ((MenuItem) event.getSource()).getParentPopup().getOwnerWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();

        } catch(Exception e){
            e.printStackTrace();
        }
    }
}

