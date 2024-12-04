package Document;

import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import models.Review;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

public class AddReviewsControllerTest extends ApplicationTest {

    private TextField AddBookId_TextField;
    private TextField AddComment_TextField;
    private TextField AddRating_TextField;
    private TextField AddMemberId_TextField;
    private Button AddReview_Button;


    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/books/AddReviews.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @BeforeEach
    public void setUpNodes() {
        AddBookId_TextField = lookup("#AddBookId_TextField").query();
        AddComment_TextField = lookup("#AddComment_TextField").query();
        AddRating_TextField = lookup("#AddRating_TextField").query();
        AddMemberId_TextField = lookup("#AddMemberId_TextField").query();
        AddReview_Button = lookup("#AddReview_Button").query();  // Tìm nút AddReview_Button

    }

    @Test
    public void testHandleAddReviewButton_EmptyFields() throws SQLException {
        // Test khi các trường trống
        clickOn("AddReview_Button");

        // Kiểm tra xem có thông báo lỗi về các trường trống không
        assertTrue(getAlertMessage().contains("Empty Fields"));
    }

    @Test
    public void testHandleAddReviewButton_InvalidBookOrMember() throws SQLException {
        // Cung cấp BookId và MemberId không tồn tại
        clickOn("#AddBookId_TextField").write("9999");  // Book ID giả mạo
        clickOn("#AddMemberId_TextField").write("9999");  // Member ID giả mạo
        clickOn("#AddComment_TextField").write("Great book!");
        clickOn("#AddRating_TextField").write("4.5");

        clickOn("AddReview_Button");

        // Kiểm tra xem có thông báo lỗi về việc không tìm thấy Book hoặc Member không
        assertTrue(getAlertMessage().contains("Book or member does not exist"));
    }

    @Test
    public void testHandleAddReviewButton_Success() throws SQLException {
        // Cung cấp thông tin hợp lệ
        clickOn("#AddBookId_TextField").write("145");
        clickOn("#AddMemberId_TextField").write("1");
        clickOn("#AddComment_TextField").write("Great book!");
        clickOn("#AddRating_TextField").write("4.5");

        clickOn("AddReview_Button");

        // Kiểm tra xem thông báo thành công có xuất hiện không
        assertTrue(getAlertMessage().contains("Successfully Added Review"));
    }
    private String alertMessage = "";  // Biến lưu trữ thông báo

    private void showAlert(String title, String message) {
        // Hiển thị Alert và lưu thông báo vào biến
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);

        // Lưu thông báo vào biến
        alertMessage = message;

        alert.showAndWait();
    }

    private String getAlertMessage() {
        // Trả về thông báo đã lưu
        return alertMessage;
    }

}






