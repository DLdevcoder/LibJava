package controllers.admin;

import controllers.HeaderController;
import javafx.event.ActionEvent;

public class SidebarAdmin extends HeaderController {
    public static final String ADMIN_DASHBOARD = "/views/admin/DashboardAdmin.fxml";
    public static final String ADMIN_INFO = "/views/admin/AdminInfo.fxml";
    public static final String CHANGE_PASSWORD = "/views/admin/ChangePassword.fxml";

    public void sceneAdminProfile(ActionEvent event) {
        changeScene(event, ADMIN_INFO);
    }

    public void sceneChangePassword(ActionEvent event) {
        changeScene(event, CHANGE_PASSWORD);
    }
}
