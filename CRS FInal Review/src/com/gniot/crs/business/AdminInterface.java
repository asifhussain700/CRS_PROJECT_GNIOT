/**
 * AdminInterface defines the operations that can be performed by an administrator
 * in the course registration system.
 */
package com.gniot.crs.business;

public interface AdminInterface {

    /**
     * Adds a new professor to the system.
     */
    void addProfessor();

    /**
     * Adds a new course to the course catalog.
     */
    void addCourseToCatalog();

    /**
     * Approves students for course registration.
     */
    void approveStudents();

    /**
     * Deletes all students from the system.
     */
    void deleteAllStudents();

    /**
     * Removes courses from the course catalog.
     */
    void removeCourses();

    /**
     * Deletes all professors from the system.
     */
    void deleteAllProfessors();

    /**
     * Deletes a professor from the system by their name.
     */
    void deleteProfessorByName();

    /**
     * Assigns a course to a professor.
     */
    void assignCourseToProfessor();

    /**
     * Removes a course assignment from a professor.
     */
    void removeCourseFromProfessor();

    /**
     * Sets the fee for the courses.
     */
    void setFeeForCourses();
}
