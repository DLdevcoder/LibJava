package utils;

import models.BorrowRecord;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class BorrowRecordDAO {

    public List<BorrowRecord> getAllRecords() {
        List<BorrowRecord> records = new ArrayList<>();

        String query = "SELECT * FROM Borrow_Records";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                BorrowRecord record = new BorrowRecord(
                        rs.getInt("record_id"),
                        rs.getInt("document_id"),
                        rs.getInt("member_id"),
                        rs.getDate("borrow_date"),
                        rs.getDate("return_date"),
                        rs.getDate("due_date"),
                        rs.getString("status"),
                        rs.getInt("quantity"),
                        rs.getInt("quantityBorrow")
                );
                records.add(record);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return records;
    }
}
