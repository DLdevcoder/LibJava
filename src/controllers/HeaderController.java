package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.Objects;

public class HeaderController {
    protected Parent root;
    protected Stage stage;
    protected Scene scene;

    public void sceneBorrow(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/views/borrow_records/Borrow.fxml")));
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            scene.getStylesheets().add(Paths.get("src/resources/stylesheet/Borrow.css").toUri().toString());
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error loading FXML file.");
        }
    }

    // Chuyển đến trang quản lý thành viên
    public void sceneMemberList(ActionEvent event) {
        changeScene(event, "/views/members/MemberList.fxml");
    }
      
    public void ScenceBookList(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull((getClass().getResource("/views/books/BookList.fxml"))));
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            scene.getStylesheets().add(Paths.get("src/resources/stylesheet/Frame.css").toUri().toString());

            stage.setScene(scene);
            stage.show();

        } catch(IOException e) {
            e.printStackTrace();
            System.out.println("Error loading FXML file.");
        }
    }

    public void sceneAdmin(ActionEvent event) {
        changeScene(event, "/views/admin/AdminInfo.fxml");
    }
    public void logout(ActionEvent event) {
        changeScene(event, "/views/login/Login.fxml");
    }
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
    public void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }


}
