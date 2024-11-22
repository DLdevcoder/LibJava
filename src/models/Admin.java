package models;

import utils.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

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
    // admin
    public List<Admin> getAdmins() {
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
            //this.Admins = Admins;
        } catch (SQLException e) {

            e.printStackTrace();
        }
        return Admins;
    }
    public void addAdmin(String name, String address, String phone, String email, String password) throws SQLException {

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

    public static Admin getAdminByLogin(String email, String password) {
        String query = "SELECT * FROM admin WHERE email = ? AND password = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, email);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                int adminId = rs.getInt("admin_id");
                String name = rs.getString("name");
                String address = rs.getString("address");
                String phone = rs.getString("phone_number");
                String createDate = rs.getString("created_at");
                return new Admin(adminId, name, address, phone, email, createDate, password);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void changePassword(int adminId, String newPassword) {
        String query = "UPDATE admin SET password = ? WHERE admin_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, newPassword);
            stmt.setInt(2, adminId);
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Password changed successfully!");
            } else {
                System.out.println("Admin not found!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    // member
    public List<Member> getMembers() {
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
            //this.members = members;
        } catch (SQLException e) {

            e.printStackTrace();
        }
        return members;
    }

    public void addMember(String name, String address, String phone, String email, String password) throws SQLException {
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

    public void updateMember(int memberId, String name, String address, String phone, String email, String password) {
        String query = "UPDATE members SET name = ?, address = ?, phone_number = ?, email = ?, password = ? WHERE member_id = ?";

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

    public Member findMember(int id) {
        String query = "SELECT * FROM members WHERE member_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                String name = rs.getString("name");
                String address = rs.getString("address");
                String phone = rs.getString("phone_number");
                String email = rs.getString("email");
                String password = rs.getString("password");
                String membershipDate = rs.getString("membership_date");
                return new Member(id, name, address, phone, email, membershipDate, password);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Member> findMemberbyName(String nameToFind) {
        String query = "SELECT * FROM members WHERE name LIKE ?";
        List<Member> members = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, "%" + nameToFind + "%");
            ResultSet rs = stmt.executeQuery();
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
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return members;
    }
    // login
    public boolean checkLogin(String email, String password) {
        String query = "SELECT * FROM admin WHERE email = ? AND password = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, email);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();

            return rs.next();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    // book

}