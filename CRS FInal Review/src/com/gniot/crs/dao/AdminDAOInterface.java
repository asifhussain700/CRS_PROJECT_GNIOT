package com.gniot.crs.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.gniot.crs.bean.Course;
import com.gniot.crs.bean.Professor;
import com.gniot.crs.bean.Student;

/**
 * AdminDAOInterface defines the data access operations that can be performed
 * by an administrator in the course registration system.
 */
public interface AdminDAOInterface {

    // Static list to hold students
    static List<Student> students = new ArrayList<>();

    /**
     * Retrieves the list of unapproved students.
     *
     * @return List of unapproved students.
     */
    List<Student> getUnapprovedStudents();

    /**
     * Adds a new course to the course catalog.
     *
     * @param course The course to be added.
     */
    void addCourseToCatalog(Course course);

    /**
     * Adds a new professor to the system.
     *
     * @param professor The professor to be added.
     */
    void addProfessor(Professor professor);

    /**
     * Approves a student for course registration.
     *
     * @param studentId The ID of the student to be approved.
     */
    void approveStudent(String studentId);

    /**
     * Deletes all students from the system.
     */
    void deleteAllStudents();

    /**
     * Finds a student by their username.
     *
     * @param username The username of the student.
     * @return The student if found, null otherwise.
     */
    Student findStudentByUsername(String username);

    /**
     * Deletes a professor by their ID.
     *
     * @param professorId The ID of the professor to be deleted.
     * @return true if the professor was successfully deleted, false otherwise.
     */
    boolean deleteProfessorByName(int professorId);

    /**
     * Deletes all professors from the system.
     *
     * @return true if all professors were successfully deleted, false otherwise.
     */
    boolean deleteAllProfessors();

    /**
     * Removes a course from the course catalog.
     *
     * @param courseName The name of the course to be removed.
     * @return true if the course was successfully removed, false otherwise.
     * @throws SQLException if a database access error occurs.
     */
    boolean removeCourse(String courseName) throws SQLException;

    /**
     * Retrieves the list of courses.
     *
     * @return List of courses.
     */
    List<Map<String, Object>> getCourses();

    /**
     * Retrieves the list of professors.
     *
     * @return List of professors.
     */
    List<Map<String, Object>> getProfessors();

    /**
     * Retrieves the assigned courses for a professor.
     *
     * @param professorId The ID of the professor.
     * @return The assigned courses as a string.
     */
    String getAssignedCourses(int professorId);

    /**
     * Updates the assigned courses for a professor.
     *
     * @param professorId The ID of the professor.
     * @param assignedCourses The new assigned courses as a string.
     * @return true if the assigned courses were successfully updated, false otherwise.
     */
    boolean updateAssignedCourses(int professorId, String assignedCourses);

    /**
     * Retrieves all professors from the system.
     *
     * @return List of all professors.
     * @throws SQLException if a database access error occurs.
     */
    List<Map<String, Object>> getAllProfessors() throws SQLException;

    /**
     * Sets the fee for a course.
     *
     * @param courseCode The code of the course.
     * @param courseFee The fee to be set for the course.
     * @return true if the fee was successfully set, false otherwise.
     */
    boolean setFeeForCourse(int courseCode, int courseFee);
}
