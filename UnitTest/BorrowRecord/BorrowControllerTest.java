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

public class BorrowControllerTest extends ApplicationTest {

    @Override
    public void start(Stage stage) throws Exception {
        // Tải giao diện từ tệp FXML
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/borrow_records/Borrow.fxml"));
        Parent root = loader.load();

        // Đặt Scene cho Stage
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @Test
    public void testBorrowButtonFunctionality() {
        FxRobot robot = new FxRobot();
        robot.clickOn("#documentIdField").write("135");
        robot.clickOn("#memberIdField").write("2");
        robot.clickOn("#quantityField").write("1");
        robot.clickOn("#borrowButton");
        Stage alertStage = (Stage) robot.targetWindow();
        assertTrue(alertStage.isShowing(), "You have successfully borrowed the book!");
    }

    @Test
    public void testErrorDoc() {
        // Nhập giá trị không hợp lệ vào TextField documentIdField
        clickOn("#documentIdField").write("-1");
        clickOn("#borrowButton");
        Label errorDoc = lookup("#errorDoc").queryAs(Label.class);
        assertEquals("Invalid document ID! Please enter a valid number.", errorDoc.getText());
    }

    @Test
    public void testErrorMem() {
        // Mô phỏng nhập sai
        clickOn("#memberIdField").write("1.9");
        clickOn("#documentIdField");
        Label errorMem = lookup("#errorMem").queryAs(Label.class);
        assertEquals("Invalid member ID! Please enter a valid number.", errorMem.getText());
    }

    @Test
    public void testErrorDate1() {
        clickOn("#dueDate");
        interact(() -> {
            DatePicker dueDate = lookup("#dueDate").queryAs(DatePicker.class);
            dueDate.setValue(LocalDate.parse("2050-01-01"));
        });
        // Chuyển focus để kiểm tra nếu logic xử lý xảy ra
        clickOn("#documentIdField");
        Label errorDate = lookup("#errorDate").queryAs(Label.class);
        assertEquals("Books can only be borrowed within 14 days, please re-enter!",
                errorDate.getText());
    }

    @Test
    public void testErrorDate2() {
        clickOn("#dueDate");
        interact(() -> {
            DatePicker dueDate = lookup("#dueDate").queryAs(DatePicker.class);
            dueDate.setValue(LocalDate.parse("2010-01-01")); // Đặt giá trị về null
        });
        // Chuyển focus để kiểm tra nếu logic xử lý xảy ra
        clickOn("#documentIdField");
        Label errorDate = lookup("#errorDate").queryAs(Label.class);
        assertEquals("The book return date cannot be less than the book's borrow date, please re-enter!",
                errorDate.getText());
    }

    @Test
    public void testErrorQuantity() {
        clickOn("#quantityField").write("a.5");
        clickOn("#borrowButton");
        Label errorQuantity = lookup("#errorQuantity").queryAs(Label.class);
        assertEquals("Invalid quantity! Please enter a valid number.", errorQuantity.getText());
    }

    @Test
    public void testBorrowButtonFail1() {
        FxRobot robot = new FxRobot();
        robot.clickOn("#documentIdField").write("1.6");
        robot.clickOn("#memberIdField").write("3");
        robot.clickOn("#quantityField").write("1");
        robot.clickOn("#borrowButton");
        Stage alertStage = (Stage) robot.targetWindow();
        assertTrue(alertStage.isShowing(), "Document id must be a positive integer");
    }

    @Test
    public void testBorrowButtonFail2() {
        FxRobot robot = new FxRobot();
        robot.clickOn("#documentIdField").write("123");
        robot.clickOn("#memberIdField").write("-10");
        robot.clickOn("#quantityField").write("1");
        robot.clickOn("#borrowButton");
        Stage alertStage = (Stage) robot.targetWindow();
        assertTrue(alertStage.isShowing(), "Member id must be a positive integer");
    }
}
