
package controllers.borrow_record;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import models.BorrowRecord;
import utils.DatabaseConnection;
import java.sql.*;
import java.time.LocalDate;

public class BorrowRecordController extends SidebarController {
    @FXML
    private TableView<BorrowRecord> tableView;

    @FXML
    private TableColumn<BorrowRecord, Integer> recordIdColumn;
    @FXML
    private TableColumn<BorrowRecord, Integer> documentIdColumn;
    @FXML
    private TableColumn<BorrowRecord, Integer> memberIdColumn;
    @FXML
    private TableColumn<BorrowRecord, Date> borrowDateColumn;
    @FXML
    private TableColumn<BorrowRecord, Date> returnDateColumn;
    @FXML
    private TableColumn<BorrowRecord, Date> dueDateColumn;
    @FXML
    private TableColumn<BorrowRecord, String> statusColumn;
    @FXML
    private TableColumn<BorrowRecord, Integer> quantityColumn;
    @FXML
    private TextField recordIdSearch;
    @FXML
    private TextField docIdSearch;
    @FXML
    private TextField memIdSearch;
    @FXML
    private DatePicker borrowDateSearch;
    @FXML
    private DatePicker dueDateSearch;
    @FXML
    private DatePicker returnDateSearch;
    @FXML
    private TextField statusSearch;
    @FXML
    private TextField quantitySearch;

    private ObservableList<BorrowRecord> borrowRecordList = FXCollections.observableArrayList();
    private FilteredList<BorrowRecord> filteredRecords;

    @FXML
    public void initialize() throws SQLException {
        recordIdColumn.setCellValueFactory(new PropertyValueFactory<>("recordId"));
        documentIdColumn.setCellValueFactory(new PropertyValueFactory<>("documentId"));
        memberIdColumn.setCellValueFactory(new PropertyValueFactory<>("memberId"));
        borrowDateColumn.setCellValueFactory(new PropertyValueFactory<>("borrowDate"));
        returnDateColumn.setCellValueFactory(new PropertyValueFactory<>("returnDate"));
        dueDateColumn.setCellValueFactory(new PropertyValueFactory<>("dueDate"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
        quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        loadBorrowRecords();

        filteredRecords = new FilteredList<>(borrowRecordList, b -> true);

        // Tạo SortedList dựa trên FilteredList
        SortedList<BorrowRecord> sortedRecords = new SortedList<>(filteredRecords);

        // Liên kết sắp xếp của SortedList với TableView
        sortedRecords.comparatorProperty().bind(tableView.comparatorProperty());

        // Đặt SortedList làm nguồn dữ liệu cho TableView
        tableView.setItems(sortedRecords);
    }

    private void loadBorrowRecords() throws SQLException {
        String query = "SELECT * FROM Borrow_Records";
        Connection conn = DatabaseConnection.getConnection();
        PreparedStatement stmt = conn.prepareStatement(query);
        ResultSet rs = stmt.executeQuery();
        try {
            borrowRecordList.clear();
            while (rs.next()) {
                BorrowRecord record = new BorrowRecord(
                        rs.getInt("record_id"),
                        rs.getInt("document_id"),
                        rs.getInt("member_id"),
                        rs.getDate("borrow_date"),
                        rs.getDate("return_date"),
                        rs.getDate("due_date"),
                        rs.getString("status"),
                        rs.getInt("quantity")
                );
                borrowRecordList.add(record);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    private void search() {
        filteredRecords.setPredicate(borrowRecord -> {
            // Kiểm tra từng trường. Nếu trường tìm kiếm trống, bỏ qua nó.
            if (!recordIdSearch.getText().isEmpty()) {
                String searchText = recordIdSearch.getText();
                if (!String.valueOf(borrowRecord.getRecordId()).contains(searchText)) {
                    return false;
                }
            }

            if (!docIdSearch.getText().isEmpty()) {
                String searchText = docIdSearch.getText();
                if (!String.valueOf(borrowRecord.getDocumentId()).contains(searchText)) {
                    return false;
                }
            }

            if (!memIdSearch.getText().isEmpty()) {
                String searchText = memIdSearch.getText();
                if (!String.valueOf(borrowRecord.getMemberId()).contains(searchText)) {
                    return false;
                }
            }

            if (borrowDateSearch.getValue() != null) {
                Date searchDate = Date.valueOf(borrowDateSearch.getValue());
                if (!borrowRecord.getBorrowDate().equals(searchDate)) {
                    return false;
                }
            }

            if (returnDateSearch.getValue() != null) {
                Date searchDate = Date.valueOf(returnDateSearch.getValue());
                if (borrowRecord.getReturnDate() == null || !borrowRecord.getReturnDate().equals(searchDate)) {
                    return false;
                }
            }

            if (dueDateSearch.getValue() != null) {
                LocalDate searchDate = dueDateSearch.getValue();
                if (!borrowRecord.getDueDate().equals(searchDate)) {
                    return false;
                }
            }

            if (!statusSearch.getText().isEmpty()) {
                String searchText = statusSearch.getText().toLowerCase();
                if (!borrowRecord.getStatus().toLowerCase().contains(searchText)) {
                    return false;
                }
            }

            if (!quantitySearch.getText().isEmpty()) {
                String searchText = quantitySearch.getText();
                if (!String.valueOf(borrowRecord.getQuantity()).contains(searchText)) {
                    return false;
                }
            }

            // Nếu tất cả kiểm tra đều thỏa mãn, trả về true (hiển thị bản ghi)
            return true;
        });
    }

    @FXML
    private void clearField() throws SQLException {
        recordIdSearch.setText("");
        docIdSearch.setText("");
        memIdSearch.setText("");
        borrowDateSearch.setValue(null);
        dueDateSearch.setValue(null);
        returnDateSearch.setValue(null);
        statusSearch.setText("");
        quantitySearch.setText("");
        loadBorrowRecords();
    }

}
