package com.gniot.crs.client;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import com.gniot.crs.business.ProfessorInterface;
import com.gniot.crs.business.ProfessorOperation;
import com.gniot.crs.utils.DBUtils;

public class CRSClientProfessor {
	
	// Scanner object for user input
	public static Scanner scanner = new Scanner(System.in);

	  final static String YELLOW = "\u001B[33m";
	    final static String GREEN = "\u001B[32m";
		final static String RESET = "\u001B[0m";
		
		
	// Method to handle professor login
	public static void professorlogin() {
	   
	   // Prompting for username and password
	   System.out.print("User Name: ");
       String username = scanner.nextLine();
       System.out.print("Password: ");
       String password = scanner.nextLine();
      
       // Database connection and query execution
       Connection conn = null;
       PreparedStatement stmt = null;
       ResultSet rs = null;

       try {
           conn = DBUtils.getConnection();
           String sql = "SELECT * FROM src_schema.professors WHERE professorName = ? AND professorPassword = ?";
           stmt = conn.prepareStatement(sql);
           stmt.setString(1, username);
           stmt.setString(2, password);
           rs = stmt.executeQuery();
           
           // Checking login result
           if (rs.next()) {
               System.out.println(GREEN + "<----Login successful. Welcome " + rs.getString("professorName")+ "---->" + RESET);
               displayProfessorMenu(); // Displaying professor menu
           } else {
               System.err.println("Invalid username or password. Please try again.");
           }
       } catch (SQLException e) {
           e.printStackTrace();
       } finally {
           // Closing resources
           try {
               if (rs != null) rs.close();
               if (stmt != null) stmt.close();
               if (conn != null) conn.close();
           } catch (SQLException e) {
               e.printStackTrace();
           }
       }
   
	}
	
	// Method to display professor menu
	 private static void displayProfessorMenu() {
	        ProfessorInterface profOps = new ProfessorOperation();
	        while (true) {
	            System.out.println("Professor Menu");
	            System.out.println("1. Add Grade");
	            System.out.println("2. View Enrolled Students");
	            System.out.println("3. Logout");

	            System.out.print("Enter your choice: ");
	            int choice = scanner.nextInt();
	            scanner.nextLine();

	            // Handling menu options
	            switch (choice) {
	                case 1:
	                    profOps.addGrade(); // Add grade operation
	                    break;
	                case 2:
	                    profOps.viewEnrolledStudents();
	                    break;
	                case 3:
	                    return; // Logout
	                default:
	                    System.err.println("Invalid choice. Please try again.");
	            }
	        }
	    }
}
