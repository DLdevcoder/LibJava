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

    /**
     * Switches to the Book List scene.
     * Loads the FXML for the Book List view and sets the new scene to the current stage.
     *
     * @param event the ActionEvent triggered by the button click
     */
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

    /**
     * Switches to the Add Book scene.
     * Loads the FXML for the Add Book view and sets the new scene to the current stage.
     *
     * @param event the ActionEvent triggered by the button click
     */
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

    /**
     * Switches to the Remove Book scene.
     * Loads the FXML for the Remove Book view and sets the new scene to the current stage.
     *
     * @param event the ActionEvent triggered by the button click
     */
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

    /**
     * Switches to the Add Reviews scene.
     * Loads the FXML for the Add Reviews view and sets the new scene to the current stage.
     *
     * @param event the ActionEvent triggered by the button click
     */
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

    /**
     * Switches to the Show Reviews scene.
     * Loads the FXML for the Show Reviews view and sets the new scene to the current stage.
     *
     * @param event the ActionEvent triggered by the button click
     */
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

    /**
     * Switches to the Add Theses scene.
     * Loads the FXML for the Add Theses view and sets the new scene to the current stage.
     *
     * @param event the ActionEvent triggered by the button click
     */
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

    /**
     * Switches to the Add Government Documents scene.
     * Loads the FXML for the Add Government Documents view and sets the new scene to the current stage.
     *
     * @param event the ActionEvent triggered by the button click
     */
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

