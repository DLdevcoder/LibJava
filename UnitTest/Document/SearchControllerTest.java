package Document;

import controllers.Document.Search.SearchController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.testfx.framework.junit5.ApplicationTest;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class   SearchControllerTest extends ApplicationTest {

    private SearchController searchController;
    private TextField searchField;
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/books/BookList.fxml")); // Sửa đường dẫn nếu cần
        Parent root = loader.load();

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }



}