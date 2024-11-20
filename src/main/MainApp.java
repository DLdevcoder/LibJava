package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.nio.file.Paths;
import java.util.Objects;

public class MainApp extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.getIcons().add(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/image/icon_app.png"))));

        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/views/login/Login.fxml")));
        Scene scene = new Scene(root);
        scene.getStylesheets().add(Paths.get("src/resources/stylesheet/Frame.css").toUri().toString());
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
