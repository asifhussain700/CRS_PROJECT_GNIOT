package com.gniot.crs.client;

import java.util.Scanner;

public class CRSClient {

    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        while (true) {
            try {
                // Displaying menu options
                System.out.println("Welcome to the CRS Application");
                System.out.println("1. Login");
                System.out.println("2. Registration of the Student");
                System.out.println("3. Change password");
                System.out.println("4. Exit");

                // Prompting user for choice
                System.out.print("Enter your choice: ");
                int choice = scanner.nextInt();
                scanner.nextLine(); // Consuming Newline

                // Handling user choice
                switch (choice) {
                    case 1:
                        login(); // Perform Login
                        break;
                    case 2:
                        CRSClientStudent.register(); // Register a Student
                        break;
                    case 3:
                        CRSClientStudent.changePassword(); // Change Password
                        break;
                    case 4:
                        System.out.println("Exiting..."); // Exit the Application
                        System.exit(0);
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            } catch (Exception ex) {
                System.err.println("An error occurred: " + ex.getMessage());
                scanner.nextLine(); // Clear the buffer
            }
        }
    }

    // Method to handle login process
    private static void login() {
        try {
            System.out.print("Role: ");
            String role = scanner.nextLine();

            // Based on the role, invoke corresponding login method
            switch (role.toLowerCase()) {
                case "student":
                    CRSClientStudent.studentlogin();  // Student login
                    break;
                case "professor":
                    CRSClientProfessor.professorlogin(); // Professor login
                    break;
                case "admin":
                    CRSClientAdmin.adminLogin(); // Admin login
                    break;
                default:
                    System.err.println("Invalid role.");
            }
        } catch (Exception ex) {
            System.err.println("An error occurred during login: " + ex.getMessage());
        }
    }
}
