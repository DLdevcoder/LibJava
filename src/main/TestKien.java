package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.util.Objects;

public class TestKien extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.getIcons().add(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/image/icon_app.png"))));
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/login/Login.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("/stylesheet/Frame.css").toExternalForm());
        primaryStage.setTitle("ManageLib");
        primaryStage.setWidth(1270);
        primaryStage.setHeight(750);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

}
