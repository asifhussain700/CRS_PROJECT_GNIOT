/**
 * StudentInterface defines the operations that can be performed by a student
 * in the course registration system.
 */
package com.gniot.crs.business;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.gniot.crs.bean.Course;
import com.gniot.crs.bean.Student;

@Service
public interface StudentInterface {

    /**
     * Allows the student to browse the course catalog and view available courses.
     *
     * @return A list of courses available in the catalog.
     */
    List<Course> browseCatalog();

    /**
     * Allows the student to drop a course they are currently registered for.
     *
     * @param username The username of the student who wants to drop the course.
     * @param courseCode The code of the course to be dropped.
     * @return True if the course was successfully dropped, false otherwise.
     * @throws Exception If an error occurs while dropping the course.
     */
    boolean dropCourse(String username, int courseCode) throws Exception;

    /**
     * Allows the student to register for courses. The student can specify primary and alternate course codes.
     *
     * @param username The username of the student registering for the courses.
     * @param primaryCourseCodes A list of primary course codes the student wants to register for.
     * @param alternateCourseCodes A list of alternate course codes to be considered if primary choices are unavailable.
     * @return A message indicating the result of the registration attempt.
     */
    String registerForCourse(String username, List<Integer> primaryCourseCodes, List<Integer> alternateCourseCodes);

    /**
     * Allows the student to view their account information.
     *
     * @param username The username of the student whose account information is to be retrieved.
     * @return A Student object containing the student's account details.
     * @throws Exception If an error occurs while retrieving account information.
     */
    Student accountInfo(String username) throws Exception;

    /**
     * Allows the student to view their grades for enrolled courses.
     *
     * @param username The username of the student whose grades are to be viewed.
     * @return A list of maps where each map contains details of grades for the student's enrolled courses.
     * @throws Exception If an error occurs while retrieving grades.
     */
    List<Map<String, Object>> viewGrades(String username) throws Exception;
}
