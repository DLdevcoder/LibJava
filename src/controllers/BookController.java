package controllers;
import com.mysql.cj.protocol.Resultset;
import models.Book;
import models.Member;
import utils.DatabaseConnection;

import java.sql.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

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

    public void updateDocument(int documentId) {
        for (Book book : books) {
            if (book.getBookId() == documentId) {
                Scanner scanner = new Scanner(System.in);
                boolean running = true;

                while (running) {
                    System.out.println("Updating book with ID: " + documentId);
                    System.out.println("[1] Update Title");
                    System.out.println("[2] Update Publisher");
                    System.out.println("[3] Update Publication Year");
                    System.out.println("[4] Update ISBN");
                    System.out.println("[5] Update Quantity");
                    System.out.println("[6] Update Description");
                    System.out.println("[7] Update Thumbnail");
                    System.out.println("[8] Update Language");
                    System.out.println("[0] Exit Update Menu");
                    System.out.print("Please select an option: ");

                    int choice = scanner.nextInt();
                    scanner.nextLine(); // consume the newline

                    switch (choice) {
                        case 1:
                            System.out.print("Enter new title: ");
                            String newTitle = scanner.nextLine();
                            book.setTitle(newTitle);
                            break;
                        case 2:
                            System.out.print("Enter new publisher: ");
                            String newPublisher = scanner.nextLine();
                            book.setPublisher(newPublisher);
                            break;
                        case 3:
                            System.out.print("Enter new publication year: ");
                            int newYear = scanner.nextInt();
                            book.setYear(newYear);
                            break;
                        case 4:
                            System.out.print("Enter new ISBN: ");
                            String newIsbn = scanner.nextLine();
                            book.setIsbn(newIsbn);
                            break;
                        case 5:
                            System.out.print("Enter new quantity: ");
                            int newQuantity = scanner.nextInt();
                            book.setQuantity(newQuantity);
                            break;
                        case 6:
                            System.out.print("Enter new description: ");
                            String newDescription = scanner.nextLine();
                            book.setDescription(newDescription);
                            break;
                        case 7:
                            System.out.print("Enter new thumbnail: ");
                            String newThumbnail = scanner.nextLine();
                            book.setThumbnail(newThumbnail);
                            break;
                        case 8:
                            System.out.print("Enter new language: ");
                            String newLanguage = scanner.nextLine();
                            book.setLanguage(newLanguage);
                            break;
                        case 0:
                            running = false;
                            break;
                        default:
                            System.out.println("Invalid choice. Please try again.");
                    }
                }

                return;
            }
        }

        System.out.println("No document found with ID: " + documentId);
    }

    public boolean findDocument(int documentId) {
        for (Book book : books) {
            if (book.getBookId() == documentId) {
                return true;
            }
        }
        return false;
    }


}
