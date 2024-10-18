package main;
import controllers.MemberController;
import java.util.Scanner;

public class MainApp {
    public MainApp() {
    }

    public static void main(String[] args) {
        MemberController memberController = new MemberController();
        Scanner scanner = new Scanner(System.in);
        int choice = 0;

        do {
            System.out.println("Welcome to My Application!");
            System.out.println("[0] Exit");
            System.out.println("[1] Add Document");
            System.out.println("[2] Remove Document");
            System.out.println("[3] Update Document");
            System.out.println("[4] Find Document");
            System.out.println("[5] Display Document");
            System.out.println("[6] Add User");
            System.out.println("[7] Borrow Document");
            System.out.println("[8] Return Document");
            System.out.println("[9] Display User Info");
            System.out.print("Please select an option: ");
            if (scanner.hasNextInt()) {
                choice = scanner.nextInt();
                switch (choice) {
                    case 0:
                        System.out.println("Exiting application...");
                        break;
                    case 1:
                        addDocument();
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
                        memberController.displayMemberInfo(1);
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

    public static void addDocument() {
    }

    public static void removeDocument() {
    }

    public static void updateDocument() {
    }

    public static void findDocument() {
    }

    public static void displayDocument() {
    }


    public static void addUser() {
 }


    public static void borrowDocument() {
    }

    public static void returnDocument() {
    }

}

