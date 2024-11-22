package controllers.Document;

import controllers.HeaderController;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class DocumentSideBarController extends HeaderController {
    public void ScenceBookList(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/views/books/Booklist.fxml")));
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
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
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

    public void AddReviewScence(ActionEvent event) {
        {
            try{
                Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/views/books/AddReviews.fxml")));
                stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                scene = new Scene(root);
                stage.setScene(scene);
                stage.show();

            } catch(Exception e){
                e.printStackTrace();
            }
        }
    }
}

