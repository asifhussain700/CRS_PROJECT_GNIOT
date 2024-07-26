package com.gniot.crs.client;

import com.gniot.crs.business.AdminInterface;
import com.gniot.crs.business.AdminOperation;

import java.util.Scanner;

public class CRSClientAdmin {

    public static Scanner scanner = new Scanner(System.in);
    
    final static String YELLOW = "\u001B[33m";
    final static String GREEN = "\u001B[32m";
	final static String RESET = "\u001B[0m";
 
	
	static void adminLogin() {
        System.out.print("User Name: ");
        String username = scanner.nextLine();
        System.out.print("Password: ");
        String password = scanner.nextLine();

        if (username.equals("admin") && password.equals("admin123")) {
            System.out.println(GREEN + "Login successful!" +  RESET);
            displayAdminMenu();
        } else {
            System.err.println("Invalid username or password for admin.");
        }
    }

    public static void displayAdminMenu() {
        AdminInterface adminOps = new AdminOperation();
        while (true) {
            System.out.println("Admin Menu");
            System.out.println("1. Add Course To Catalog");
            System.out.println("2. Remove Course From Catalog");
            System.out.println("3. Add Professor");
            System.out.println("4. Approve Students");
            System.out.println("5. Remove All Students");
            System.out.println("6. Remove Professor (by name)");
            System.out.println("7. Remove All Professors");
            System.out.println("8. Assign Course to Professor");
            System.out.println("9. Remove Course from Professor");
            System.out.println("10. Set Fee for Courses"); 
            System.out.println("11. Logout");

            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    adminOps.addCourseToCatalog();
                    break;
                case 2:
                    adminOps.removeCourses();
                    break;
                case 3:
                    adminOps.addProfessor();
                    break;
                case 4:
                    adminOps.approveStudents();
                    break;
                case 5:
                    adminOps.deleteAllStudents();
                    break;
                case 6:
                    adminOps.deleteProfessorByName();
                    break;
                case 7:
                    adminOps.deleteAllProfessors();
                    break;
                case 8:
                    adminOps.assignCourseToProfessor();
                    break;
                case 9:
                    adminOps.removeCourseFromProfessor();
                    break;
                case 10:
                    adminOps.setFeeForCourses();
                    break;
                case 11:
                    return;
                default:
                    System.err.println("Invalid choice. Please try again.");
            }
        }
    }
}
