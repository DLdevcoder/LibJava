package BorrowRecord;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import models.BorrowRecord;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

import static org.junit.jupiter.api.Assertions.*;

public class BorrowListTest extends ApplicationTest {

    private TableView<BorrowRecord> tableView; // Đã kiểu hóa
    private TextField recordIdSearch;
    private TextField docIdSearch;
    private TextField memIdSearch;

    @Override
    public void start(Stage stage) throws Exception {
        // Tải giao diện từ tệp FXML
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/borrow_records/BorrowRecordList.fxml"));
        Parent root = loader.load();

        // Đặt Scene cho Stage
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @BeforeEach
    public void setUpNodes() {
        // Lookup UI elements
        tableView = lookup("#tableView").queryTableView();
        recordIdSearch = lookup("#recordIdSearch").query();
        docIdSearch = lookup("#docIdSearch").query();
        memIdSearch = lookup("#memIdSearch").query();
    }

    @Test
    public void testSearchByRecordId() {
        // Enter search text in recordIdSearch field
        clickOn("#recordIdSearch").write("0");
        clickOn("#buttonSearh");
        // Wait for UI update
        waitForFxEvents();

        // Verify TableView updates correctly
        assertFalse(tableView.getItems().isEmpty(), "TableView null.");
        tableView.getItems().forEach(item -> {
            assertTrue(String.valueOf(item.getRecordId()).contains("0"), "Record ID does not match search criteria.");
        });
    }

    @Test
    public void testNoResults() {
        // Enter invalid search text in recordIdSearch field
        clickOn("#recordIdSearch").write("adfdsf");
        clickOn("#buttonSearh");
        // Wait for UI update
        waitForFxEvents();

        // Verify TableView updates to show no results
        assertEquals(0, tableView.getItems().size(), "TableView null.");
    }

    private void waitForFxEvents() {
        Platform.runLater(() -> {});
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
