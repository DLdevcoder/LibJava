package main;
import controllers.MemberController;

import controllers.BookController;


import java.sql.SQLException;
import java.util.Scanner;

public class MainApp {
    public MainApp() {
    }

    public static void main(String[] args) throws SQLException {
        MemberController memberController = new MemberController();
        BookController bookController = new BookController();
        Scanner scanner = new Scanner(System.in);
        int choice = 0;
        int memberId;

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
                        break;
                    case 2:
                        removeDocument();
                        break;
                    case 3:
                        updateDocument();
                        break;
                    case 4:
                        findDocument();
                        break;
                    case 5:
                        displayDocument();
                        break;
                    case 6:
                        memberController.addMember();
                        break;
                    case 7:
                        borrowDocument();
                        break;
                    case 8:
                        returnDocument();
                        break;
                    case 9:
                        memberController.getMembers();
                        System.out.print("Enter member ID: ");
                        memberId = scanner.nextInt();
                        memberController.displayMemberInfo(memberId);
                        break;
                    case 10:
                        System.out.print("Enter member ID: ");
                        memberId = scanner.nextInt();
                        memberController.removeMember(memberId);
                        break;
                    case 11:
                        System.out.print("Enter member ID: ");
                        memberId = scanner.nextInt();
                        memberController.updateMember(memberId);
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

    public static void addBook() throws SQLException {
    }

    public static void removeDocument() {
    }

    public static void updateDocument() {
    }

    public static void findDocument() {
    }

    public static void displayDocument() {
    }

    public static void borrowDocument() {
    }

    public static void returnDocument() {
    }

}

