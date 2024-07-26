package com.gniot.crs.business;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import org.springframework.stereotype.Service;

import com.gniot.crs.dao.ProfessorDAOImpl;
import com.gniot.crs.dao.ProfessorDAOInterface;

@Service

public  class ProfessorOperation implements ProfessorInterface {
    private final ProfessorDAOInterface professorDAO = new ProfessorDAOImpl();
    private Scanner scanner = new Scanner(System.in);
    
    
 
    final static String YELLOW = "\u001B[33m";
    final static String GREEN = "\u001B[32m";
    final static String RESET = "\u001B[0m";
	  
	  
    @Override
    public boolean addGrade(int professorId, int courseCode, String username, String grade) throws SQLException {
        // Get the professor's assigned courses
        List<Map<String, Object>> assignedCourses = professorDAO.getAssignedCourses(professorId);

        if (assignedCourses.isEmpty()) {
            System.out.println(YELLOW + "You have not been assigned any courses." + RESET);
            return false;
        }

        // Check if the professor is assigned to the specified course
        boolean isAssignedCourse = assignedCourses.stream().anyMatch(course -> course.get("courseCode").equals(courseCode));
        if (isAssignedCourse) {
            System.out.println(YELLOW + "You are not assigned to this course." + RESET);
            return false;
        }

        // Get the list of students enrolled in the course
        List<Map<String, Object>> enrolledStudents = professorDAO.getEnrolledStudents(courseCode);

        // Check if the entered username is enrolled in the course
        boolean validStudent = enrolledStudents.stream().anyMatch(student -> student.get("username").equals(username));
        if (!validStudent) {
            System.out.println(YELLOW + "The entered username is not enrolled in this course." + RESET);
            return false;
        }

        // Store the grade in the student_courses table
        return professorDAO.addGrade(username, courseCode, grade);
    }

    // Method to validate the grade
    private boolean isValidGrade(String grade) {
        // Assuming grades are A to F
        return grade.matches("[A-F]");
    }

    
    
    
    @Override
   public List<Map<String, Object>> getAssignedCourses(int professorId) throws Exception {
        try {
            return professorDAO.getAssignedCourses(professorId);
        } catch (Exception ex) {
            throw new Exception("Failed to retrieve assigned courses: " + ex.getMessage(), ex);
        }
    }

    public List<Map<String, Object>> getEnrolledStudents(int courseCode) throws Exception {
        try {
            return professorDAO.getEnrolledStudents(courseCode);
        } catch (Exception ex) {
            throw new Exception("Failed to retrieve enrolled students: " + ex.getMessage(), ex);
        }
    }
}
