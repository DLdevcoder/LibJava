package models;
import java.util.Date;
import java.time.LocalDate;
public class BorrowRecord {
    // Các thuộc tính của bảng Borrow_Records
    private int recordId;
    private int documentId;
    private int memberId;
    private Date borrowDate;
    private Date returnDate;
    private Date dueDate;
    private String status;
    private int quantity;
    private int quantityBorrow;

    /**
     * Constructor không tham số
     */
    public BorrowRecord() {

    }

    /**
     * Constructor đầy đủ tham số
     * @param recordId
     * @param documentId
     * @param memberId
     * @param borrowDate
     * @param returnDate
     * @param dueDate
     * @param status
     * @param quantity
     * @param quantityBorrow
     */
    public BorrowRecord(int recordId, int documentId,
                        int memberId, Date borrowDate, Date returnDate,
                        Date dueDate, String status, int quantity, int quantityBorrow) {
        this.recordId = recordId;
        this.documentId = documentId;
        this.memberId = memberId;
        this.borrowDate = borrowDate;
        this.returnDate = returnDate;
        this.dueDate = dueDate;
        this.status = status;
        this.quantity = quantity;
        this.quantityBorrow = quantityBorrow;
    }

    // Getter và Setter cho từng thuộc tính
    public int getRecordId() {
        return recordId;
    }

    public void setRecordId(int recordId) {
        this.recordId = recordId;
    }

    public int getDocumentId() {
        return documentId;
    }

    public void setDocumentId(int documentId) {
        this.documentId = documentId;
    }

    public int getMemberId() {
        return memberId;
    }

    public void setMemberId(int memberId) {
        this.memberId = memberId;
    }

    public Date getBorrowDate() {
        return borrowDate;
    }

    public void setBorrowDate(Date borrowDate) {
        this.borrowDate = borrowDate;
    }

    public Date getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    // Phương thức để hiển thị thông tin của bản ghi mượn
    @Override
    public String toString() {
        return "BorrowRecord{" +
                "recordId=" + recordId +
                ", documentId=" + documentId +
                ", memberId=" + memberId +
                ", borrowDate=" + borrowDate +
                ", returnDate=" + returnDate +
                ", dueDate=" + dueDate +
                ", status='" + status +
                ", quantity='" + quantity + '\'' +
                '}';
    }

    public int getQuantityBorrow() {
        return quantityBorrow;
    }

    public void setQuantityBorrow(int quantityBorrow) {
        this.quantityBorrow = quantityBorrow;
    }
}
