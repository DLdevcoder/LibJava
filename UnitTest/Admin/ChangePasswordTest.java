package Admin;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class ChangePasswordTest extends ApplicationTest {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/admin/ChangePassword.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    /*
     * thành công
     */
    @Test
    public void testChangePasswordButtonFunctionality() {
        clickOn("#oldPass").write("admin");
        clickOn("#newPass").write("admin");
        clickOn("#confirmPass").write("admin");
        clickOn("#save");
        Stage alertStage = (Stage) targetWindow();
        assertTrue(alertStage.isShowing(), "You have successfully changed your password!");
        sleep(2000);
    }

    /*
     * mật khẩu cũ không đúng
     */
    @Test
    public void testErrorOldPass() {
        clickOn("#oldPass").write("123");
        clickOn("#newPass").write("admin");
        clickOn("#confirmPass").write("admin");
        clickOn("#save");
        Stage alertStage = (Stage) targetWindow();
        assertTrue(alertStage.isShowing(), "Invalid old password!");
        sleep(2000);
    }

    /*
     * mật khẩu mới không khớp
     */
    @Test
    public void testErrorNewPass() {
        clickOn("#oldPass").write("admin");
        clickOn("#newPass").write("admin");
        clickOn("#confirmPass").write("123");
        clickOn("#save");
        Stage alertStage = (Stage) targetWindow();
        assertTrue(alertStage.isShowing(), "New password does not match!");
        sleep(2000);
    }
}
