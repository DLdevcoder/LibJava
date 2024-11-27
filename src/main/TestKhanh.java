package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.net.URL;
import java.util.Objects;

public class TestKhanh extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        // Kiểm tra xem icon có tồn tại không trước khi thêm vào
        URL iconUrl = getClass().getResource("/resources/image/icon_app.png");
        if (iconUrl != null) {
            primaryStage.getIcons().add(new Image(iconUrl.toExternalForm()));
        } else {
            System.err.println("Icon not found!");
        }

        // Kiểm tra lại đường dẫn FXML
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/Home/Home.fxml"));
        Parent root = loader.load();

        // Tạo Scene và thêm stylesheet
        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("/resources/stylesheet/BookList.css").toExternalForm());

        // Cài đặt các thuộc tính cho primaryStage
        primaryStage.setTitle("ManageLib");
        primaryStage.setWidth(1350);
        primaryStage.setHeight(750);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
