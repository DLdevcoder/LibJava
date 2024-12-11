package Document;

import controllers.Document.Book.BookController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import models.Book;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

import static org.junit.jupiter.api.Assertions.*;

public class BookControllerTest extends ApplicationTest {

    private TableView<Book> Document_Table;


    @BeforeEach
    public void setUpNodes() {
        // Lookup UI elements
        Document_Table = lookup("#Document_Table").queryTableView();

    }


    @Override
    public void start(Stage stage) throws Exception {
        // Tải FXML chứa giao diện UpdateBook
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/books/BookList.fxml"));
        Parent root = loader.load();
        stage.setScene(new Scene(root));
        stage.show();
    }

    @Test
    public void SearchBookByTitleTest() {
        clickOn("#searchField").write("Harry Potter");
        clickOn("#searchButton");
        assertTrue(Document_Table.getItems().stream()
                        .anyMatch(book -> book.getTitle().contains("Harry Potter")),
                "The table should contain books with the title 'Harry Potter'");
    }

    @Test
    public void InvalidSearchBookTest() {
        clickOn("#searchField").write("kdsdjbchsdjf");
        clickOn("#searchButton");
        assertEquals(0,Document_Table.getItems().size(), "There should be no books matching 'Invalid Title'");


    }






}
