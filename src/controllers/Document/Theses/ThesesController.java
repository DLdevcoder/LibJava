package controllers.Document.Theses;

import controllers.Document.Search.SearchByAuthorStrategy;
import controllers.Document.Search.SearchDocumentController;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import models.Theses;
import utils.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ThesesController {

    @FXML
    private TableView<Theses> ThesesTable;

    @FXML
    private TableColumn<Theses, String> Theses_ID;

    @FXML
    private TableColumn<Theses, String> Theses_Title;

    @FXML
    private TableColumn<Theses, String> Theses_Author;

    @FXML
    private TableColumn<Theses, String> Theses_Year;

    @FXML
    private TableColumn<Theses, String> Theses_Degree;

    @FXML
    private TableColumn<Theses, String> Theses_Institution;

    @FXML
    private TableColumn<Theses, String> Theses_Language;

    @FXML
    private TextField searchAuthorField;


    @FXML
    private Button clearButton;

    private ObservableList<Theses> thesesList;
    private SearchDocumentController searchController;

    public ThesesController() {
        thesesList = FXCollections.observableArrayList();
        searchController = new SearchDocumentController();
    }

    /**
     * Initializes the table columns, loads theses data from the database,
     * and sets up actions for the search and clear buttons.
     */
    @FXML
    private void initialize() {
        // Initialize columns
        Theses_ID.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getId())));
        Theses_Title.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTitle()));
        Theses_Author.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAuthor()));
        Theses_Year.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getPublicationYear())));
        Theses_Degree.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDegree()));
        Theses_Institution.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getInstitution()));
        Theses_Language.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getLanguage()));

        loadTheses();

        // Set up button actions
        searchAuthorField.setOnAction(event -> performSearch());
        clearButton.setOnAction(event -> clearSearch());
    }

    /**
     * Loads theses data from the database in a separate thread
     * and populates the table with the retrieved data.
     */
    private void loadTheses() {
        new Thread(() -> {
            try (Connection connection = DatabaseConnection.getConnection(); Statement statement = connection.createStatement()) {
                String sql = "SELECT id, title, author, publication_year, degree, institution, language FROM theses";
                ResultSet resultSet = statement.executeQuery(sql);

                List<Theses> theses = new ArrayList<>();
                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String title = resultSet.getString("title");
                    String author = resultSet.getString("author");
                    String year = resultSet.getString("publication_year");
                    String degree = resultSet.getString("degree");
                    String institution = resultSet.getString("institution");
                    String language = resultSet.getString("language");

                    theses.add(new Theses(id, title, author, year, degree, institution, language));
                }

                Platform.runLater(() -> {
                    thesesList.setAll(theses);
                    ThesesTable.setItems(thesesList);
                });

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }).start();
    }

    /**
     * Executes a search for theses based on the author entered in the search field
     * and updates the table to display the search results.
     */
    @FXML
    private void performSearch() {
        String authorKeyword = searchAuthorField.getText();
        searchController.setSearchStrategy(new SearchByAuthorStrategy());
        List<Theses> result = searchController.executeSearch(thesesList, authorKeyword);
        ThesesTable.setItems(FXCollections.observableArrayList(result));
    }

    /**
     * Clears the search field and resets the table to display all theses.
     */
    @FXML
    private void clearSearch() {
        searchAuthorField.clear();
        ThesesTable.setItems(thesesList);
    }
}
