package com.gniot.crs.business;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import com.gniot.crs.bean.Course;
import com.gniot.crs.bean.Professor;
import com.gniot.crs.bean.Student;
import com.gniot.crs.dao.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class AdminOperation implements AdminInterface {
    private final AdminDAOInterface adminDAO;
	private boolean success;

    public AdminOperation() {
        this.adminDAO = new AdminDAOImpl();
    }
    final static String YELLOW = "\u001B[33m";
    final static String GREEN = "\u001B[32m";
    final static String RESET = "\u001B[0m";

    @Override
    public void addProfessor(Professor professor) {
        adminDAO.addProfessor(professor);
        System.out.println(GREEN + "<---Professor " + professor.getProfessorName() + " added successfully--->" + RESET);
    }

    @Override
    public void deleteProfessorByName(int professorId) {
        System.out.println(YELLOW + "<----Delete Professor by ID---->" + RESET);
        boolean success = adminDAO.deleteProfessorByName(professorId);
		if (success) {
		    System.out.println(GREEN + "<----Professor removed successfully.---->" + RESET);
		} else {
		    System.err.println("Failed to delete professor.");
		}
    }

    @Override
    public boolean deleteAllProfessors() {
        System.out.println(YELLOW + "Deleting All Professors......." + RESET);
        boolean success = adminDAO.deleteAllProfessors();
        if (success) {
            System.out.println(GREEN + "<----All professors deleted successfully.---->" + RESET);
        } else {
            System.err.println("Failed to delete all professors.");
        }
        return success;
    }

    @Override
    public void addCourseToCatalog(Course course) {
        System.out.println(YELLOW + "<----Adding Course To Course Catalog---->" + RESET);
        adminDAO.addCourseToCatalog(course);
        System.out.println(GREEN + "<----Course Successfully Added To Catalog---->" + RESET);
    }

    @Override
    public boolean removeCourses(String courseName) {
        try {
            boolean success = adminDAO.removeCourse(courseName);
            if (success) {
                System.out.println(GREEN + "<----Course removed successfully.---->" + RESET);
            } else {
                System.err.println("Failed to remove course. Please check the course name.");
            }
        } catch (SQLException e) {
            System.err.println("SQL Exception generated: " + e.getMessage());
        }
        return success;
    }

    @Override
    public void deleteAllStudents() {
        adminDAO.deleteAllStudents();
    }

    @Override
    public void approveStudents(List<String> usernames) {
        for (String username : usernames) {
            Student student = adminDAO.findStudentByUsername(username);
            if (student != null && student.getRole().equalsIgnoreCase("student")) {
                adminDAO.approveStudent(student.getUsername());
                System.out.println(GREEN + "<----Student " + student.getUsername() + " approved successfully!---->" + RESET);
            } else {
                System.out.println(YELLOW + "Student not found or invalid role." + RESET);
            }
        }
    }

    @Override
    public void assignCourseToProfessor(int courseId, int professorId) {
        try {
            String assignedCourses = adminDAO.getAssignedCourses(professorId);
            if (assignedCourses == null || assignedCourses.isEmpty()) {
                assignedCourses = String.valueOf(courseId);
            } else {
                assignedCourses += "," + courseId;
            }
            boolean success = adminDAO.updateAssignedCourses(professorId, assignedCourses);
            if (success) {
                System.out.println(GREEN + "<----Course assigned successfully.---->" + RESET);
            } else {
                System.err.println("Failed to assign course. Please try again.");
            }
        } catch (Exception e) {
            System.err.println("An error occurred: " + e.getMessage());
        }
    }

    @Override
    public void removeCourseFromProfessor(int professorId, String courseCode) {
        try {
            String assignedCourses = adminDAO.getAssignedCourses(professorId);
            if (assignedCourses == null || assignedCourses.isEmpty()) {
                System.out.println(YELLOW + "No courses assigned to this professor." + RESET);
                return;
            }
            String[] courses = assignedCourses.split(",");
            String updatedCourses = String.join(",", Arrays.stream(courses).filter(course -> !course.equals(courseCode)).toArray(String[]::new));
            boolean success = adminDAO.updateAssignedCourses(professorId, updatedCourses);
            if (success) {
                System.out.println(GREEN + "<----Course removed successfully.---->" + RESET);
            } else {
                System.err.println("Failed to remove course. Please try again.");
            }
        } catch (Exception e) {
            System.err.println("An error occurred: " + e.getMessage());
        }
    }

    @Override
    public void setFeeForCourses(int courseCode, int fee) {
        try {
            boolean success = adminDAO.setFeeForCourse(courseCode, fee);
            if (success) {
                System.out.println(GREEN + "<----Fee for course " + courseCode + " set to " + fee + " successfully---->" + RESET);
            } else {
                System.err.println("Failed to set the fee for the course. Please check if the course code is correct.");
            }
        } catch (Exception ex) {
            System.err.println("An error occurred while setting the fee for the course: " + ex.getMessage());
        }
    }
}
