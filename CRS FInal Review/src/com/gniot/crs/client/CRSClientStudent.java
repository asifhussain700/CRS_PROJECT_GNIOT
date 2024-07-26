package com.gniot.crs.client;

import com.gniot.crs.dao.StudentDAOImpl;
import com.gniot.crs.dao.StudentDAOInterface;
import java.util.InputMismatchException;
import java.util.Scanner;
import com.gniot.crs.bean.Student;
import com.gniot.crs.business.*;

public class CRSClientStudent {

    private static StudentDAOInterface studentDAO = new StudentDAOImpl();
    private static Scanner scanner = new Scanner(System.in);
     
    final static String YELLOW = "\u001B[33m";
    final static String GREEN = "\u001B[32m";
	final static String RESET = "\u001B[0m";

    static void studentlogin() {
        try {
            System.out.print("User Name: ");
            String username = scanner.nextLine();
            System.out.print("Password: ");
            String password = scanner.nextLine();

            String student = studentDAO.findStudentByUsername(username, password);
            if ("notApproved".equals(student)) {
                System.err.println("Your account is not approved yet. Please contact the admin.");
                return;
            } else if ("Approved".equals(student)) {
                System.out.println(GREEN + "Login successful!" + RESET);
                displayStudentMenu();
            } else {
                System.err.println("Invalid username or password or role.");
            }
        } catch (Exception e) {
            System.err.println("An error occurred during login: " + e.getMessage());
        }
    }

    private static void displayStudentMenu() {
        StudentInterface studOps = new StudentOperation();
        while (true) {
            try {
                System.out.println("Student Menu");
                System.out.println("1. Browse Catalog");
                System.out.println("2. Register for Course");
                System.out.println("3. Drop Course");
                System.out.println("4. View Grades");
                System.out.println("5. Account Info");
                System.out.println("6. Logout");

                System.out.print("Enter your choice: ");
                int choice = scanner.nextInt();
                scanner.nextLine();

                switch (choice) {
                    case 1:
                        studOps.browseCatalog();
                        break;
                    case 2:
                        studOps.registerForCourse();
                        break;
                    case 3:
                        studOps.dropCourse();
                        break;
                    case 4:
                        studOps.viewGrades();
                        break;
                    case 5:
                        studOps.accountInfo();
                        break;
                    case 6:
                        return;
                    default:
                        System.err.println("Invalid choice. Please try again.");
                }
            } catch (InputMismatchException e) {
                System.err.println("Invalid input. Please enter a valid choice.");
                scanner.nextLine(); // Clear the buffer
            } catch (Exception ex) {
                System.err.println("An error occurred: " + ex.getMessage());
            }
        }
    }

    static void changePassword() {
        try {
            System.out.print("User Name: ");
            String username = scanner.nextLine();
            System.out.print("Old Password: ");
            String oldPassword = scanner.nextLine();
            System.out.print("New Password: ");
            String newPassword = scanner.nextLine();

            boolean passwordChanged = studentDAO.updateStudentPassword(username, oldPassword, newPassword);
            if (passwordChanged) {
                System.out.println(GREEN + "Password changed successfully!" + RESET);
            } else {
                System.err.println("Failed to change password.");
            }
        } catch (Exception ex) {
            System.err.println("An error occurred while changing the password: " + ex.getMessage());
        }
    }

    static void register() {
        try {
            System.out.print("User Name: ");
            String username = scanner.nextLine();
            System.out.print("Password: ");
            String password = scanner.nextLine();
            System.out.print("Role: ");
            String role = scanner.nextLine();

            if (role.equalsIgnoreCase("admin")) {
                System.err.println("Registration of new admin is not allowed.");
                return;
            }

            System.out.print("First Name: ");
            String firstName = scanner.nextLine();
            System.out.print("Last Name: ");
            String lastName = scanner.nextLine();
            System.out.print("Gender: ");
            String gender = scanner.nextLine();
            System.out.print("Age: ");
            int age = scanner.nextInt();
            scanner.nextLine(); // Consume newline
            System.out.print("10th Percentage: ");
            double tenthPercentage = scanner.nextDouble();
            scanner.nextLine(); // Consume newline
            System.out.print("12th Percentage: ");
            double twelfthPercentage = scanner.nextDouble();
            scanner.nextLine(); // Consume newline
            System.out.print("Address: ");
            String address = scanner.nextLine();
            System.out.print("Phone Number: ");
            String phoneNumber = scanner.nextLine();
            System.out.print("Email ID: ");
            String emailId = scanner.nextLine();

            if (studentDAO.checkUserName(username) == null) {
                System.out.println(GREEN + "Registration successful! Please wait for admin approval." + RESET);

                Student student = new Student(username, password, role, firstName, lastName, gender, age, tenthPercentage, twelfthPercentage, address, phoneNumber, emailId);
                student.setUsername(username);
                student.setStudentPassword(password);
                student.setFirstName(firstName);
                student.setLastName(lastName);
                student.setGender(gender);
                student.setAge(age);
                student.setTenthPercentage(tenthPercentage);
                student.setTwelfthPercentage(twelfthPercentage);
                student.setAddress(address);
                student.setPhoneNumber(phoneNumber);
                student.setEmailId(emailId);

                studentDAO.addStudent(student);
            } else {
                System.err.println("User already exists.");
            }
        } catch (InputMismatchException ex) {
            System.err.println("Invalid input. Please enter valid details.");
//            scanner.nextLine(); // Clear the buffer
        } catch (Exception ex) {
            System.err.println("An error occurred during registration: " + ex.getMessage());
        }
    }
}
