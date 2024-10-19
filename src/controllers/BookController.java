package controllers;
import com.mysql.cj.protocol.Resultset;
import models.Book;
import utils.DatabaseConnection;

import java.sql.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class BookController {
     private List<Book> books;

     public BookController() {
         this.books = new ArrayList<>();
     }

     public static void addBook() throws SQLException {
         Scanner sc = new Scanner(System.in);

         System.out.println("Nhập tiêu đề của tài liệu: ");
         String title = sc.nextLine();

         System.out.println("Nhập tên nhà xuất bản: ");
         String publisher = sc.nextLine();

         System.out.println("Nhập năm phát hành: ");
         int year = sc.nextInt();
         sc.nextLine();

         System.out.println("Nhập isbn: ");
         String isbn = sc.nextLine();

         System.out.println("Nhập số lượng định thêm: ");
         int quantity = sc.nextInt();
         sc.nextLine();

         System.out.println("Nhập miêu tả: ");
         String description = sc.nextLine();

         System.out.println("Nhập thumnail: ");
         String thumbnail = sc.nextLine();

         System.out.println("Tài liệu sử dụng ngôn ngữ: ");
         String language = sc.nextLine();

         Book book = new Book(title, publisher, year, isbn, quantity, description, thumbnail, language);

         try(Connection connection = DatabaseConnection.getConnection()){
             PreparedStatement statement = connection.prepareStatement("INSERT INTO books (title, publisher, publication_year, isbn, quantity, description, thumbnail, language) VALUES (?, ?, ?, ?, ?, ?, ?, ?)",Statement.RETURN_GENERATED_KEYS);
             statement.setString(1, title);
             statement.setString(2, publisher);
             statement.setInt(3, year);
             statement.setString(4, isbn);
             statement.setInt(5, quantity);
             statement.setString(6, description);
             statement.setString(7, thumbnail);
             statement.setString(8, language);

             int rows = statement.executeUpdate();
             if(rows > 0){
                 System.out.println("Tài liệu đã được thêm thành công!");

             }

             ResultSet resultSet = statement.getGeneratedKeys();
             if(resultSet.next()){
                 int bookId = resultSet.getInt(1);
                 System.out.println("ID cua tài liệu mới: "+bookId);

             }







         }









     }


}
