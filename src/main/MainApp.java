package main;
import controllers.BorrowRecordController;
import controllers.MemberController;
import controllers.BookController;
import controllers.AdminController;
import utils.DatabaseConnection;


import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.Scanner;
import models.Book;

public class MainApp {
    public MainApp() {
    }

    public static void main(String[] args) throws SQLException {
        MemberController memberController = new MemberController();
        BookController bookController = new BookController();
        AdminController adminController = new AdminController();
        Scanner scanner = new Scanner(System.in);

        Connection connection = DatabaseConnection.getConnection();



        int choice = 0;
        int memberId;
        int bookId;

        do {
            System.out.println("Welcome to My Application!");
            System.out.println("[0] Exit");
            System.out.println("[1] Add Document");
            System.out.println("[2] Remove Document");
            System.out.println("[3] Update Document");
            System.out.println("[4] Find Document");
            System.out.println("[5] Display Document");
            System.out.println("[6] Add Member");
            System.out.println("[7] Borrow Document");
            System.out.println("[8] Return Document");
            System.out.println("[9] Display Member Info");
            System.out.println("[10] Remove Member");
            System.out.println("[11] Update Member");
            System.out.print("Please select an option: ");
            if (scanner.hasNextInt()) {
                choice = scanner.nextInt();
                switch (choice) {
                    case 0:
                        System.out.println("Exiting application...");
                        break;
                    case 1:

                        BookController.addBook();
                        waitToRead(scanner);
                        break;
                    case 2:
                        System.out.println("Enter ID: ");
                        try {
                            bookId = scanner.nextInt(); // Nhận input từ người dùng
                            BookController.removeBook(connection, bookId);
                        } catch (InputMismatchException e) {
                            System.out.println("Invalid input! Please enter a valid number."); // Thông báo lỗi khi nhập sai kiểu
                            scanner.next(); // Đọc và loại bỏ input sai
                        }
                        waitToRead(scanner);

                    case 3:
                        System.out.print("Enter the document ID to update: ");
                        bookId = scanner.nextInt();
                        bookController.updateDocument(bookId);
                        break;
                    case 4:
                        bookController.getBook();
                        System.out.print("Enter the document ID to find: ");
                        int documentId = scanner.nextInt();
                        if (bookController.findDocument(documentId)) {
                            System.out.println("Document found with ID: " + documentId);
                        } else {
                            System.out.println("No document found with ID: " + documentId);
                        }
                        waitToRead(scanner);
                        break;
                    case 5:
                        bookController.getBook();
                        System.out.print("Enter book ID: ");
                        bookId = scanner.nextInt();
                        bookController.displayDocument(bookId);
                        waitToRead(scanner);
                        break;
                    case 6:
                        adminController.addMember();
                        waitToRead(scanner);
                        break;
                    case 7:
                        BorrowRecordController.borrowDocument();
                        waitToRead(scanner);
                        break;
                    case 8:
                        BorrowRecordController.returnDocument();
                        waitToRead(scanner);
                        break;
                    case 9:
                        System.out.println("All members: ");
                        adminController.displayAllMember();

                        waitToRead(scanner);
                        break;
                    case 10:
                        System.out.print("Enter member ID: ");
                        memberId = scanner.nextInt();
                        adminController.removeMember(memberId);
                        waitToRead(scanner);
                        break;
                    case 11:
                        System.out.print("Enter member ID: ");
                        memberId = scanner.nextInt();
                        adminController.updateMember(memberId);
                        waitToRead(scanner);
                        break;
                    default:
                        System.out.println("Invalid choice, please try again.");
                }
            } else {
                System.out.println("Invalid input! Please enter an integer.");
                scanner.next();
            }
        } while(choice != 0);

        scanner.close();
    }

    public static void waitToRead(Scanner scanner) {
        while (true) {
            String checkEnter = scanner.nextLine();
            System.out.print("Press enter to back to menu");
            if (checkEnter.equals(scanner.nextLine())) break;
        }
    }

}

