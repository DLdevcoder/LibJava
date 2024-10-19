package controllers;
import com.mysql.cj.protocol.Resultset;
import models.Member;
import utils.DatabaseConnection;

import java.sql.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MemberController {
    private List<Member> members;

    public MemberController() {
        members = new ArrayList<>();
    }
    public void getMembers() {
        String query = "SELECT * FROM members";
        List<Member> members = new ArrayList<>();
        // execute query
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query);) {
            while (rs.next()) {
                int memberId = rs.getInt("member_id");
                String name = rs.getString("name");
                String address = rs.getString("address");
                String phone = rs.getString("phone_number");
                String email = rs.getString("email");
                String password = rs.getString("password");
                String membershipDate = rs.getString("membership_date");
                Member member = new Member(memberId, name, address, phone, email, membershipDate, password);
                members.add(member);
            }
            this.members = members;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void addMember() throws SQLException {
        Scanner sc = new Scanner(System.in);
        System.out.println("Nhập tên thành viên: ");
        String name = sc.nextLine();

        System.out.println("Nhập địa chỉ: ");
        String address = sc.nextLine();

        System.out.println("Nhập số liên lạc: ");
        String phone = sc.nextLine();

        System.out.println("Nhập email: ");
        String email = sc.nextLine();

        System.out.println("Nhập ngày đăng kí thành viên: ");
        String membershipDate = sc.nextLine();

        System.out.println("Nhập mật khẩu: ");
        String password = sc.nextLine();

        Member member = new Member(name, address, phone, email, membershipDate, password);

        try(Connection connection = DatabaseConnection.getConnection()){
            PreparedStatement statement = connection.prepareStatement("INSERT INTO members (name, address, phone_number, email, membership_date, password)  VALUES (?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, name);
            statement.setString(2, address);
            statement.setString(3, phone);
            statement.setString(4, email);
            statement.setString(5, membershipDate);
            statement.setString(6, password);
            int rowsAffected = statement.executeUpdate();
            if(rowsAffected>0){
                System.out.println("Thành viên mới đã được thêm thành công!.");

            }

            ResultSet resultSet = statement.getGeneratedKeys();
            if(resultSet.next()){
                int id = resultSet.getInt(1);
                System.out.println("ID của thành viên mới: " + id);


            }




        } catch (SQLException e) {
            e.printStackTrace();
        }





    }

    public void removeMember() {

    }

    public void updateMember() {
    }

    public void findMember() {
    }


    // su dung cho admin
    public void displayAllMember() {
        for (Member member : members) {
            System.out.println("Member ID: " + member.getMemberId());
            System.out.println("Name: " + member.getName());
            System.out.println("Address: " + member.getAddress());
            System.out.println("Phone: " + member.getPhone());
            System.out.println("Email: " + member.getEmail());
            System.out.println("Membership Date: " + member.getMembershipDate());
            System.out.println("Password: " + member.getPassword());
            System.out.println();
        }

    }

    //su dung cho member
    public void displayMemberInfo(int memberId) {
        for (Member member : members) {
            if (member.getMemberId() == memberId) {
                System.out.println("Member ID: " + member.getMemberId());
                System.out.println("Name: " + member.getName());
                System.out.println("Address: " + member.getAddress());
                System.out.println("Phone: " + member.getPhone());
                System.out.println("Email: " + member.getEmail());
                System.out.println("Membership Date: " + member.getMembershipDate());
                return;
            }
        }
        System.out.println("Member not found!");
    }


}