package models;
public class Member {
    private int memberId; // id tự tăng nên k cần setter
    private String name;
    private String address;
    private String phone;
    private String email;
    private String membershipDate;
    private String password;

    public Member() {
    }

    public Member(int memberId, String name, String address, String phone, String email, String membershipDate, String password) {
        this.memberId = memberId;
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.email = email;
        this.membershipDate = membershipDate;
        this.password = password;
    }
    public int getMemberId() {
        return memberId;
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getAddress() {
        return this.address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public String getPhone() {
        return this.phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
    public String getEmail() {
        return this.email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getMembershipDate() {
        return this.membershipDate;
    }
    public void setMembershipDate(String membershipDate) {
        this.membershipDate = membershipDate;
    }
    public String getPassword() {
        return this.password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "Member ID: " + this.memberId + "\nName: " + this.name + "\nAddress: " + this.address + "\nPhone: " + this.phone + "\nEmail: " + this.email + "\nMembership Date: " + this.membershipDate;
    }
}
