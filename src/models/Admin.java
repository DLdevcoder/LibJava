package models;

import javafx.scene.control.Alert;
import utils.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Admin extends Person {
    private int adminId; // id tự tăng nên k cần setter
    private String createDate;
    // tu dong lay ngay hien tai nen k can setter
    private static Admin instance;


    public Admin() {
    }
    public static Admin getInstance() {
        if (instance == null) {
            instance = new Admin();
        }
        return instance;
    }
    public Admin(String email, String password) {
        this.email = email;
        this.password = password;
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

    /**
     * Lấy thông tin admin từ database
     * @param email email
     * @param password mật khẩu
     * @return admin
     */
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

    /**
     * Đổi mật khẩu của admin
     * @param adminId id của admin
     * @param newPassword mật khẩu mới
     */
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

    /**
     * Lấy danh sách các thành viên từ database
     * @return danh sách thành viên
     */
    public List<Member> getMembers() {
        String query = "SELECT * FROM members";
        List<Member> members = new ArrayList<>();
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
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return members;
    }

    /**
     * Thêm một thành viên mới vào database
     * @param name tên
     * @param address địa chỉ
     * @param phone số điện thoại
     * @param email email
     * @param password mật khẩu
     */
    public void addMember(String name, String address, String phone, String email, String password) {
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

    /**
     * Xóa một thành viên khỏi database
     * @param memberId id của thành viên
     */
    public void removeMember(int memberId) {
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

    /**
     * Cập nhật thông tin của một thành viên
     * @param memberId id của thành viên
     * @param name tên
     * @param address địa chỉ
     * @param phone số điện thoại
     * @param email email
     * @param password mật khẩu
     */
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

    /**
     * Tìm kiếm một thành viên theo id
     * @param id id của thành viên
     * @return thành viên
     */
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

    /**
     * Tìm kiếm thành viên theo tên, địa chỉ, số điện thoại, email, ngày tham gia
     * @param name tên
     * @param address địa chỉ
     * @param phone số điện thoại
     * @param email email
     * @param membershipDate ngày tham gia
     * @return danh sách thành viên
     */
    public List<Member> findMemberwithFilter(String name, String address, String phone, String email, String membershipDate) {
        List<Member> members = new ArrayList<>();
        String query = "SELECT * FROM members WHERE " +
                "(name LIKE ? OR ? IS NULL) AND " +
                "(address LIKE ? OR ? IS NULL) AND " +
                "(phone_number LIKE ? OR ? IS NULL) AND " +
                "(email LIKE ? OR ? IS NULL) AND " +
                "(membership_date > ? OR ? IS NULL)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            // Set các giá trị cho tham số
            stmt.setString(1, name != null && !name.isEmpty() ? "%" + name + "%" : null);
            stmt.setString(2, name != null && !name.isEmpty() ? "%" + name + "%" : null);

            stmt.setString(3, address != null && !address.isEmpty() ? "%" + address + "%" : null);
            stmt.setString(4, address != null && !address.isEmpty() ? "%" + address + "%" : null);

            stmt.setString(5, phone != null && !phone.isEmpty() ? "%" + phone + "%" : null);
            stmt.setString(6, phone != null && !phone.isEmpty() ? "%" + phone + "%" : null);

            stmt.setString(7, email != null && !email.isEmpty() ? "%" + email + "%" : null);
            stmt.setString(8, email != null && !email.isEmpty() ? "%" + email + "%" : null);

            stmt.setString(9, membershipDate != null && !membershipDate.isEmpty() ? membershipDate : null);
            stmt.setString(10, membershipDate != null && !membershipDate.isEmpty() ? membershipDate : null);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int memberId = rs.getInt("member_id");
                String memberName = rs.getString("name");
                String memberAddress = rs.getString("address");
                String memberPhone = rs.getString("phone_number");
                String memberEmail = rs.getString("email");
                String memberPassword = rs.getString("password");
                String memberMembershipDate = rs.getString("membership_date");
                Member member = new Member(memberId, memberName, memberAddress, memberPhone, memberEmail, memberMembershipDate, memberPassword);
                members.add(member);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return members;
    }
    // login

    /**
     * Kiểm tra thông tin đăng nhập
     * @param email email
     * @param password mật khẩu
     * @return true nếu thông tin đúng, ngược lại false
     */
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

    public void saveBookToDatabase(Book book) {
        String sql = "INSERT INTO books (title, author, publication_year, publisher, language, preview_link, quantity) VALUES (?, ?, ?, ?, ?, ?,?)";

        try( Connection connection = DatabaseConnection.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql) ){
            preparedStatement.setString(1, book.getTitle());
            preparedStatement.setString(2, book.getAuthor());
            preparedStatement.setString(3, book.getPublicationYear());
            preparedStatement.setString(4, book.getPublisher());
            preparedStatement.setString(5, book.getLanguage());
            preparedStatement.setString(6, book.getImageLink().getImage().getUrl());
            preparedStatement.setInt(7,book.getQuantity());

            preparedStatement.executeUpdate();

        }catch (SQLException e){
            e.printStackTrace();
        }

    }

    public void deleteBook(String id) {
        String sql = "DELETE FROM books WHERE id = ?";

        try (Connection connection = DatabaseConnection.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, id);
            int affectRows = preparedStatement.executeUpdate();
            if (affectRows > 0) {
                showAlert("Success", "Book deleted successfully!");
            } else {
                showAlert("Error", "Book could not be deleted. Please check the ID.");
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }


    }
    //theses
    public void saveThesesToDataBase(Theses theses) {
        String sql = "INSERT INTO theses (title, author,quantity, degree, publication_year, institution) VALUES (?, ?, ?, ?, ?, ?)";
        try( Connection connection = DatabaseConnection.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql) ){
            preparedStatement.setString(1, theses.getTitle());
            preparedStatement.setString(2, theses.getAuthor());
            preparedStatement.setInt(3, theses.getQuantity());
            preparedStatement.setString(4, theses.getDegree());
            preparedStatement.setString(5, theses.getPublicationYear());
            preparedStatement.setString(6, theses.getInstitution());


           preparedStatement.executeUpdate();


        } catch(SQLException e){
            e.printStackTrace();
        }
    }

    public void saveGDToDatabase(GovernmentDocuments governmentDocuments) {
        String sql = "INSERT INTO governmentdocuments (title, author, quantity, document_type, publication_year,description) VALUES (?, ?, ?, ?, ?, ?)";
        try( Connection connection = DatabaseConnection.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql) ){
            preparedStatement.setString(1, governmentDocuments.getTitle());
            preparedStatement.setString(2, governmentDocuments.getAuthor());
            preparedStatement.setInt(3, governmentDocuments.getQuantity());
            preparedStatement.setString(4, governmentDocuments.getDocumentType());
            preparedStatement.setString(5, governmentDocuments.getPublicationYear());
            preparedStatement.setString(6, governmentDocuments.getDescriptions());
            preparedStatement.executeUpdate();




    } catch(SQLException e){
            e.printStackTrace();
        }
    }
    private static void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION); // Use ERROR or WARNING type for other scenarios
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

}