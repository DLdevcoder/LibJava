package controllers;
import com.mysql.cj.protocol.Resultset;
import models.Book;
import models.Member;
import utils.DatabaseConnection;

import java.sql.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class BookController {
     protected static List<Book> books;

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

     public static void removeBook(Connection connection, int bookId) throws SQLException {
         try(PreparedStatement statement = connection.prepareStatement("DELETE FROM books WHERE book_id = ?")){
             statement.setInt(1, bookId);
             int rows = statement.executeUpdate();
             if(rows > 0){
                 System.out.println("Xóa tài liệu thành công!");
             }
             else {
                 System.out.println("Vui lòng nhập đúng id của tài liệu!");
             }
         }

     }

     public void getBook() {
         String query = "SELECT * FROM books";
         List<Book> books = new ArrayList<>();
         try (Connection connection = DatabaseConnection.getConnection();
              Statement stmt = connection.createStatement();
              ResultSet resultSet = stmt.executeQuery(query);) {
             while (resultSet.next()) {
                 int bookId = resultSet.getInt("id");
                 String title = resultSet.getString("title");
                 String author = resultSet.getString("author");
                 String publisher = resultSet.getString("publisher");
                 int year = resultSet.getInt("publication_year");
                 String isbn = resultSet.getString("isbn");
                 int quantity = resultSet.getInt("quantity");
                 String description = resultSet.getString("description");
                 String thumbnail = resultSet.getString("thumbnail");
                 String language = resultSet.getString("language");
                 Book book = new Book(bookId, title, publisher, year, isbn, quantity, description, thumbnail, language);
                 books.add(book);
             }
             BookController.books = books;
         } catch (SQLException e) {
             e.printStackTrace();
         }
     }

    public void displayDocument(int bookId) {
         for (Book book : books) {
             if (book.getId() == bookId) {
                 System.out.println("Title: " + book.getTitle());
                 System.out.println("Author: " + book.getAuthor());
                 System.out.println("Published: " + book.getPublisher());
                 System.out.println("Year: " + book.getPublicationYear());
                 System.out.println("International Standard Book Number: " + book.getIsbn());
                 System.out.println("Quantity: " + book.getQuantity());
                 System.out.println("Description: " + book.getDescription());
                 System.out.println("Thumbnail: " + book.getThumbnail());
                 System.out.println("Language: " + book.getLanguage());
                 System.out.println();
                 return;
             }
         }
        System.out.println("Book not found!");
    }

    public void displayAllDocument() {
        for (Book book : books) {
                System.out.println("Book ID: " + book.getId());
                System.out.println("Title: " + book.getTitle());
                System.out.println("Published: " + book.getPublisher());
                System.out.println("Year: " + book.getId());
                System.out.println("International Standard Book Number: " + book.getIsbn());
                System.out.println("Quantity: " + book.getQuantity());
                System.out.println("Description: " + book.getDescription());
                System.out.println("Thumbnail: " + book.getThumbnail());
                System.out.println("Language: " + book.getLanguage());
                System.out.println();
        }
    }


}
