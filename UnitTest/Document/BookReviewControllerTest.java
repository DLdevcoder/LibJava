package Document;

import controllers.Document.BookReviewController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import models.Book;
import models.Member;
import models.Review;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;
import javafx.scene.control.TableView;

import java.sql.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class BookReviewControllerTest extends ApplicationTest {

    private TableView<Review> ReviewTable;

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/books/BookReviews.fxml"));
        Parent root = loader.load();


        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @BeforeEach
    public void setUpNodes() {
        // Lấy TableView từ giao diện
        ReviewTable = lookup("#ReviewTable").queryTableView();
    }

    @Test
    public void testLoadReviewData() {
        // Đảm bảo rằng bảng được nạp dữ liệu
        waitForFxEvents();

        // Kiểm tra xem bảng có dữ liệu hay không
        assertNotNull(ReviewTable, "Bảng không được nạp.");
        assertTrue(ReviewTable.getItems().size() > 0, "Bảng không có dữ liệu.");
    }

    @Test
    public void testReviewDataContent() {
        // Kiểm tra nội dung trong bảng
        waitForFxEvents();

        // Lấy tất cả các review trong bảng
        List<Review> reviews = ReviewTable.getItems();

        // Đảm bảo rằng mỗi review có thông tin đúng
        for (Review review : reviews) {
            assertNotNull(review.getBook(), "Book không được null.");
            assertNotNull(review.getReviewText(), "Review không được null.");
            assertNotNull(review.getReviewDate(), "Review date không được null.");
            assertNotNull(review.getReviewerName(), "Reviewer name không được null.");

            // Kiểm tra rằng BookId không phải là rỗng
            assertTrue(review.getBook().getId() > 0, "BookId không hợp lệ.");
            assertFalse(review.getBook().getTitle().isEmpty(), "Book title không được rỗng.");
        }
    }

    @Test
    public void testTableColumns() {
        // Kiểm tra xem các cột trong bảng có được thiết lập đúng không
        assertNotNull(ReviewTable.getColumns().get(0), "Cột BookId không được null.");
        assertNotNull(ReviewTable.getColumns().get(1), "Cột BookTitle không được null.");
        assertNotNull(ReviewTable.getColumns().get(2), "Cột BookReviews không được null.");
        assertNotNull(ReviewTable.getColumns().get(3), "Cột ReviewDate không được null.");
        assertNotNull(ReviewTable.getColumns().get(4), "Cột BookReviewer không được null.");
    }

    // Hàm này sẽ mô phỏng sự kiện đợi khi giao diện nạp
    private void waitForFxEvents() {
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
