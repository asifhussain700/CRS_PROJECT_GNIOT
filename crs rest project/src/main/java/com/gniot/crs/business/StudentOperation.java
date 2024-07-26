package com.gniot.crs.business;

import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import org.springframework.stereotype.Service;

import com.gniot.crs.bean.Course;
import com.gniot.crs.bean.Student;
import com.gniot.crs.dao.StudentDAOImpl;
import com.gniot.crs.dao.StudentDAOInterface;

@Service
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
   
    
    
  
    
    @Override
    public List<Course> browseCatalog() {
        try {
            return studentDAO.getCourses();
        } catch (Exception ex) {
            throw new RuntimeException("An error occurred while browsing the catalog: " + ex.getMessage(), ex);
        }
    }

    
    public String registerForCourse(String username, List<Integer> primaryCourseCodes, List<Integer> alternateCourseCodes) {
        try {
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
                return "You are already registered for the maximum number of courses (4 primary and 2 alternate). Total course fee: " + totalCourseFee;
            }

            // Register primary courses
            for (int primaryCourseCode : primaryCourseCodes) {
                if (primaryCourseCount >= 4) break;

                double courseFee = studentDAO.getCourseFee(primaryCourseCode); // Fetch course fee
                boolean success = studentDAO.registerForCourse(username, primaryCourseCode, "primary");
                if (success) {
                    primaryCourseCount++;
                    totalCourseFee += courseFee;
                } else {
                    return "Failed to register for the primary course with code: " + primaryCourseCode;
                }
            }

            // Register alternate courses
            for (int alternateCourseCode : alternateCourseCodes) {
                if (alternateCourseCount >= 2) break;

                double courseFee = studentDAO.getCourseFee(alternateCourseCode); // Fetch course fee
                boolean success = studentDAO.registerForCourse(username, alternateCourseCode, "alternate");
                if (success) {
                    alternateCourseCount++;
                    totalCourseFee += courseFee;
                } else {
                    return "Failed to register for the alternate course with code: " + alternateCourseCode;
                }
            }

            // Notify if any courses are still pending registration
            StringBuilder response = new StringBuilder();
            if (primaryCourseCount < 4) {
                response.append("You still need to register for ").append(4 - primaryCourseCount).append(" primary course(s). ");
            }

            if (alternateCourseCount < 2) {
                response.append("You still need to register for ").append(2 - alternateCourseCount).append(" alternate course(s). ");
            }

            // Update student's balance with the total course fee
            studentDAO.updateStudentBalance(username, totalCourseFee);

            response.append("Total course fee: ").append(totalCourseFee);
            return response.toString();

        } catch (Exception ex) {
            return "An error occurred while registering for courses: " + ex.getMessage();
        }
    }
    
    
    
// Drop Course 
  @Override
    public boolean dropCourse(String username, int courseCode) throws Exception {
        try {
            // Get the list of registered courses for the student
            List<Map<String, Object>> registeredCourses = studentDAO.getRegisteredCourses(username);

            // Check if the student is registered in any courses
            if (registeredCourses.isEmpty()) {
                return false; // No courses to drop
            }

            // Attempt to drop the course
            boolean success = studentDAO.dropCourse(username, courseCode);
            if (success) {
                // Retrieve course fee for the dropped course
                int courseFee = studentDAO.getCourseFee(courseCode);

                // Subtract course fee from student's balance and update database
                studentDAO.updateStudentBalance(username, -courseFee);

                return true; // Successfully dropped
            } else {
                return false; // Failed to drop
            }
        } catch (Exception ex) {
            throw new Exception("An error occurred while dropping the course: " + ex.getMessage(), ex);
        }
    }
    
     
    // View Grades
  @Override
    public List<Map<String, Object>> viewGrades(String username) throws Exception {
        try {
            return studentDAO.getCoursesWithGrades(username);
        } catch (Exception ex) {
            throw new Exception("An error occurred while retrieving grades: " + ex.getMessage(), ex);
        }
    }
  
  

    // Account Info
  @Override
    public Student accountInfo(String username) throws Exception {
        try {
            return studentDAO.getStudentInfo(username);
        } catch (Exception ex) {
            throw new Exception("An error occurred while retrieving student information: " + ex.getMessage(), ex);
        }
    }
}
