package controllers.member;

import controllers.HeaderController;
import javafx.event.ActionEvent;

public class SidebarMemberController extends HeaderController {
    public static final String MEMBER_LIST = "/views/members/MemberList.fxml";
    public static final String ADD_MEMBER = "/views/members/AddMember.fxml";
    public static final String EDIT_MEMBER = "/views/members/EditMember.fxml";
    public static final String FIND_MEMBER = "/views/members/FindMember.fxml";
    public static final String STATISTIC_MEMBER = "/views/members/StatisticMember.fxml";

    public void sceneAddMember(ActionEvent event) {
        changeScene(event, ADD_MEMBER);
    }

    public void sceneEditMember(ActionEvent event) {
        changeScene(event, EDIT_MEMBER);
    }

    public void sceneFindMember(ActionEvent event) {
        changeScene(event, FIND_MEMBER);
    }
    public void scenceStatisticMember(ActionEvent event) {
        changeScene(event, STATISTIC_MEMBER);
    }
}
