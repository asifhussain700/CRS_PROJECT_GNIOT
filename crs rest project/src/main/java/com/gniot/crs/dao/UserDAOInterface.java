package com.gniot.crs.dao;

import java.sql.SQLException;

import com.gniot.crs.bean.Student;
import com.gniot.crs.bean.User;

/**
 * UserDAOInterface defines the data access operations related to user management
 * in the course registration system.
 */
public interface UserDAOInterface {

    /**
     * Registers a new student in the system.
     *
     * @param student The student object containing details of the student to be registered.
     * @throws SQLException if a database access error occurs during registration.
     */
    public void register(Student student) throws SQLException;

    /**
     * Authenticates a user based on their username, password, and role.
     *
     * @param username The username of the user attempting to log in.
     * @param password The password of the user attempting to log in.
     * @param role The role of the user (e.g., student, professor) attempting to log in.
     * @throws Exception if authentication fails or an error occurs.
     */
    void login(String username, String password, String role) throws Exception;

    /**
     * Changes the password for a user.
     *
     * @param username The username of the user whose password is to be changed.
     * @param oldPassword The current password of the user.
     * @param newPassword The new password to be set for the user.
     * @param role The role of the user (e.g., student, professor) whose password is being changed.
     * @throws Exception if the password change fails or an error occurs.
     */
    public void changePassword(String username, String oldPassword, String newPassword, String role) throws Exception;
}
