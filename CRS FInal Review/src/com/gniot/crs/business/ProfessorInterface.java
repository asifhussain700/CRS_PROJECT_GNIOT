/**
 * ProfessorInterface defines the operations that can be performed by a professor
 * in the course registration system.
 */
package com.gniot.crs.business;

public interface ProfessorInterface {

    /**
     * Allows the professor to add a grade for a student.
     */
    void addGrade();

    /**
     * Allows the professor to view the list of students enrolled in their courses.
     */
    void viewEnrolledStudents();
}
