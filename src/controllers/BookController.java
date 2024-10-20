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

    public Book findBookById(Connection connection, int bookId) throws SQLException {
        String sql = "SELECT * FROM books WHERE bookId = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, bookId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    // Tạo đối tượng Book từ kết quả truy vấn
                    return new Book(
                            rs.getInt("bookId"),
                            rs.getString("title"),
                            rs.getInt("authorId"),
                            rs.getString("publisher"),
                            rs.getInt("year"),
                            rs.getString("isbn"),
                            rs.getInt("quantity"),
                            rs.getInt("categoryId"),
                            rs.getString("googleId"),
                            rs.getString("description"),
                            rs.getString("thumbnail"),
                            rs.getString("language")
                    );
                } else {
                    // Trả về null nếu không tìm thấy sách
                    System.out.println("Book not found with ID: " + bookId);
                    return null;
                }
            }
        } catch (SQLException e) {
            System.out.println("Error finding book: " + e.getMessage());
            throw e; // Ném lại lỗi để xử lý ở nơi khác nếu cần
        }
        }

        public static void updateBook(Connection connection, Book book) throws SQLException {
            String sql = "UPDATE books SET title = ?, authorId = ?, publisher = ?, year = ?, isbn = ?, quantity = ?, categoryId = ?, googleId = ?, description = ?, thumbnail = ?, language = ? WHERE bookId = ?";

            try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
                pstmt.setString(1, book.getTitle());
                pstmt.setInt(2, book.getAuthorId());
                pstmt.setString(3, book.getPublisher());
                pstmt.setInt(4, book.getYear());
                pstmt.setString(5, book.getIsbn());
                pstmt.setInt(6, book.getQuantity());
                pstmt.setInt(7, book.getCategoryId());
                pstmt.setString(8, book.getGoogleId());
                pstmt.setString(9, book.getDescription());
                pstmt.setString(10, book.getThumbnail());
                pstmt.setString(11, book.getLanguage());
                pstmt.setInt(12, book.getBookId());

                // Thực hiện cập nhật
                int rowsAffected = pstmt.executeUpdate();
                if (rowsAffected > 0) {
                    System.out.println("Book updated successfully!");
                } else {
                    System.out.println("No book found with the given ID. Update failed.");
                }
            } catch (SQLException e) {
                System.out.println("Error updating book: " + e.getMessage());
                throw e; // Ném lại ngoại lệ để xử lý ở nơi khác nếu cần
            }
        }


    public void updateDocument(Connection connection) throws SQLException {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter book ID to update: ");
        int bookId = scanner.nextInt();
        scanner.nextLine(); // Clear the newline

        // Lấy thông tin sách từ cơ sở dữ liệu
        Book book = findBookById(connection, bookId); // tìm sách theo ID

        if (book != null) {
            boolean updating = true;

            while (updating) {
                // Hiển thị thông tin sách
                System.out.println("Current Book Info:");
                System.out.println(book);

                // Hiển thị các thuộc tính có thể thay đổi
                System.out.println("Which attribute do you want to update?");
                System.out.println("[1] Title");
                System.out.println("[2] Author ID");
                System.out.println("[3] Publisher");
                System.out.println("[4] Year");
                System.out.println("[5] ISBN");
                System.out.println("[6] Quantity");
                System.out.println("[7] Category ID");
                System.out.println("[8] Google ID");
                System.out.println("[9] Description");
                System.out.println("[10] Thumbnail");
                System.out.println("[11] Language");
                System.out.println("[0] Finish updating");

                int choice = scanner.nextInt();
                scanner.nextLine();

                switch (choice) {
                    case 1:
                        System.out.print("Enter new title: ");
                        book.setTitle(scanner.nextLine());
                        break;
                    case 2:
                        System.out.print("Enter new author ID: ");
                        book.setAuthorId(scanner.nextInt());
                        scanner.nextLine();
                        break;
                    case 3:
                        System.out.print("Enter new publisher: ");
                        book.setPublisher(scanner.nextLine());
                        break;
                    case 4:
                        System.out.print("Enter new year: ");
                        book.setYear(scanner.nextInt());
                        scanner.nextLine();
                        break;
                    case 5:
                        System.out.print("Enter new ISBN: ");
                        book.setIsbn(scanner.nextLine());
                        break;
                    case 6:
                        System.out.print("Enter new quantity: ");
                        book.setQuantity(scanner.nextInt());
                        scanner.nextLine();
                        break;
                    case 7:
                        System.out.print("Enter new category ID: ");
                        book.setCategoryId(scanner.nextInt());
                        scanner.nextLine();
                        break;
                    case 8:
                        System.out.print("Enter new Google ID: ");
                        book.setGoogleId(scanner.nextLine());
                        break;
                    case 9:
                        System.out.print("Enter new description: ");
                        book.setDescription(scanner.nextLine());
                        break;
                    case 10:
                        System.out.print("Enter new thumbnail: ");
                        book.setThumbnail(scanner.nextLine());
                        break;
                    case 11:
                        System.out.print("Enter new language: ");
                        book.setLanguage(scanner.nextLine());
                        break;
                    case 0:
                        updating = false;
                        break;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            }

            // Cập nhật thông tin sách vào cơ sở dữ liệu
            updateBook(connection, book);
        } else {
            System.out.println("Book not found!");
        }
    }


}
