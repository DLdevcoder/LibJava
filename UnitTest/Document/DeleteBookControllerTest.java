package Document;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.matcher.base.NodeMatchers.isVisible;

public class DeleteBookControllerTest extends ApplicationTest {

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/books/DeleteBook.fxml"));
        Parent root = loader.load();
        stage.setScene(new Scene(root));
        stage.show();
    }

    @Test
    public void testUpdateBookName_ValidBookId() {
        // Nhập ID hợp lệ vào TextField
        clickOn("#RemoveBook_TextField").write("123");
        Label error = lookup("#BookNameLabel").queryAs(Label.class);
        assertEquals("Book Title: The Great Gatsby", error.getText());


    }

    @Test
    public void testUpdateBookName_InvalidBookId() {
        // Nhập ID không hợp lệ vào TextField
        clickOn("#RemoveBook_TextField").write("9999"); // Giả sử ID "9999" không tồn tại
        Label error = lookup("#BookNameLabel").queryAs(Label.class);
        assertEquals("Book not found!", error.getText());


    }

    @Test
    public void testHandleRemoveBookButton_EmptyId() {
        // Không nhập ID vào TextField
        clickOn("#RemoveBook_TextField").write("");

        // Nhấn nút Remove Book
        clickOn("#RemoveBook_Button");
        Stage alertStage = (Stage) targetWindow();
        assertTrue(alertStage.isShowing(), "ID cannot be empty");



    }



}
