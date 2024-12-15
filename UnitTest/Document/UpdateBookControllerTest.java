package Document;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.testfx.api.FxAssert;
import org.testfx.framework.junit5.ApplicationTest;
import org.testfx.matcher.base.NodeMatchers;
import org.testfx.matcher.control.TextMatchers;
import org.testfx.util.WaitForAsyncUtils;

import static org.junit.jupiter.api.Assertions.*;
import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.matcher.base.NodeMatchers.isVisible;

public class UpdateBookControllerTest extends ApplicationTest {

    @Override
    public void start(Stage stage) throws Exception {
        // Tải FXML chứa giao diện UpdateBook
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/books/UpdateBook.fxml"));
        Parent root = loader.load();
        stage.setScene(new Scene(root));
        stage.show();
    }

    @Test
    public void testHandleUpdate_InvalidBookId() {
        // Nhập thông tin với ID sách không tồn tại
        clickOn("#bookIdField").write("9999"); // ID sách không tồn tại
        clickOn("#choiceBox").clickOn("Title");

        // Chọn "Title" trong ChoiceBox
        clickOn("#inputField").write("New Title"); // Nhập tiêu đề mới vào TextField

        // Nhấn nút Update
        clickOn("#updateButton");

        Stage alertStage = (Stage) targetWindow();
        assertTrue(alertStage.isShowing(), "No book found with this ID");

        // Lấy `DialogPane` trong Alert


        // Xác minh nội dung của Alert

    }
    @Test
    public void testHandleUpdate_ValidBookId() {
        // Nhập thông tin với ID sách hợp lệ
        clickOn("#bookIdField").write("1"); // Giả sử ID 1 tồn tại trong database
        clickOn("#choiceBox").clickOn("Title"); // Chọn "Title" trong ChoiceBox
        clickOn("#inputField").write("Updated Title"); // Nhập tiêu đề mới

        // Nhấn nút Update
        clickOn("#updateButton");
        Stage alertStage = (Stage) targetWindow();
        assertTrue(alertStage.isShowing(), "Book updated successfully.");





    }

    @Test
    public void testDisplayBookTitle_ValidBookId() {
        // Nhập ID sách hợp lệ
        clickOn("#bookIdField").write("123"); // Giả sử ID 1 tồn tại trong database
        Label error = lookup("#bookTitleLabel").queryAs(Label.class);
        assertEquals("Book Title: The Great Gatsby", error.getText());



    }

    @Test
    public void testDisplayBookTitle_InvalidBookId() {
        // Nhập ID sách không hợp lệ
        clickOn("#bookIdField").write("9999"); // ID không tồn tại
        Label error = lookup("#bookTitleLabel").queryAs(Label.class);
        assertEquals("No book found with this ID", error.getText());

    }

    @Test
    public void testChoiceBoxBehavior() {
        // Chọn một trường từ ChoiceBox
        clickOn("#choiceBox").clickOn("Author");

        // Kiểm tra xem TextField đã hiện và prompt text có đúng không
        verifyThat("#inputField", (TextField textField) ->
               textField.getPromptText().equals("Enter new author")
        );
    }


}
