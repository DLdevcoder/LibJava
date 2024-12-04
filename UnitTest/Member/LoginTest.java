package Member;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

import java.io.IOException;

public class LoginTest extends ApplicationTest {
    @Override
    public void start(Stage stage) throws IOException {
        // Tải giao diện từ tệp FXML
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/login/Login.fxml"));
        Parent root = loader.load();

        // Đặt Scene cho Stage
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @Test
    public void testLoginButtonFunctionality() {
        clickOn("#emailField").write("admin");
        clickOn("#passwordField").write("admin");
        clickOn("#loginButton");
    }
}
