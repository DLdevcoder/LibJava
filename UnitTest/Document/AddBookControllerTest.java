package Document;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.matcher.base.NodeMatchers.isVisible;

public class AddBookControllerTest extends ApplicationTest {

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/books/AddBook.fxml"));
        Parent root = loader.load();
        stage.setScene(new Scene(root));
        stage.show();
    }

    @Test
    public void testHandleAddBookButton_EmptyISBN() throws Exception {
        // Để trống trường ISBN
        clickOn("#AddBook_TextField").write("");
        clickOn("#ImageLink_TextField").write("http://example.com/image.jpg");
        clickOn("#EnterBookQuantity_TextField").write("5");
        clickOn("#AddBook_Button");

        // Kiểm tra hiển thị thông báo lỗi
        verifyThat(".dialog-pane", isVisible());
        verifyThat(".dialog-pane .content", (label) -> label.toString().contains("Please Enter Book Title"));
    }



    @Test
    public void testHandleAddBookButton_BookNotFound() throws Exception {
        // Nhập ISBN không hợp lệ
        clickOn("#AddBook_TextField").write("123");
        clickOn("#ImageLink_TextField").write("http://example.com/image.jpg");
        clickOn("#EnterBookQuantity_TextField").write("5");
        clickOn("#AddBook_Button");
        Stage alertStage = (Stage) targetWindow();
        assertTrue(alertStage.isShowing(), "Book not found");

    }


    @Test
    public void testHandleAddBookButton_ClearFieldsAfterError() throws Exception {
        // Nhập ISBN không hợp lệ và kiểm tra trường được xóa sau lỗi
        clickOn("#AddBook_TextField").write("123");
        clickOn("#ImageLink_TextField").write("http://example.com/image.jpg");
        clickOn("#EnterBookQuantity_TextField").write("5");
        clickOn("#AddBook_Button");

        // Kiểm tra trường được xóa
        assertEquals("", lookup("#AddBook_TextField").queryAs(TextField.class).getText());
        assertEquals("", lookup("#ImageLink_TextField").queryAs(TextField.class).getText());
        assertEquals("", lookup("#EnterBookQuantity_TextField").queryAs(TextField.class).getText());
    }
}
