package controllers;
import models.Admin;
import models.Member;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class AdminController {
    private Admin admin;

    public AdminController() {
        this.admin = new Admin();
    }
    public AdminController(Admin admin) {
        this.admin = admin;
    }

    public List<Admin> getAdmins() {
        return this.admin.getAdmins();
    }

    public void addAdmin() throws SQLException {
        this.admin.addAdmin();
    }

    public void removeAdmin(int AdminId) throws SQLException {
        this.admin.removeAdmin(AdminId);
    }

    public void updateAdmin(int adminId) {
        this.admin.updateAdmin(adminId);
    }

    public void findAdmin() {
    }

    public void displayAllAdmin() {
        this.admin.displayAllAdmin();

    }

    public void displayAdminInfo() {
        this.admin.displayAdminInfo();
    }

    //member
    public List<Member> getMembers() {
        return this.admin.getMembers();
    }
    public void displayAllMember() {
        this.admin.displayAllMember();
    }
    public void addMember () throws SQLException {
        this.admin.addMember();
    }

    public void removeMember(int memberId) throws SQLException {
        this.admin.removeMember(memberId);
    }

    public void updateMember(int memberId) {
        this.admin.updateMember(memberId);
    }



//    public static void main(String[] agrs) throws SQLException {
//        Admin admin = new Admin( 1,"name", "address", "phone", "email", "membershipDate", "password");
//        AdminController adminController = new AdminController(admin);
//        adminController.displayAdminInfo();
//        //adminController.addAdmin();
//        //adminController.updateAdmin(1);
//        //adminController.getAdmins();
//
//
//
//    }

}