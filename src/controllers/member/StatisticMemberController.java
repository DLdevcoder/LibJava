package controllers.member;

import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ComboBox;
import javafx.collections.FXCollections;
import utils.DatabaseConnection;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class StatisticMemberController extends SidebarMemberController {

    @FXML
    private BarChart<String, Number> barChart;

    @FXML
    private CategoryAxis xAxis;

    @FXML
    private NumberAxis yAxis;

    @FXML
    private ComboBox<Integer> yearSelector;

    private final String[] months = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};

    /**
     * khởi tạo controller
     */
    @FXML
    public void initialize() {
        // Populate ComboBox with available years
        yearSelector.setItems(FXCollections.observableArrayList(2023, 2024));
        yearSelector.setValue(2024);
        loadChartData(2024);
        yearSelector.setOnAction(event -> {
            int selectedYear = yearSelector.getValue();
            loadChartData(selectedYear);
        });

        yAxis.setTickUnit(4);

        yAxis.setTickMarkVisible(true);
        yAxis.setMinorTickVisible(false);
        yAxis.setTickLabelFormatter(new javafx.util.StringConverter<Number>() {
            @Override
            public String toString(Number object) {
                return object.intValue() + ""; // Display as integer
            }

            @Override
            public Number fromString(String string) {
                return Integer.parseInt(string); // Parse back to integer
            }
        });
    }

    /**
     * load dữ liệu biểu đồ
     * @param year năm
     */
    private void loadChartData(int year) {
        barChart.getData().clear(); // Clear existing data

        XYChart.Series<String, Number> series = new XYChart.Series<>();
        for (int i = 1; i <= 12; i++) {
            series.getData().add(new XYChart.Data<>(months[i - 1], 0));
        }
        barChart.getData().add(series);
        String query = String.format(
                "SELECT MONTH(membership_date) AS month, COUNT(*) AS total " +
                        "FROM members WHERE YEAR(membership_date) = %d " +
                        "GROUP BY MONTH(membership_date)", year);

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                int monthIndex = rs.getInt("month") - 1; // Month index (starts from 0)
                int total = rs.getInt("total");

                // Update the data for the corresponding month
                XYChart.Data<String, Number> data = series.getData().get(monthIndex);
                data.setYValue(total); // Update the value
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

