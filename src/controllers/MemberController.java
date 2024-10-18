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
            e.printStackTrace();
        }
    }
    public void addMember() throws SQLException {
        Scanner sc = new Scanner(System.in);
        System.out.print("Nhập tên thành viên: ");
        String name = sc.nextLine();

        System.out.print("Nhập địa chỉ: ");
        String address = sc.nextLine();

        System.out.print("Nhập số liên lạc: ");
        String phone = sc.nextLine();

        System.out.print("Nhập email: ");
        String email = sc.nextLine();

        System.out.print("Nhập mật khẩu: ");
        String password = sc.nextLine();

        try(Connection connection = DatabaseConnection.getConnection()){
            PreparedStatement statement = connection.prepareStatement("INSERT INTO members (name, address, phone_number, email, password)  VALUES (?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, name);
            statement.setString(2, address);
            statement.setString(3, phone);
            statement.setString(4, email);
            statement.setString(5, password);
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

    public void removeMember(int memberId) throws SQLException {
        String query = "DELETE FROM members WHERE member_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, memberId);
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Member removed successfully!");
            } else {
                System.out.println("Member not found!");
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateMember(int memberId) {
        String query = "UPDATE members SET name = ?, address = ?, phone_number = ?, email = ?, password = ? WHERE member_id = ?";
        Scanner sc = new Scanner(System.in);
        System.out.print("Nhập tên thành viên: ");
        String name = sc.nextLine();

        System.out.print("Nhập địa chỉ: ");
        String address = sc.nextLine();

        System.out.print("Nhập số liên lạc: ");
        String phone = sc.nextLine();

        System.out.print("Nhập email: ");
        String email = sc.nextLine();

        System.out.print("Nhập mật khẩu: ");
        String password = sc.nextLine();
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, name);
            stmt.setString(2, address);
            stmt.setString(3, phone);
            stmt.setString(4, email);
            stmt.setString(5, password);
            stmt.setInt(6, memberId);
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Member updated successfully!");
            } else {
                System.out.println("Member not found!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
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
