package com.gniot.crs.dao;

import com.gniot.crs.bean.Student;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

/**
 * ProfessorDAOInterface defines the data access operations that can be performed
 * by a professor in the course registration system.
 */
@Service
public interface ProfessorDAOInterface {

    /**
     * Retrieves the list of students enrolled in a specific course.
     *
     * @param courseCode The code of the course for which students are to be retrieved.
     * @return A list of maps where each map represents a student enrolled in the course.
     * @throws SQLException if a database access error occurs.
     */
    List<Map<String, Object>> getEnrolledStudents(int courseCode) throws SQLException;

    /**
     * Retrieves the list of courses assigned to a specific professor.
     *
     * @param professorId The ID of the professor for whom assigned courses are to be retrieved.
     * @return A list of maps where each map represents a course assigned to the professor.
     * @throws SQLException if a database access error occurs.
     */
    List<Map<String, Object>> getAssignedCourses(int professorId) throws SQLException;

    /**
     * Adds a grade for a student in a specific course.
     *
     * @param username The username of the student who is receiving the grade.
     * @param courseCode The code of the course in which the student is receiving the grade.
     * @param grade The grade to be assigned to the student.
     * @return true if the grade was successfully added, false otherwise.
     * @throws SQLException if a database access error occurs.
     */
    boolean addGrade(String username, int courseCode, String grade) throws SQLException;
}
