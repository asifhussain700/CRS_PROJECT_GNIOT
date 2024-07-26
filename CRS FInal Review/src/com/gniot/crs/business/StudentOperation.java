package com.gniot.crs.business;

import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import com.gniot.crs.bean.Course;
import com.gniot.crs.bean.Student;
import com.gniot.crs.dao.StudentDAOImpl;
import com.gniot.crs.dao.StudentDAOInterface;

public class StudentOperation implements StudentInterface {

    private static Scanner scanner = new Scanner(System.in);
    private StudentDAOInterface studentDAO;

    public StudentOperation() {
        this.studentDAO = new StudentDAOImpl();
    }
    
    
    final String RED = "\u001B[31m"; 
    final static String YELLOW = "\u001B[33m";
    final static String GREEN = "\u001B[32m";
    final static String RESET = "\u001B[0m";
    // Browse Catalog
    @Override
    public void browseCatalog() {
        try {
            System.out.println(YELLOW + "<----Course Catalog---->"+ RESET);
            List<Course> courses = studentDAO.getCourses();
            if (courses.isEmpty()) {
                System.out.println(YELLOW + "No courses available." + RESET);
            } else {
                // Print table header
                System.out.println("---------------------------------------------------------");
                System.out.printf("%-15s %-30s %-30s%n", "Course Code", "Course Name", "Type");
                System.out.println("---------------------------------------------------------");

                for (Course course : courses) {
                    System.out.printf("%-15s %-30s %-30s%n", course.getCourseCode(), course.getCourseName(), course.getCourseType());
                    System.out.println("----------------------------------------------------------");
                }
            }
        } catch (Exception ex) {
            System.err.println("An error occurred while browsing the catalog: " + ex.getMessage());
        }
    }

    @Override
    public void registerForCourse() {
        try {
            System.out.println(YELLOW +"<---- Register For Course ---->" + RESET);
            System.out.print("Enter your username: ");
            String username = scanner.nextLine();

            List<Map<String, Object>> registeredCourses = studentDAO.getRegisteredCourses(username);
            int primaryCourseCount = 0;
            int alternateCourseCount = 0;
            int totalCourseFee = 0;

            // Count primary and alternate courses
            for (Map<String, Object> course : registeredCourses) {
                String courseType = (String) course.get("courseType");
                int courseFee = studentDAO.getCourseFee((int) course.get("courseCode")); // Fetch course fee
                totalCourseFee += courseFee;

                if ("primary".equalsIgnoreCase(courseType)) {
                    primaryCourseCount++;
                } else if ("alternate".equalsIgnoreCase(courseType)) {
                    alternateCourseCount++;
                }
            }

            // Check if the student is already registered for the maximum number of courses
            if (primaryCourseCount == 4 && alternateCourseCount == 2) {
                System.out.println(YELLOW +"You are already registered for the maximum number of courses (4 primary and 2 alternate)." + RESET);
                System.out.println(YELLOW +"Total course fee: " + totalCourseFee + RESET);
                return;
            }

         // Display registered courses
            System.out.println(YELLOW +"<----Registered Courses---->" + RESET);
            System.out.println("-----------------------------------------------------------------------------------------");
            System.out.printf("%-15s | %-30s | %-15s | %-10s%n", "Course Code", "Course Name", "Course Type", "Course Fee");
            System.out.println("-----------------------------------------------------------------------------------------");

            for (Map<String, Object> course : registeredCourses) {
                int courseCode = (int) course.get("courseCode");
                String courseName = (String) course.get("courseName");
                String courseType = (String) course.get("courseType");
                int courseFee = studentDAO.getCourseFee(courseCode);

                System.out.printf("%-15s | %-30s | %-15s | %-10d%n", courseCode, courseName, courseType, courseFee);
            }

            System.out.println("------------------------------------------------------------------------------------------");


         // Display available courses
            System.out.println(YELLOW +"<---Available Courses--->" + RESET);
            List<Course> courses = studentDAO.getCourses();
            System.out.println("-----------------------------------------------------------------------------------------");
            System.out.printf("%-15s | %-30s | %-15s%n", "Course Code", "Course Name", "Course Type");
            System.out.println("-----------------------------------------------------------------------------------------");

            for (Course course : courses) {
                int courseCode = course.getCourseCode();
                String courseName = course.getCourseName();
                String courseType = course.getCourseType();
 

                System.out.printf("%-15s | %-30s | %-15s%n", courseCode, courseName, courseType);
            }

            System.out.println("----------------------------------------------------------------------------------------");


            // Register primary courses
            while (primaryCourseCount < 4) {
                System.out.print("Enter primary course code: ");
                int primaryCourseCode = Integer.parseInt(scanner.nextLine());
                double courseFee = studentDAO.getCourseFee(primaryCourseCode); // Fetch course fee
                boolean success = studentDAO.registerForCourse(username, primaryCourseCode, "primary");
                if (success) {
                    primaryCourseCount++;
                    totalCourseFee += courseFee;
                    System.out.println(GREEN + "<---Successfully registered for the primary course with code: " + primaryCourseCode + "--->" + RESET);
                    System.out.println(GREEN + "Course Fee: " + courseFee + RESET);
                } else {
                    System.err.println("Failed to register for the primary course with code: " + primaryCourseCode );
                }

                // Exit if primary courses are complete
                if (primaryCourseCount == 4) {
                    break;
                }
            }

            // Register alternate courses
            while (alternateCourseCount < 2) {
                System.out.print("Enter alternate course code: ");
                int alternateCourseCode = Integer.parseInt(scanner.nextLine());
                double courseFee = studentDAO.getCourseFee(alternateCourseCode); // Fetch course fee
                boolean success = studentDAO.registerForCourse(username, alternateCourseCode, "alternate");
                if (success) {
                    alternateCourseCount++;
                    totalCourseFee += courseFee;
                    System.out.println(GREEN + "<---Successfully registered for the alternate course with code: " + alternateCourseCode +"--->" + RESET);
                    System.out.println(GREEN + "Course Fee: " + courseFee + RESET);
                } else {
                    System.err.println("Failed to register for the alternate course with code: " + alternateCourseCode);
                }

                // Exit if alternate courses are complete
                if (alternateCourseCount == 2) {
                    break;
                }
            }

            // Notify if any courses are still pending registration
            if (primaryCourseCount < 4) {
                System.out.println(YELLOW +"You still need to register for " + (4 - primaryCourseCount) + " primary course(s)." + RESET);
            }

            if (alternateCourseCount < 2) {
                System.out.println(YELLOW +"You still need to register for " + (2 - alternateCourseCount) + " alternate course(s)." + RESET);
            }

            // Update student's balance with the total course fee
            studentDAO.updateStudentBalance(username, totalCourseFee);
            System.out.println(YELLOW +"Total course fee: " + totalCourseFee + RESET);

        } catch (NumberFormatException ex) {
            System.err.println("Invalid course code format. Please enter a valid course code.");
        } catch (Exception ex) {
            System.err.println("An error occurred while registering for courses: " + ex.getMessage());
        }
    }


    @Override
    public boolean dropCourse() {
        try {
            System.out.println(YELLOW +"<----Drop Course---->" + RESET);
            System.out.print("Enter your username: ");
            String username = scanner.nextLine();

            // Get the list of registered courses for the student
            List<Map<String, Object>> registeredCourses = studentDAO.getRegisteredCourses(username);

            // Display the list of registered courses
            if (registeredCourses.isEmpty()) {
                System.out.println(YELLOW +"You are not registered in any courses." + RESET);
                return false;
            } else {
           
            	// Display registered courses
            	System.out.println(YELLOW +"Registered Courses:"+ RESET);
            	System.out.println("-----------------------------------------------------------------------------------------");
            	System.out.printf("%-15s | %-30s | %-15s | %-10s%n", "Course Code", "Course Name", "Course Type", "Course Fee");
            	System.out.println("-----------------------------------------------------------------------------------------");

            	for (Map<String, Object> course : registeredCourses) {
            	    int courseCode = (int) course.get("courseCode");
            	    String courseName = (String) course.get("courseName");
            	    String courseType = (String) course.get("courseType");
            	    int courseFee = (int) course.get("courseFee");

            	    System.out.printf("%-15d | %-30s | %-15s | %-10d%n", courseCode, courseName, courseType, courseFee);
            	}

            	System.out.println("------------------------------------------------------------------------------------------");


            }

            // Ask for the course code to drop
            System.out.print("Enter the course code to drop: ");
            int courseCode = Integer.parseInt(scanner.nextLine());

            // Attempt to drop the course
            boolean success = studentDAO.dropCourse(username, courseCode);
            if (success) {
                // Retrieve course fee for the dropped course
                int courseFee = studentDAO.getCourseFee(courseCode);

                // Subtract course fee from student's balance and update database
                studentDAO.updateStudentBalance(username, -courseFee);

                System.out.println(GREEN + "<---Successfully dropped the course with code: " + courseCode + "--->" + RESET);
            } else {
                System.err.println("Failed to drop the course. Please check if the course code is correct.");
            }
            return success;
        } catch (SQLException ex) {
            System.err.println("Failed to retrieve registered courses. Please try again.");
            System.out.println("SQL Exception generated: " + ex.getMessage());
            return false;
        } catch (NumberFormatException ex) {
            System.err.println("Invalid course code format. Please enter a valid course code.");
            return false;
        } catch (Exception ex) {
            System.err.println("An error occurred while dropping the course: " + ex.getMessage());
            return false;
        }
    }
    

    @Override
    public void viewGrades() {
        try {
            System.out.print("Enter your username: ");
            String username = scanner.nextLine();

            List<Map<String, Object>> coursesWithGrades = studentDAO.getCoursesWithGrades(username);
            if (coursesWithGrades.isEmpty()) {
                System.out.println(YELLOW +"You are not registered in any courses." + RESET);
            } else {
            	System.out.println(YELLOW +"<-------Courses and Grades------->" + RESET);
            	System.out.println("--------------------------------------------------------------");
            	System.out.println(String.format("%-15s | %-30s | %10s", "Course Code", "Course Name", "Grade"));
            	System.out.println("--------------------------------------------------------------");

            	for (Map<String, Object> course : coursesWithGrades) {
            	    System.out.println(String.format("%-15s | %-30s | %10s", 
            	                                     course.get("courseCode"), 
            	                                     course.get("courseName"), 
            	                                     course.get("grade")));
            	}

            	System.out.println("--------------------------------------------------------------");

            }
        } catch (SQLException ex) {
            System.err.println("Failed to retrieve grades. Please try again.");
            System.out.println("SQL Exception generated: " + ex.getMessage());
        } catch (Exception ex) {
            System.err.println("An error occurred while viewing grades: " + ex.getMessage());
        }
    }

    // Account Info
    @Override
    public void accountInfo() {
        try {
            System.out.print("Enter your username: ");
            String username = scanner.nextLine();
            System.out.println(YELLOW +"<------Account Info----->"+ RESET);

            Student student = studentDAO.getStudentInfo(username);
            if (student != null) {
                System.out.println("Student Information:");
                System.out.println("----------------------------------------");
                System.out.printf("| %-15s | %-15s |\n", "Attribute", "Value");
                System.out.println("----------------------------------------");
                System.out.printf("| %-15s | %-15s |\n", "Username", student.getUsername());
                System.out.printf("| %-15s | %-15s |\n", "First Name", student.getFirstName());
                System.out.printf("| %-15s | %-15s |\n", "Last Name", student.getLastName());
                System.out.printf("| %-15s | %-15s |\n", "Gender", student.getGender());
                System.out.printf("| %-15s | %-15d |\n", "Age", student.getAge());
                System.out.printf("| %-15s | %-15.2f |\n", "10th Percentage", student.getTenthPercentage());
                System.out.printf("| %-15s | %-15.2f |\n", "12th Percentage", student.getTwelfthPercentage());
                System.out.printf("| %-15s | %-15s |\n", "Address", student.getAddress());
                System.out.printf("| %-15s | %-15s |\n", "Phone Number", student.getPhoneNumber());
                System.out.printf("| %-15s | %-15s |\n", "Email ID", student.getEmailId());
                System.out.println("-----------------------------------------");
            } else {
                System.err.println("Student not found.");
            }
        } catch (SQLException ex) {
            System.err.println("Failed to retrieve student information. Please try again.");
            System.out.println("SQL Exception generated: " + ex.getMessage());
        } catch (Exception ex) {
            System.err.println("An error occurred while retrieving account information: " + ex.getMessage());
        }
    }
}