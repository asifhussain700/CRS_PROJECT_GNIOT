package com.gniot.crs.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * ProfessorDAOInterface defines the data access operations that can be performed
 * by a professor in the course registration system.
 */
public interface ProfessorDAOInterface {

    /**
     * Retrieves the list of students enrolled in a specific course.
     *
     * @param courseCode the code of the course
     * @return a list of maps, where each map represents an enrolled student
     * @throws SQLException if there is a database access error
     */
    List<Map<String, Object>> getEnrolledStudents(int courseCode) throws SQLException;

    /**
     * Retrieves the list of courses assigned to a specific professor.
     *
     * @param professorId the ID of the professor
     * @return a list of maps, where each map represents an assigned course
     * @throws SQLException if there is a database access error
     */
    List<Map<String, Object>> getAssignedCourses(int professorId) throws SQLException;

    /**
     * Adds a grade for a student in a specific course.
     *
     * @param username the username of the student
     * @param courseCode the code of the course
     * @param grade the grade to be assigned
     * @return true if the grade was successfully added, false otherwise
     * @throws SQLException if there is a database access error
     */
    boolean addGrade(String username, int courseCode, String grade) throws SQLException;
}
