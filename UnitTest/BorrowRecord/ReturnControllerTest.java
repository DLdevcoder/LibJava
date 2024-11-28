package BorrowRecord;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationTest;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ReturnControllerTest extends ApplicationTest {

    @Override
    public void start(Stage stage) throws Exception {
        // Tải giao diện từ tệp FXML
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/borrow_records/Return.fxml"));
        Parent root = loader.load();

        // Đặt Scene cho Stage
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @Test
    public void testErrorDoc() {
        // Nhập giá trị không hợp lệ vào TextField documentIdField
        clickOn("#documentIdField").write("9.9");
        clickOn("#returnButton");
        Label errorDoc = lookup("#errorDoc").queryAs(Label.class);
        assertEquals("Invalid document ID! Please enter a valid number.", errorDoc.getText());
    }

    @Test
    public void testErrorMem() {
        // Mô phỏng nhập sai
        clickOn("#memberIdField").write("-7");
        clickOn("#documentIdField");
        Label errorMem = lookup("#errorMem").queryAs(Label.class);
        assertEquals("Invalid member ID! Please enter a valid number.", errorMem.getText());
    }

    @Test
    public void testErrorDate() {
        FxRobot robot = new FxRobot();
        robot.clickOn("#documentIdField").write("135");
        robot.clickOn("#memberIdField").write("3");
        robot.clickOn("#quantityField").write("1");
        clickOn("#returnDate");
        interact(() -> {
            DatePicker returnDate = lookup("#returnDate").queryAs(DatePicker.class);
            returnDate.setValue(LocalDate.parse("2010-01-01"));
        });
        robot.clickOn("#returnButton");
        Stage alertStage = (Stage) robot.targetWindow();
        assertTrue(alertStage.isShowing(), "Member id must be a positive integer");
    }

    @Test
    public void testErrorQuantity() {
        clickOn("#quantityField").write("2.5");
        clickOn("#returnButton");
        Label errorQuantity = lookup("#errorQuantity").queryAs(Label.class);
        assertEquals("Invalid quantity! Please enter a valid number.", errorQuantity.getText());
    }
}
