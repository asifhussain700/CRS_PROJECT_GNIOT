package com.gniot.crs.business;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import com.gniot.crs.dao.ProfessorDAOImpl;
import com.gniot.crs.dao.ProfessorDAOInterface;

public class ProfessorOperation implements ProfessorInterface {
    
    private ProfessorDAOInterface professorDAO = new ProfessorDAOImpl();
    private Scanner scanner = new Scanner(System.in);
    
    
 
    final static String YELLOW = "\u001B[33m";
    final static String GREEN = "\u001B[32m";
    final static String RESET = "\u001B[0m";
	  
	  
    @Override
    public void addGrade() {
        try {
        	
        	System.out.print("Enter your ID: ");
        	int professorId = scanner.nextInt();
        	scanner.nextLine();
            // Get the professor's assigned courses
            List<Map<String, Object>> assignedCourses = professorDAO.getAssignedCourses(professorId);

            if (assignedCourses.isEmpty()) {
                System.out.println(YELLOW + "You have not been assigned any courses." + RESET);
                return;
            }

            // Display the assigned courses with both courseCode and courseName
            System.out.println(YELLOW + "<----Assigned Courses---->" + RESET);
            System.out.println("-----------------------------------");
            System.out.println("Course Code\t| Course Name");
            System.out.println("-----------------------------------");
            for (Map<String, Object> course : assignedCourses) {
                System.out.println(course.get("courseCode") + "\t\t| " + course.get("courseName"));
            }
            System.out.println("-----------------------------------");

            // Ask for the course code to add grades
            System.out.print("Enter the course code to add grades: ");
            int courseCode = scanner.nextInt();
            scanner.nextLine();

            


            

            // Get the list of students enrolled in the course
            List<Map<String, Object>> enrolledStudents = professorDAO.getEnrolledStudents(courseCode);

            // Display the list of students in tabular form
            System.out.println(YELLOW + "<----Enrolled Students in Course" + courseCode + "---->" + RESET);
         // Display enrolled students
       
            System.out.println("|-----------|");
            System.out.printf(" %-30s%n", "Username", "|");
            System.out.println("|-----------|");

            for (Map<String, Object> student : enrolledStudents) {
                String username = (String) student.get("username");
               

                System.out.printf("%-30s%n", username);
            }

            System.out.println("|-----------|");


            // Ask for the student's username to add grades
            System.out.print("Enter the username of the student to add grades: ");
            String username = scanner.nextLine();

            // Check if the entered username is enrolled in the course
            boolean validStudent = enrolledStudents.stream().anyMatch(student -> student.get("username").equals(username));
            if (!validStudent) {
                System.out.println(YELLOW + "The entered username is not enrolled in this course." + RESET);
                return;
            }

            // Ask for the grade
            System.out.print("Enter the grade (A to F) for the student: ");
            String grade = scanner.nextLine();

            // Validate the grade
            if (!isValidGrade(grade)) {
                System.err.println("Invalid grade. Grade must be between A and F.");
                return;
            }

            // Store the grade in the student_courses table
            boolean success = professorDAO.addGrade(username, courseCode, grade);
            if (success) {
                System.out.println(GREEN + "<----Grade added successfully---->" + RESET);
            } else {
                System.err.println("Failed to add grade. Please try again.");
            }
        } catch (SQLException ex) {
        	System.out.println("SQL Exception generated" +ex.getMessage());
        }
    }

    // Method to validate the grade
    private boolean isValidGrade(String grade) {
        // Assuming grades are A to F
        return grade.matches("[A-F]");
    }

    @Override
    public void viewEnrolledStudents() {
        try {
            System.out.print("Enter your ID: ");
            int professorId = scanner.nextInt();
            scanner.nextLine();

            // Get the professor's assigned courses
            List<Map<String, Object>> assignedCourses = professorDAO.getAssignedCourses(professorId);

            if (assignedCourses.isEmpty()) {
                System.err.println("You have not been assigned any courses.");
                return;
            }

            // Display the assigned courses with both courseCode and courseName
            System.out.println(YELLOW + "<------Assigned Courses------->" + RESET);
            System.out.println("Course Code\t| Course Name");
            for (Map<String, Object> course : assignedCourses) {
                System.out.println(course.get("courseCode") + "\t\t| " + course.get("courseName"));
            }

            // Ask for the course code to view enrolled students
            System.out.print("Enter the course code to view enrolled students: ");
            int courseCode = scanner.nextInt();
            scanner.nextLine();

            // Get the list of students enrolled in the course
            List<Map<String, Object>> enrolledStudents = professorDAO.getEnrolledStudents(courseCode);

            if (enrolledStudents.isEmpty()) {
                System.err.println("No students are enrolled in this course.");
                return;
            }

            // Display the list of students in tabular form
            System.out.println(YELLOW + "<----Enrolled Students in Course " + courseCode + "---->" + RESET);
            System.out.println("Username\t| ID");
            for (Map<String, Object> student : enrolledStudents) {
                System.out.println(student.get("username") + "\t\t| " + student.get("id"));
            }

        } catch (SQLException ex) {
        	System.out.println("SQL Exception generated" +ex.getMessage());
        }
    }
}
