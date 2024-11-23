module main {
    requires org.json;
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;
    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires com.almasb.fxgl.all;
    requires java.sql;
    requires mysql.connector.j;
    requires jdk.httpserver;
    requires okhttp3;
    opens controllers to javafx.fxml;
    exports main;
    opens models to javafx.base;
    opens main to javafx.fxml;
    opens controllers.member to javafx.fxml;
    opens controllers.BorrowRecord to javafx.fxml;
    opens controllers.Document to javafx.fxml;
    opens controllers.admin to javafx.fxml;
    opens controllers.Home to javafx.fxml;
}