package Document;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import models.Book;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

import static org.junit.jupiter.api.Assertions.*;

public class BookControllerTest extends ApplicationTest {

    private TableView<Book> Document_Table;
    private TextField searchField;
    private TextField searchDocTextField;  // Thêm TextField mới cho header

    /**
     * This method initializes the JavaFX application.
     * It loads the FXML layout for the BookList view and displays it in the window.
     *
     * @param stage The main window of the JavaFX application.
     * @throws Exception If an error occurs during the loading of the FXML file.
     */
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/books/BookList.fxml")); // Sửa đường dẫn nếu cần
        Parent root = loader.load();

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * This method sets up necessary nodes for testing.
     * It is called before each test in JUnit.
     */
    @BeforeEach
    public void setUpNodes() {
        Document_Table = lookup("#Document_Table").queryTableView();
        searchField = lookup("#searchField").query();
        searchDocTextField = lookup("#SearchDoc_TextField").query(); // Tìm kiếm TextField trong header

    }

    /**
     * This method tests the performSearch functionality.
     * It simulates a search query, performs the search, and checks the results in the table.
     */
    @Test
    public void testPerformSearch() {
        // Đảm bảo bảng có dữ liệu
        waitForFxEvents();

        // Nhập từ khóa tìm kiếm
        clickOn("#searchField").write("Harry Potter");
        clickOn("#searchButton");
        waitForFxEvents();
        try {
            Thread.sleep(2000);  // Tạm dừng 2 giây (2000ms)
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        // Kiểm tra kết quả
        assertFalse(Document_Table.getItems().isEmpty(), "Bảng kết quả trống.");
        Document_Table.getItems().forEach(book -> {
            assertTrue(book.getTitle().contains("Harry Potter"), "Không tìm thấy từ khóa trong kết quả.");
        });
    }




    /**
     * This method waits for the table to be populated (i.e., non-empty).
     * It is used to ensure that the table has been updated after a search.
     */
    private void waitForFxEvents() {
        // Đợi cho đến khi bảng không còn trống, tức là dữ liệu đã được tải
        while (Document_Table.getItems().isEmpty()) {
            try {
                Thread.sleep(100);  // Chờ 100ms trước khi kiểm tra lại
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
