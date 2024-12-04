package Member;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class AddMemberTest extends ApplicationTest {
    public void check(String message) {
        Stage alertStage = (Stage) targetWindow();
        assertTrue(alertStage.isShowing(), message);
        sleep(2000);
    }

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/members/AddMember.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    // thành công
    @Test
    public void testAddMember() {
        clickOn("#nameField").write("Nguyen Van A");
        clickOn("#emailField").write("aaa@gmail.com");
        clickOn("#passwordField").write("123456");
        clickOn("#addressField").write("Ha Noi");
        clickOn("#phoneField").write("0123456789");
        clickOn("#add");
        check("Member added successfully!");
    }

    // trường nào cũng trống
    @Test
    public void testEmptyField() {
        clickOn("#add");
        check("Please fill in all fields!");
    }

    // tên không phải chữ
    @Test
    public void testErrorName() {
        clickOn("#nameField").write("Nguyen Van A1");
        clickOn("#emailField").write("c@gmail.com");
        clickOn("#passwordField").write("123456");
        clickOn("#addressField").write("Ha Noi");
        clickOn("#phoneField").write("0123456789");
        clickOn("#add");
        check("Name must contain only letters and spaces!");
    }

    // email không đúng định dạng
    @Test
    public void testErrorEmail() {
        clickOn("#nameField").write("Nguyen Van A");
        clickOn("#emailField").write("aaa");
        clickOn("#passwordField").write("123456");
        clickOn("#addressField").write("Ha Noi");
        clickOn("#phoneField").write("0123456789");
        clickOn("#add");
        check("Invalid email format!");
    }

    // số điện thoại không phải số
    @Test
    public void testErrorPhone() {
        clickOn("#nameField").write("Nguyen Van A");
        clickOn("#emailField").write("b@gmail.com");
        clickOn("#passwordField").write("123456");
        clickOn("#addressField").write("Ha Noi");
        clickOn("#phoneField").write("abc");
        clickOn("#add");
        check("Phone number must be a number!");
    }

    // mật khẩu không đủ 6 ký tự
    @Test
    public void testErrorPassword() {
        clickOn("#nameField").write("Nguyen Van A");
        clickOn("#emailField").write("b@gmail.com");
        clickOn("#passwordField").write("123");
        clickOn("#addressField").write("Ha Noi");
        clickOn("#phoneField").write("abc");
        clickOn("#add");
        check("Password must be at least 6 characters long!");
    }

}
