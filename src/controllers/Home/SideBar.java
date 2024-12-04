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

    // Enum hoặc constants để lưu trữ đường dẫn FXML
    public enum ScenePaths {
        SUGGESTION("/views/Home/Suggestion.fxml"),
        DOCUMENTS("/views/Home/Documents.fxml"),
        BORROWS("/views/Home/Borrows.fxml"),
        REVIEWS("/views/Home/Reviews.fxml");

        private final String path;

        ScenePaths(String path) {
            this.path = path;
        }

        public String getPath() {
            return this.path;
        }
    }

    // Phương thức chung để thay đổi scene
    private void changeScene(ActionEvent event, ScenePaths scenePath) {
        try {
            // Đảm bảo rằng bạn sử dụng đúng đường dẫn
            root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(scenePath.getPath())));
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            // Cải thiện thông báo lỗi cho người dùng
            e.printStackTrace();
            System.out.println("Error loading FXML file: " + scenePath.getPath());
        }
    }

    // Các phương thức hành động cho từng scene
    public void sceneSuggestion(ActionEvent event) {
        changeScene(event, ScenePaths.SUGGESTION);
    }

    public void sceneDocuments(ActionEvent event) {
        changeScene(event, ScenePaths.DOCUMENTS);
    }

    public void sceneBorrows(ActionEvent event) {
        changeScene(event, ScenePaths.BORROWS);
    }

    public void sceneReviews(ActionEvent event) {
        changeScene(event, ScenePaths.REVIEWS);
    }
}
