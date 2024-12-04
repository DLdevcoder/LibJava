package Document;

import controllers.Document.AddReviewsController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

import java.sql.SQLException;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class AddReviewsControllerTest extends ApplicationTest {
    private AddReviewsController controller;
    private TextField AddBookId_TextField;
    private TextField AddComment_TextField;
    private TextField AddRating_TextField;
    private TextField AddMemberId_TextField;

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/books/AddReviews.fxml"));
        Parent root = loader.load();
        controller = loader.getController();

        stage.setScene(new Scene(root));
        stage.show();
    }

    @BeforeEach
    public void setUp() {
        AddBookId_TextField = lookup("#AddBookId_TextField").query();
        AddComment_TextField = lookup("#AddComment_TextField").query();
        AddRating_TextField = lookup("#AddRating_TextField").query();
        AddMemberId_TextField = lookup("#AddMemberId_TextField").query();
    }

    @Test
    public void testHandleAddReviewButton_ValidInput() throws Exception {
        // Arrange: Set up valid input data
        AddBookId_TextField.setText("1");
        AddComment_TextField.setText("Great book!");
        AddRating_TextField.setText("5");
        AddMemberId_TextField.setText("3");




        // Act: Click on the "Add Review" button
        clickOn("#AddReview_Button");

        // Assert: Verify that the fields are cleared after adding review
        assertEquals("", AddBookId_TextField.getText(), "Book ID field should be empty after adding review");
        assertEquals("", AddComment_TextField.getText(), "Comment field should be empty after adding review");
        assertEquals("", AddRating_TextField.getText(), "Rating field should be empty after adding review");
        assertEquals("", AddMemberId_TextField.getText(), "Member ID field should be empty after adding review");
        // Optionally, you could check if the review is added to some data structure or displayed in a table
    }

    @Test
    public void testHandleAddReviewButton_InvalidBookId() throws SQLException {
        // Arrange: Set up invalid book ID
        AddBookId_TextField.setText("9999");  // A book ID that does not exist
        AddComment_TextField.setText("Great book!");
        AddRating_TextField.setText("5");
        AddMemberId_TextField.setText("1");

        // Mocking static methods to simulate database behavior

        // Act: Click on the "Add Review" button
        clickOn("#AddReview_Button");

        // Assert: Verify that the fields are not cleared due to invalid input
        assertNotEquals("", AddBookId_TextField.getText(), "Book ID field should not be empty due to invalid input");
        assertNotEquals("", AddMemberId_TextField.getText(), "Member ID field should not be empty due to invalid input");

        // Optionally: You can also verify the "Empty Fields" scenario or check other states of the UI
    }

    @Test
    public void testHandleAddReviewButton_EmptyFields() {
        // Arrange: Set up empty fields
        AddBookId_TextField.setText("");
        AddComment_TextField.setText("");
        AddRating_TextField.setText("");
        AddMemberId_TextField.setText("");

        // Act: Click on the "Add Review" button
        clickOn("#AddReview_Button");

        // Assert: Verify that the fields are still empty (no review should be added)
        assertEquals("", AddBookId_TextField.getText(), "Book ID field should remain empty when fields are empty");
        assertEquals("", AddComment_TextField.getText(), "Comment field should remain empty when fields are empty");
        assertEquals("", AddRating_TextField.getText(), "Rating field should remain empty when fields are empty");
        assertEquals("", AddMemberId_TextField.getText(), "Member ID field should remain empty when fields are empty");
    }
}
