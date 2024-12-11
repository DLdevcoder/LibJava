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

public class AddReviewsControllerTest extends ApplicationTest {

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/books/AddReviews.fxml"));
        Parent root = loader.load();
        stage.setScene(new Scene(root));
        stage.show();
    }

    @Test
    public void testUpdateLabels_BookExists() throws Exception {
        // Nhập ID sách hợp lệ
        clickOn("#AddBookId_TextField").write("123"); // Giả sử ID 1 tồn tại

        // Kiểm tra nhãn BookTitleLabel được cập nhật đúng
        verifyThat("#BookTitleLabel", (Label label) ->
                label.isVisible() && label.getText().equals("The Great Gatsby") && label.getStyle().contains("green")
        );
    }

    @Test
    public void testUpdateLabels_BookNotExists() throws Exception {
        // Nhập ID sách không hợp lệ
        clickOn("#AddBookId_TextField").write("9999");

        // Kiểm tra nhãn BookTitleLabel hiển thị "Not Found" và màu đỏ
        verifyThat("#BookTitleLabel", (Label label) ->
                label.isVisible() && label.getText().equals("Not Found")
        );
    }

    @Test
    public void testUpdateLabels_MemberExists() throws Exception {
        // Nhập ID thành viên hợp lệ
        clickOn("#AddMemberId_TextField").write("2"); // Giả sử ID 1 tồn tại

        // Kiểm tra nhãn MemberNameLabel được cập nhật đúng
        verifyThat("#MemberNameLabel", (Label label) ->
                label.isVisible() && label.getText().equals("Bob Johnson")
        );
    }

    @Test
    public void testUpdateLabels_MemberNotExists() throws Exception {
        // Nhập ID thành viên không hợp lệ
        clickOn("#AddMemberId_TextField").write("9999");

        // Kiểm tra nhãn MemberNameLabel hiển thị "Not Found" và màu đỏ
        verifyThat("#MemberNameLabel", (Label label) ->
                label.isVisible() && label.getText().equals("Not Found")
        );
    }

    @Test
    public void testHandleAddReviewButton_EmptyFields() throws Exception {
        // Nhấn nút AddReview khi không điền đủ thông tin
        clickOn("#AddBookId_TextField").write("");
        clickOn("#AddComment_TextField").write("");
        clickOn("#AddRating_TextField").write("");
        clickOn("#AddMemberId_TextField").write("");
        clickOn("#AddReview_Button");
        Stage alertStage = (Stage) targetWindow();
        assertTrue(alertStage.isShowing(), "Empty Fields");


    }

    @Test
    public void testHandleAddReviewButton_BookOrMemberNotExists() throws Exception {
        // Nhập ID sách hoặc thành viên không tồn tại
        clickOn("#AddBookId_TextField").write("9999");
        clickOn("#AddComment_TextField").write("Great book!");
        clickOn("#AddRating_TextField").write("4.5");
        clickOn("#AddMemberId_TextField").write("9999");
        clickOn("#AddReview_Button");
        Stage alertStage = (Stage) targetWindow();
        assertTrue(alertStage.isShowing(), "Book or member does not exist");

    }


}
