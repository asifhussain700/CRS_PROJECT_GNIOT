/**
 * StudentInterface defines the operations that can be performed by a student
 * in the course registration system.
 */
package com.gniot.crs.business;

public interface StudentInterface {

    /**
     * Allows the student to browse the course catalog.
     */
    void browseCatalog();

    /**
     * Allows the student to drop a course they are registered for.
     * 
     * @return true if the course was successfully dropped, false otherwise.
     */
    boolean dropCourse();

    /**
     * Allows the student to register for a course.
     */
    void registerForCourse();

    /**
     * Allows the student to view their account information.
     */
    void accountInfo();

    /**
     * Allows the student to view their grades.
     */
    void viewGrades();
}
