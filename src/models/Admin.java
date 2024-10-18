package models;

public class Admin extends Person {
    private int adminId; // id tự tăng nên k cần setter
    private String createDate; // tu dong lay ngay hien tai nen k can setter

    public Admin() {
    }

    public Admin(int adminId, String name, String address, String phone, String email, String createDate, String password) {
        super(name, address, phone, email, password);
        this.adminId = adminId;
        this.createDate = createDate;

    }

    public int getAdminId() {
        return adminId;
    }

    public String getCreateDate() {
        return this.createDate;
    }
}