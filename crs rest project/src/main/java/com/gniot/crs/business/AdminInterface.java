/**
 * AdminInterface defines the operations that can be performed by an administrator
 * in the course registration system.
 */
package com.gniot.crs.business;

import java.util.List;

import org.springframework.stereotype.Service;

import com.gniot.crs.bean.Course;
import com.gniot.crs.bean.Professor;

@Service
public interface AdminInterface {

    /**
     * Adds a new professor to the system.
     *
     * @param professor The professor object containing details of the new professor.
     */
    void addProfessor(Professor professor);

    /**
     * Adds a new course to the course catalog.
     *
     * @param course The course object containing details of the new course.
     */
    void addCourseToCatalog(Course course);

    /**
     * Approves students for course registration.
     *
     * @param usernames A list of usernames of the students to be approved.
     */
    void approveStudents(List<String> usernames);

    /**
     * Deletes all students from the system.
     */
    void deleteAllStudents();

    /**
     * Removes courses from the course catalog.
     *
     * @param courseName The name of the course to be removed.
     * @return True if the course was successfully removed, false otherwise.
     */
    boolean removeCourses(String courseName);

    /**
     * Deletes all professors from the system.
     *
     * @return True if all professors were successfully deleted, false otherwise.
     */
    boolean deleteAllProfessors();

    /**
     * Deletes a professor from the system by their ID.
     *
     * @param professorId The ID of the professor to be deleted.
     */
    void deleteProfessorByName(int professorId);

    /**
     * Assigns a course to a professor.
     *
     * @param courseId The ID of the course to be assigned.
     * @param professorId The ID of the professor to whom the course will be assigned.
     */
    void assignCourseToProfessor(int courseId, int professorId);

    /**
     * Removes a course assignment from a professor.
     *
     * @param professorId The ID of the professor.
     * @param courseCode The code of the course to be removed.
     */
    void removeCourseFromProfessor(int professorId, String courseCode);

    /**
     * Sets the fee for a course.
     *
     * @param courseCode The code of the course.
     * @param fee The fee to be set for the course.
     */
    void setFeeForCourses(int courseCode, int fee);
}
