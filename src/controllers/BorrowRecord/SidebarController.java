package controllers.BorrowRecord;

import controllers.HeaderController;
import javafx.event.ActionEvent;

public class SidebarController extends HeaderController {
    public void sceneDataStatistics(ActionEvent event) {
        changeScene(event, "/views/borrow_records/DataStatistics.fxml");
    }

    public void sceneBorrowRecordList(ActionEvent event) {
        changeScene(event, "/views/borrow_records/BorrowRecordList.fxml");
    }

    public void sceneReturn(ActionEvent event) {
        changeScene(event, "/views/borrow_records/Return.fxml");
    }
}
