package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.nio.file.Paths;

public class MainApp extends Application {
    @Override
    public void start(Stage primaryStage) {
        try {
            primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("/icon_app.png")));
            FXMLLoader loader = new FXMLLoader(Paths.get("src/views/frame/Frame.fxml").toUri().toURL());
            Parent root = loader.load();
            Scene scene = new Scene(root, 930, 690);
            scene.getStylesheets().add(Paths.get("resources/Frame.css").toUri().toString());
            primaryStage.setTitle("ManageLib");
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
