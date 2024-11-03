package controllers.borrow_record.controllers;

import models.Admin;
import models.Member;

import java.sql.SQLException;
import java.util.List;


public class AdminController extends HeaderController {
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


    public void removeAdmin(int AdminId) throws SQLException {
        this.admin.removeAdmin(AdminId);
    }

    public void updateAdmin(int adminId) {
        this.admin.updateAdmin(adminId);
    }

    public void findAdmin() {
    }

    public void displayAdminInfo() {
        this.admin.displayAdminInfo();
    }

    //member
    public List<Member> getMembers() {
        return this.admin.getMembers();
    }


}