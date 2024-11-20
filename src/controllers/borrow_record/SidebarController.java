package controllers.borrow_record;

import controllers.HeaderController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.Objects;

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
