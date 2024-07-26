/**
 * ProfessorInterface defines the operations that can be performed by a professor
 * in the course registration system.
 */
package com.gniot.crs.business;

import com.gniot.crs.bean.*;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

@Service
public interface ProfessorInterface {

    /**
     * Allows the professor to add a grade for a student in a specific course.
     *
     * @param professorId The ID of the professor adding the grade.
     * @param courseCode The code of the course for which the grade is being added.
     * @param username The username of the student receiving the grade.
     * @param grade The grade to be assigned to the student (e.g., A, B, C, etc.).
     * @return True if the grade was successfully added, false otherwise.
     * @throws SQLException If a database access error occurs.
     */
    boolean addGrade(int professorId, int courseCode, String username, String grade) throws SQLException;

    /**
     * Retrieves the list of courses assigned to a professor.
     *
     * @param professorId The ID of the professor whose assigned courses are being retrieved.
     * @return A list of maps where each map represents a course assigned to the professor. The map contains course details such as course code and name.
     * @throws Exception If an error occurs while fetching the assigned courses.
     */
    List<Map<String, Object>> getAssignedCourses(int professorId) throws Exception;

    /**
     * Retrieves the list of students enrolled in a specific course.
     *
     * @param courseCode The code of the course for which the enrolled students are being retrieved.
     * @return A list of maps where each map represents a student enrolled in the course. The map contains student details such as username.
     * @throws Exception If an error occurs while fetching the enrolled students.
     */
    List<Map<String, Object>> getEnrolledStudents(int courseCode) throws Exception;
}
