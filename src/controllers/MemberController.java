package controllers;
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

    public void displayMember() {
    }

    public void displayUserInfo() {
    }
}
