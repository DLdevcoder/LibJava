package controllers.borrow_record;

import javafx.animation.ScaleTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Tooltip;
import javafx.util.Duration;
import utils.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DataStatisticsController extends SidebarController{
    @FXML
    private PieChart pieChart1; // Biểu đồ thành viên
    @FXML
    private PieChart pieChart2; // Biểu đồ sách

    @FXML
    public void initialize() {
        loadMemberData();
        loadBookData();
    }

    public void loadMemberData() {
        String query1 = "SELECT COUNT(member_id) AS sumMember FROM borrow_records";
        String query2 = "SELECT br.member_id,\n" +
                "m.name,\n" +
                "COUNT(br.record_id) AS sumBorrow\n" +
                "FROM borrow_records br \n" +
                "LEFT JOIN members m \n" +
                "ON br.member_id = m.member_id \n" +
                "GROUP BY member_id \n" +
                "ORDER BY COUNT(record_id) DESC\n" +
                "LIMIT 3";
        try (Connection conn = DatabaseConnection.getConnection()) {
            PreparedStatement stmt1 = conn.prepareStatement(query1);
            ResultSet rs = stmt1.executeQuery();
            int sumMember = 0;
            if (rs.next()) {
                sumMember = rs.getInt("sumMember");
            }

            // Kiểm tra sumBook trước khi tiếp tục
            if (sumMember == 0) {
                return;
            }

            PreparedStatement stmt2 = conn.prepareStatement(query2);
            rs = stmt2.executeQuery();
            ObservableList<PieChart.Data> memberData = FXCollections.observableArrayList();

            int sumOfTop = 0;
            while (rs.next()) {
                String name = rs.getString("name");
                int sumBorrow = rs.getInt("sumBorrow");

                // Cộng dồn vào sumOfTop
                sumOfTop += sumBorrow;
                // Thêm dữ liệu vào pie chart
                PieChart.Data data = new PieChart.Data(name, sumBorrow);
                memberData.add(data);
            }

            // Thêm "Others member" nếu còn dư
            if (sumOfTop < sumMember) {
                memberData.add(new PieChart.Data("Others member", sumMember - sumOfTop));
            }

            // Thiết lập pie chart
            pieChart1.getData().clear();
            pieChart1.getData().addAll(memberData);
            for (PieChart.Data data : memberData) {
                int originData = (int) data.getPieValue();
                double actualPercentage = (data.getPieValue() / sumMember) * 100;
                data.setName(String.format("%s %.2f%%", data.getName(), actualPercentage));
                // Cập nhật tooltip để hiển thị phần trăm
                Tooltip tooltip = new Tooltip();
                Tooltip.install(data.getNode(), tooltip);

                // Thiết lập sự kiện cho khi chuột vào
                data.getNode().setOnMouseEntered(event -> {
                    tooltip.setText("Number of documents borrowed: " + originData);
                    tooltip.show(data.getNode(), event.getScreenX() - 5, event.getScreenY() + 10); // Hiện tooltip ở vị trí gần chuột
                    ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(300), data.getNode());
                    scaleTransition.setToX(1.1);
                    scaleTransition.setToY(1.1);
                    scaleTransition.play();
                });

                // Thiết lập sự kiện cho khi chuột ra
                data.getNode().setOnMouseExited(event -> {
                    tooltip.hide();
                    ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(300), data.getNode());
                    scaleTransition.setToX(1);
                    scaleTransition.setToY(1);
                    scaleTransition.play();
                });
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void loadBookData() {
        String query1 = "SELECT IFNULL(SUM(quantity),0) AS sumBook " +
                "FROM borrow_records " +
                "WHERE status = 'borrowed';";
        String query2 = "SELECT br.document_id,\n" +
                "SUM(br.quantity) AS total_borrowed, d.title\n" +
                "FROM borrow_records br LEFT JOIN documents d ON d.id = br.document_id\n" +
                "WHERE br.status = 'borrowed'\n" +
                "GROUP BY br.document_id, d.title\n" +
                "ORDER BY total_borrowed DESC\n" +
                "LIMIT 3";

        try (Connection conn = DatabaseConnection.getConnection()) {
            PreparedStatement stmt1 = conn.prepareStatement(query1);
            ResultSet rs = stmt1.executeQuery();
            int sumBook = 0;
            if (rs.next()) {
                sumBook = rs.getInt("sumBook");
            }

            // Kiểm tra sumBook trước khi tiếp tục
            if (sumBook == 0) {
                return;
            }

            PreparedStatement stmt2 = conn.prepareStatement(query2);
            rs = stmt2.executeQuery();
            ObservableList<PieChart.Data> bookData = FXCollections.observableArrayList();

            int sumOfTop = 0;
            while (rs.next()) {
                String title = rs.getString("title");
                int totalBorrowed = rs.getInt("total_borrowed");

                // Cộng dồn vào sumOfTop
                sumOfTop += totalBorrowed;
                // Thêm dữ liệu vào pie chart
                PieChart.Data data = new PieChart.Data(title, totalBorrowed);
                bookData.add(data);
            }
            System.out.println(sumBook);

            // Thêm "Other book" nếu còn dư
            if (sumOfTop < sumBook) {
                bookData.add(new PieChart.Data("Others book", sumBook - sumOfTop));
            }

            // Thiết lập pie chart
            pieChart2.getData().clear();
            pieChart2.getData().addAll(bookData);
            for (PieChart.Data data : bookData) {
                int originData = (int) data.getPieValue();
                double actualPercentage = (data.getPieValue() / sumBook) * 100;
                data.setName(String.format("%s %.2f%%", data.getName(), actualPercentage));
                // Cập nhật tooltip để hiển thị phần trăm
                Tooltip tooltip = new Tooltip();
                Tooltip.install(data.getNode(), tooltip);

                // Thiết lập sự kiện cho khi chuột vào
                data.getNode().setOnMouseEntered(event -> {
                    tooltip.setText("Number of documents: " + originData);
                    tooltip.show(data.getNode(), event.getScreenX() - 5, event.getScreenY() + 10); // Hiện tooltip ở vị trí gần chuột
                    ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(300), data.getNode());
                    scaleTransition.setToX(1.1);
                    scaleTransition.setToY(1.1);
                    scaleTransition.play();
                });

                // Thiết lập sự kiện cho khi chuột ra
                data.getNode().setOnMouseExited(event -> {
                    tooltip.hide();
                    ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(300), data.getNode());
                    scaleTransition.setToX(1);
                    scaleTransition.setToY(1);
                    scaleTransition.play();
                });
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
