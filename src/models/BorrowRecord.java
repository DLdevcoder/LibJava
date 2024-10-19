package models;
import java.util.Date;
public class BorrowRecord {
    // Các thuộc tính của bảng Borrow_Records
    private int recordId;
    private int bookId;
    private int memberId;
    private Date borrowDate;
    private Date returnDate;
    private Date dueDate;
    private String status;

    // Constructor không tham số
    public BorrowRecord() {

    }

    // Constructor đầy đủ tham số
    public BorrowRecord(int recordId, int bookId, int memberId, Date borrowDate, Date returnDate, Date dueDate, String status) {
        this.recordId = recordId;
        this.bookId = bookId;
        this.memberId = memberId;
        this.borrowDate = borrowDate;
        this.returnDate = returnDate;
        this.dueDate = dueDate;
        this.status = status;
    }

    // Getter và Setter cho từng thuộc tính
    public int getRecordId() {
        return recordId;
    }

    public void setRecordId(int recordId) {
        this.recordId = recordId;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
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

    // Phương thức để hiển thị thông tin của bản ghi mượn
    @Override
    public String toString() {
        return "BorrowRecord{" +
                "recordId=" + recordId +
                ", bookId=" + bookId +
                ", memberId=" + memberId +
                ", borrowDate=" + borrowDate +
                ", returnDate=" + returnDate +
                ", dueDate=" + dueDate +
                ", status='" + status + '\'' +
                '}';
    }

}
