package controllers.Home;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.NumberAxis; // Import NumberAxis
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import utils.DatabaseConnection;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class Borrows {

    @FXML
    private LineChart<String, Number> borrowLineChart;

    @FXML
    private Label totalBooksLabel; // Thêm Label để hiển thị tổng số sách đã mượn

    @FXML
    private void initialize() {
        loadLineChartData();
    }

    private void loadLineChartData() {
        ObservableList<XYChart.Series<String, Number>> lineChartData = FXCollections.observableArrayList();
        XYChart.Series<String, Number> dataSeries = new XYChart.Series<>();
        dataSeries.setName("Books Borrowed in 2024");

        // Lấy trục Y và đặt các giá trị chỉ hiển thị số nguyên
        NumberAxis yAxis = (NumberAxis) borrowLineChart.getYAxis();
        yAxis.setForceZeroInRange(false); // Không bắt buộc zero ở trục Y

        // Cấu hình định dạng số trên trục Y để chỉ hiển thị số nguyên
        yAxis.setTickLabelFormatter(new javafx.scene.chart.NumberAxis.DefaultFormatter(yAxis) {
            @Override
            public String toString(Number value) {
                return String.format("%.0f", value.doubleValue()); // Chỉ hiển thị số nguyên
            }
        });

        // Đặt trục Y từ 0 đến 10
        yAxis.setAutoRanging(false);
        yAxis.setLowerBound(0);
        yAxis.setUpperBound(25);
        yAxis.setTickUnit(1);

        // Khởi tạo mảng tháng, tất cả đều bắt đầu là 0
        int[] monthlyData = new int[12];
        int totalBooksBorrowed = 0; // Biến để tính tổng số sách mượn trong năm

        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "SELECT MONTH(borrow_date) AS month, SUM(quantity_borrow) AS total_borrowed " +
                    "FROM borrow_records WHERE borrow_date BETWEEN DATE_SUB(CURDATE(), INTERVAL 1 YEAR) AND CURDATE() " +
                    "GROUP BY MONTH(borrow_date) ORDER BY MONTH(borrow_date)";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            // Cập nhật dữ liệu tháng thực tế vào mảng monthlyData và tính tổng số sách mượn
            while (resultSet.next()) {
                int month = resultSet.getInt("month") - 1; // Tháng trong cơ sở dữ liệu là từ 1-12, nên trừ đi 1 để phù hợp với mảng (0-11)
                int totalBorrowed = resultSet.getInt("total_borrowed");
                monthlyData[month] = totalBorrowed;
                totalBooksBorrowed += totalBorrowed; // Cộng dồn số sách mượn vào tổng
            }

            // Thêm dữ liệu vào series
            for (int i = 0; i < 12; i++) {
                dataSeries.getData().add(new XYChart.Data<>(getMonthName(i + 1), monthlyData[i])); // i+1 vì tháng bắt đầu từ 1
            }

            lineChartData.add(dataSeries);
            borrowLineChart.setData(lineChartData);

            // Cập nhật Label với tổng số sách mượn
            totalBooksLabel.setText("Total Books Borrowed: " + totalBooksBorrowed);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String getMonthName(int month) {
        switch (month) {
            case 1: return "January";
            case 2: return "February";
            case 3: return "March";
            case 4: return "April";
            case 5: return "May";
            case 6: return "June";
            case 7: return "July";
            case 8: return "August";
            case 9: return "September";
            case 10: return "October";
            case 11: return "November";
            case 12: return "December";
            default: return "Unknown";
        }
    }
}
