package Admin;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class LoginTest extends ApplicationTest {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/login/Login.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    /*
     * đúng email và password
     */
    @Test
    public void testLoginButtonFunctionality() {
        clickOn("#emailField").write("admin");
        clickOn("#passwordField").write("admin");
        clickOn("#loginButton");
        Stage alertStage = (Stage) targetWindow();
        assertTrue(alertStage.isShowing(), "You have successfully logged in!");
        sleep(2000);
    }

    /*
     * email hoặc password không đúng
     */
    @Test
    public void testErrorLogin() {
        clickOn("#emailField").write("admin");
        clickOn("#passwordField").write("123");
        clickOn("#loginButton");
        Stage alertStage = (Stage) targetWindow();
        assertTrue(alertStage.isShowing(), "Invalid email or password!");
        sleep(2000);
    }

    /*
     * email và password trống
     */
    @Test
    public void testEmptyLogin() {
        clickOn("#loginButton");
        Stage alertStage = (Stage) targetWindow();
        assertTrue(alertStage.isShowing(), "Empty email or password!");
        sleep(2000);
    }
}
