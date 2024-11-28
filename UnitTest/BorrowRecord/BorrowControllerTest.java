package BorrowRecord;

import org.testfx.framework.junit5.ApplicationTest;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BorrowControllerTest extends ApplicationTest {

    private TextField textField;

    @Override
    public void start(Stage stage) {
        textField = new TextField();
        textField.setId("documentIdField");
        Scene scene = new Scene(textField, 200, 100);
        stage.setScene(scene);
        stage.show();
    }

    @Test
    public void testTextFieldInput() {
        clickOn("#documentIdField").write("123");
        assertEquals("123", textField.getText());
    }
}
