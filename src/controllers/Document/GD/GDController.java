package controllers.Document.GD;

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
import models.GovernmentDocuments;
import utils.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GDController {

    @FXML
    private TableView<GovernmentDocuments> GD_Table;

    @FXML
    private TableColumn<GovernmentDocuments, String> GD_ID;

    @FXML
    private TableColumn<GovernmentDocuments, String> GD_Title;

    @FXML
    private TableColumn<GovernmentDocuments, String> GD_Author;

    @FXML
    private TableColumn<GovernmentDocuments, String> GD_Year;

    @FXML
    private TableColumn<GovernmentDocuments, String> GD_Descriptions;

    @FXML
    private TableColumn<GovernmentDocuments, String> GD_Language;



    @FXML
    private TableColumn<GovernmentDocuments, String> GD_Type;



    @FXML
    private TextField searchGDAuthorField;



    @FXML
    private Button searchButton;

    @FXML
    private Button clearButton;

    private ObservableList<GovernmentDocuments> gdList;

    private SearchDocumentController searchController;

    public GDController() {
        gdList = FXCollections.observableArrayList();
        searchController = new SearchDocumentController();
    }

    /**
     * Initializes the table columns, loads GovernmentDocuments data from the database,
     * and sets up actions for the search and clear buttons.
     */
    @FXML
    private void initialize() {
        // Initialize columns
        GD_ID.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getId())));
        GD_Title.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTitle()));
        GD_Author.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAuthor()));
        GD_Year.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getPublicationYear())));
        GD_Type.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDocumentType()));
        GD_Descriptions.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDescriptions()));
        GD_Language.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getLanguage()));


        loadGD();

        // Set up button actions
        searchButton.setOnAction(event -> performSearch());
        clearButton.setOnAction(event -> clearSearch());
    }

    /**
     * Loads Government Documents data from the database in a separate thread
     * and populates the table with the retrieved data.
     */
    private void loadGD() {
        new Thread(() -> {
            try (Connection connection = DatabaseConnection.getConnection(); Statement statement = connection.createStatement()) {
                String sql = "SELECT id, title, author, publication_year,document_type, description, language FROM governmentdocuments";
                ResultSet resultSet = statement.executeQuery(sql);

                List<GovernmentDocuments> gd = new ArrayList<>();
                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String title = resultSet.getString("title");
                    String author = resultSet.getString("author");
                    String year = resultSet.getString("publication_year");
                    String type = resultSet.getString("document_type");
                    String descriptions = resultSet.getString("description");
                    String language = resultSet.getString("language");


                    gd.add(new GovernmentDocuments(id, title, author, year, type, descriptions, language));
                }

                Platform.runLater(() -> {
                    gdList.setAll(gd);
                    GD_Table.setItems(gdList);
                });

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }).start();
    }

    /**
     * Executes a search for Government Documents based on the author entered in the search field
     * and updates the table to display the search results.
     */
    @FXML
    private void performSearch() {
        String authorKeyword = searchGDAuthorField.getText();
        searchController.setSearchStrategy(new SearchByAuthorStrategy());
        List<GovernmentDocuments> result = searchController.executeSearch(gdList, authorKeyword);
        GD_Table.setItems(FXCollections.observableArrayList(result));
    }

    /**
     * Clears the search field and resets the table to display all Government Documents.
     */
    @FXML
    private void clearSearch() {
        searchGDAuthorField.clear();
        GD_Table.setItems(gdList);
    }
}
