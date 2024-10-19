package controllers;
import models.Admin;

import utils.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class AdminController {
    // mai lam tiep
    private List<Admin> Admins;

    public AdminController() {
        Admins = new ArrayList<>();
    }
    public void getAdmins() {
        String query = "SELECT * FROM admin";
        List<Admin> Admins = new ArrayList<>();
        // execute query
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                int AdminId = rs.getInt("admin_id");
                String name = rs.getString("name");
                String address = rs.getString("address");
                String phone = rs.getString("phone_number");
                String email = rs.getString("email");
                String password = rs.getString("password");
                String createDate = rs.getString("created_at");
                Admin Admin = new Admin(AdminId, name, address, phone, email, createDate, password);
                Admins.add(Admin);
            }
            this.Admins = Admins;
        } catch (SQLException e) {

            e.printStackTrace();
        }
    }

    public void addAdmin() throws SQLException {
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
            PreparedStatement statement = connection.prepareStatement("INSERT INTO admin (name, address, phone_number, email, password)  VALUES (?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
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

    public void removeAdmin(int AdminId) throws SQLException {
        String query = "DELETE FROM admin WHERE Admin_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, AdminId);
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Admin removed successfully!");
            } else {
                System.out.println("Admin not found!");
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateAdmin(int adminId) {
        String query = "UPDATE admin SET name = ?, address = ?, phone_number = ?, email = ?, password = ? WHERE admin_id = ?";
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
            stmt.setInt(6, adminId);
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Admin updated successfully!");
            } else {
                System.out.println("Admin not found!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void findAdmin() {
    }


    // su dung cho admin
    // xem xet
    public void displayAllAdmin() {
        for (Admin Admin : Admins) {
            System.out.println("Admin ID: " + Admin.getAdminId());
            System.out.println("Name: " + Admin.getName());
            System.out.println("Address: " + Admin.getAddress());
            System.out.println("Phone: " + Admin.getPhone());
            System.out.println("Email: " + Admin.getEmail());
            System.out.println("Create Date: " + Admin.getCreateDate());
            System.out.println("Password: " + Admin.getPassword());
            System.out.println();
        }

    }

    //su dung cho Admin
    public void displayAdminInfo(int adminId) {
        for (Admin Admin : Admins) {
            if (Admin.getAdminId() == adminId) {
                System.out.println("Admin ID: " + Admin.getAdminId());
                System.out.println("Name: " + Admin.getName());
                System.out.println("Address: " + Admin.getAddress());
                System.out.println("Phone: " + Admin.getPhone());
                System.out.println("Email: " + Admin.getEmail());
                System.out.println("Create Date: " + Admin.getCreateDate());
                return;
            }
        }
        System.out.println("Admin not found!");
    }



//
//    public static void main(String[] agrs) throws SQLException {
//        AdminController adminController = new AdminController();
//        //adminController.addAdmin();
//        adminController.updateAdmin(1);
//        adminController.getAdmins();
//        adminController.displayAdminInfo(1);
//
//    }

}