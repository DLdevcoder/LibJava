package controllers.Home;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class SideBar {
    protected Parent root;
    protected Stage stage;
    protected Scene scene;

    public void changeScene(ActionEvent event, String path) {
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(path)));
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error loading FXML file.");
        }
    }
    public void sceneSuggestion(ActionEvent event) {
        changeScene(event, "/views/Home/Suggestion.fxml");
    }

    public void sceneDocuments(ActionEvent event) {
        changeScene(event,"/views/Home/Documents.fxml");
    }

    public void sceneBorrows(ActionEvent event) {
        changeScene(event,"/views/Home/Borrows.fxml");
    }

    public void sceneReviews(ActionEvent event) {
        changeScene(event,"/views/Home/Reviews.fxml");
    }
}
