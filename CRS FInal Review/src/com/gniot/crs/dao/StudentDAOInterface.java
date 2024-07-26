package com.gniot.crs.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.gniot.crs.bean.Course;
import com.gniot.crs.bean.Student;

/**
 * StudentDAOInterface defines the data access operations that can be performed
 * by a student in the course registration system.
 */
public interface StudentDAOInterface {

    /**
     * Adds a new student to the database.
     *
     * @param student the student to be added
     */
    void addStudent(Student student);

    /**
     * Finds a student by username and password.
     *
     * @param username the username of the student
     * @param password the password of the student
     * @return a String indicating the result of the search (e.g., "Student found" or "Student not found")
     */
    String findStudentByUsername(String username, String password);

    /**
     * Updates the password of a student.
     *
     * @param username the username of the student
     * @param oldPassword the current password of the student
     * @param newPassword the new password to be set
     * @return true if the password was updated successfully, false otherwise
     */
    Boolean updateStudentPassword(String username, String oldPassword, String newPassword);

    /**
     * Checks if a username exists in the database.
     *
     * @param username the username to be checked
     * @return "Student already exists" if the username exists, "Student does not exist" otherwise
     */
    String checkUserName(String username);

    /**
     * Retrieves the list of courses available for students.
     *
     * @return a list of Course objects representing the available courses
     */
    List<Course> getCourses();

    /**
     * Registers a student for a specific course.
     *
     * @param username the username of the student
     * @param courseCode the code of the course to be registered
     * @param courseType the type of the course
     * @return true if the registration was successful, false otherwise
     */
    boolean registerForCourse(String username, int courseCode, String courseType);

//    /**
//     * Retrieves the grades of a student.
//     *
//     * @param username the username of the student
//     * @return a list of Grade objects representing the student's grades
//     */
//    List<Grade> getGrades(String username);

    /**
     * Drops a registered course for a student.
     *
     * @param username the username of the student
     * @param courseCode the code of the course to be dropped
     * @return true if the course was dropped successfully, false otherwise
     */
    boolean dropCourse(String username, int courseCode);

    /**
     * Retrieves the courses with grades for a student.
     *
     * @param username the username of the student
     * @return a list of maps, where each map represents a course and its associated grade for the student
     * @throws SQLException if there is a database access error
     */
    List<Map<String, Object>> getCoursesWithGrades(String username) throws SQLException;

    /**
     * Retrieves the list of courses a student is registered for.
     *
     * @param username the username of the student
     * @return a list of maps, where each map represents a registered course for the student
     * @throws SQLException if there is a database access error
     */
    List<Map<String, Object>> getRegisteredCourses(String username) throws SQLException;

    /**
     * Retrieves the information of a student.
     *
     * @param username the username of the student
     * @return a Student object containing the student's information
     * @throws SQLException if there is a database access error
     */
    Student getStudentInfo(String username) throws SQLException;

    /**
     * Retrieves the fee for a specific course.
     *
     * @param courseCode the code of the course
     * @return the fee for the course
     * @throws SQLException if there is a database access error
     */
    int getCourseFee(int courseCode) throws SQLException;

    /**
     * Updates the balance of a student.
     *
     * @param username the username of the student
     * @param totalCourseFee the total fee for the courses
     * @throws SQLException if there is a database access error
     */
    void updateStudentBalance(String username, int totalCourseFee) throws SQLException;
}
