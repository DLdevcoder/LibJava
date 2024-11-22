package models;

import utils.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

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

    public Member(String name, String email, String phone, String address, String password) {
        super(name, email, phone, address, password);
    }

    public Member(int id, String name) {
        this.memberId = id;
        this.name = name;
    }

    public Member(int i) {
        this.memberId = i;
    }

    public Member(String name) {
        this.name = name;

    }


    public void setMemberId(int memberId) {
        this.memberId = memberId;
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

    public void updateInfo(){
        return;
    }
    public void displayMemberInfo() {
        System.out.println("Member ID: " + this.memberId);
        System.out.println("Name: " + this.name);
        System.out.println("Address: " + this.address);
        System.out.println("Phone: " + this.phone);
        System.out.println("Email: " + this.email);
        System.out.println("Membership Date: " + this.membershipDate);
    }

    public void saveReviewToDatabase(Review review) {
        String sql = "INSERT INTO reviews(book_id, review_date, comment, rating, member_id) VALUES(?,?,?,?,?)";
        DatabaseConnection databaseConnection = new DatabaseConnection();

        try(Connection connection = databaseConnection.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, review.getBook().getId()); // book_id
            preparedStatement.setDate(2, review.getReviewDate()); // review_date (java.sql.Date)
            preparedStatement.setString(3, review.getReviewText()); // comment
            preparedStatement.setDouble(4, review.getRating()); // rating
            preparedStatement.setInt(5, review.getMember().getMemberId()); // member_id


            preparedStatement.executeUpdate();

        } catch(SQLException e) {
            e.printStackTrace();
        }

    }
    }
