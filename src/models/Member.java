package models;
public class Member extends Person {
    private int memberId; // id tự tăng nên k cần setter
    private String membershipDate; // tu dong lay ngay hien tai nen k can setter

    public Member() {
    }

    public Member( int memberId,String name, String address, String phone, String email, String membershipDate, String password) {
        super(name, address, phone, email, password);
        this.memberId = memberId;
        this.membershipDate = membershipDate;

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
    public String getMembershipDate() {
        return this.membershipDate;
    }


    @Override
    public String toString() {
        return "Member ID: " + this.memberId + "\nName: " + this.name + "\nAddress: " + this.address + "\nPhone: " + this.phone + "\nEmail: " + this.email + "\nMembership Date: " + this.membershipDate;
    }
}
