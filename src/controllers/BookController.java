package controllers;
import com.mysql.cj.protocol.Resultset;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableView;
import models.Book;
import models.Member;
import utils.DatabaseConnection;

import java.net.URL;
import java.sql.*;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Scanner;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class BookController extends HeaderController implements Initializable {

     protected static List<Book> books;
     @FXML
     private TableView<Book> Doccument_Table;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}





