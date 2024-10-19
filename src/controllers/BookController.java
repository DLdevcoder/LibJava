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

         System.out.println("Enter title: ");
         String title = sc.nextLine();

         System.out.println("Enter publisher: ");
         String publisher = sc.nextLine();

         System.out.println("Enter publication year: ");
         int year = sc.nextInt();
         sc.nextLine();

         System.out.println("Enter isbn: ");
         String isbn = sc.nextLine();

         System.out.println("Enter quantity: ");
         int quantity = sc.nextInt();
         sc.nextLine();

         System.out.println("Enter description: ");
         String description = sc.nextLine();

         System.out.println("Enter thumnail: ");
         String thumbnail = sc.nextLine();

         System.out.println("Language used: ");
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
                 System.out.println("Book added successfully!");

             }

             ResultSet resultSet = statement.getGeneratedKeys();
             if(resultSet.next()){
                 int bookId = resultSet.getInt(1);
                 System.out.println("BookID: "+bookId);

             }
         }


     }

     public static void removeBook(Connection connection, int bookId) throws SQLException {
         try(PreparedStatement statement = connection.prepareStatement("DELETE FROM books WHERE book_id = ?")){
             statement.setInt(1, bookId);
             int rows = statement.executeUpdate();
             if(rows > 0){
                 System.out.println("Delete Successfully!");
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
                 int bookId = resultSet.getInt("book_id");
                 String title = resultSet.getString("title");
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
             if (book.getBookId() == bookId) {
                 System.out.println("Title: " + book.getTitle());
                 System.out.println("Published: " + book.getPublisher());
                 System.out.println("Year: " + book.getYear());
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

    public void displayAllDocument(int bookId) {
        for (Book book : books) {
            if (book.getBookId() == bookId) {
                System.out.println("Book ID: " + book.getBookId());
                System.out.println("Title: " + book.getTitle());
                System.out.println("Published: " + book.getPublisher());
                System.out.println("Year: " + book.getYear());
                System.out.println("International Standard Book Number: " + book.getIsbn());
                System.out.println("Quantity: " + book.getQuantity());
                System.out.println("Description: " + book.getDescription());
                System.out.println("Thumbnail: " + book.getThumbnail());
                System.out.println("Language: " + book.getLanguage());
                System.out.println();
            }
        }
    }


}
