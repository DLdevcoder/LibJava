package controllers.Home;

import controllers.HeaderController;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import utils.DatabaseConnection;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class HomeController  {
    @FXML
    private PieChart Home_PieChart;

    @FXML
    private void initialize() {
        loadPieChartData();
    }

    private void loadPieChartData(){
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
        DatabaseConnection databaseConnection = new DatabaseConnection();
        try(Connection connection = databaseConnection.getConnection()){
            String query = "SELECT language, COUNT(*) AS count FROM books GROUP BY language";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while(resultSet.next()){
                String language = resultSet.getString("language");
                int count = resultSet.getInt("count");
                pieChartData.add(new PieChart.Data(language, count));
            }
        } catch(Exception e){
            e.printStackTrace();
        }
        Home_PieChart.setData(pieChartData);

    }
}
