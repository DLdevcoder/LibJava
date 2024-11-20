package controllers.borrow_record;

import javafx.animation.ScaleTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.stage.Popup;
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
                Popup customTooltip = new Popup();
                Label tooltipLabel = new Label();
                tooltipLabel.setStyle("-fx-background-color: #68d69d; " +
                        "-fx-text-fill: #401d83;" +
                        "-fx-padding: 5; " +
                        "-fx-border-color: #73ec8b ; " +
                        "-fx-border-width: 1; " +
                        "-fx-border-radius: 10; " +
                        "-fx-background-radius: 10;");
                customTooltip.getContent().add(tooltipLabel);

                // Sự kiện khi chuột vào Node
                data.getNode().setOnMouseEntered(event -> {
                    tooltipLabel.setText("Number of documents borrowed: " + originData);
                    customTooltip.show(data.getNode(), event.getScreenX() + 10, event.getScreenY() + 10); // Vị trí Tooltip gần chuột

                    // Hiệu ứng phóng to
                    ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(300), data.getNode());
                    scaleTransition.setToX(1.1);
                    scaleTransition.setToY(1.1);
                    scaleTransition.play();
                });

                // Sự kiện khi chuột di chuyển (cập nhật vị trí Tooltip)
                data.getNode().setOnMouseMoved(event -> {
                    customTooltip.setX(event.getScreenX() + 10); // Cập nhật vị trí X
                    customTooltip.setY(event.getScreenY() + 10); // Cập nhật vị trí Y
                });

                // Sự kiện khi chuột rời khỏi Node
                data.getNode().setOnMouseExited(event -> {
                    customTooltip.hide(); // Ẩn Tooltip
                    ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(300), data.getNode());
                    scaleTransition.setToX(1.0); // Thu nhỏ về kích thước gốc
                    scaleTransition.setToY(1.0);
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
                // Cập nhật popup để hiển thị phần trăm
                Popup customTooltip = new Popup();
                Label tooltipLabel = new Label();
                tooltipLabel.setStyle("-fx-background-color: #68d69d; " +
                        "-fx-text-fill: #401d83;" +
                        "-fx-padding: 5; " +
                        "-fx-border-color: #73ec8b ; " +
                        "-fx-border-width: 1; " +
                        "-fx-border-radius: 10; " +
                        "-fx-background-radius: 10;");
                customTooltip.getContent().add(tooltipLabel);

                // Sự kiện khi chuột vào Node
                data.getNode().setOnMouseEntered(event -> {
                    tooltipLabel.setText("Number of documents: " + originData);
                    customTooltip.show(data.getNode(), event.getScreenX() + 10, event.getScreenY() + 10); // Vị trí Tooltip gần chuột

                    // Hiệu ứng phóng to
                    ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(300), data.getNode());
                    scaleTransition.setToX(1.1);
                    scaleTransition.setToY(1.1);
                    scaleTransition.play();
                });

                // Sự kiện khi chuột di chuyển (cập nhật vị trí Tooltip)
                data.getNode().setOnMouseMoved(event -> {
                    customTooltip.setX(event.getScreenX() + 10); // Cập nhật vị trí X
                    customTooltip.setY(event.getScreenY() + 10); // Cập nhật vị trí Y
                });

                // Sự kiện khi chuột rời khỏi Node
                data.getNode().setOnMouseExited(event -> {
                    customTooltip.hide(); // Ẩn Tooltip
                    ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(300), data.getNode());
                    scaleTransition.setToX(1.0); // Thu nhỏ về kích thước gốc
                    scaleTransition.setToY(1.0);
                    scaleTransition.play();
                });
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
