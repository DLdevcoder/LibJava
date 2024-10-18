package controllers;
import com.mysql.cj.protocol.Resultset;
import models.Member;
import utils.DatabaseConnection;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class MemberController {
    private List<Member> members;

    public MemberController() {
        members = new ArrayList<>();
    }
    public void getMembers() {
        String query = "SELECT * FROM members";
        List<Member> members = new ArrayList<>();
        // execute query
        try(Connection conn = DatabaseConnection.getConnection();
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
            System.out.println("Error fetching members: " + e.getMessage());
        }
        //return this.members;
    }
    public void addMember() {
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
