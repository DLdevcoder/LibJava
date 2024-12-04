package Member;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class FindMemberByIDTest extends ApplicationTest {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/members/FindMember.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    // thanh cong
    @Test
    public void testFindMemberByID() {
        clickOn("#idField").write("3");
        clickOn("#searchButton");
        Stage alertStage = (Stage) targetWindow();
        assertTrue(alertStage.isShowing(), "Found member!");
        sleep(2000);
    }
    // khong tim thay
    @Test
    public void testErrorFindMemberByID() {
        clickOn("#idField").write("1000000");
        clickOn("#searchButton");
        Stage alertStage = (Stage) targetWindow();
        assertTrue(alertStage.isShowing(), "Member not found!");
        sleep(2000);
    }

    // id trống
    @Test
    public void testEmptyFindMemberByID() {
        clickOn("#searchButton");
        Stage alertStage = (Stage) targetWindow();
        assertTrue(alertStage.isShowing(), "Empty ID!");
        sleep(2000);
    }

    // id không hợp lệ
    @Test
    public void testInvalidFindMemberByID() {
        clickOn("#idField").write("abc");
        clickOn("#searchButton");
        Stage alertStage = (Stage) targetWindow();
        assertTrue(alertStage.isShowing(), "Invalid ID!");
        sleep(2000);
    }

}
