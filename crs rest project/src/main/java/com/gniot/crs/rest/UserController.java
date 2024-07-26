package com.gniot.crs.rest;

import com.gniot.crs.bean.Student;
import com.gniot.crs.bean.User;
import com.gniot.crs.business.UserInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {
    private final UserInterface userService;

    @Autowired
    public UserController(UserInterface userService) {
        this.userService = userService;
    }

    /**
     * Endpoint for registering a new student.
     * @param student The student object containing registration details.
     * @return A response indicating the success or failure of the registration process.
     */
    @PostMapping(value = "/register", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> register(@RequestBody Student student) {
        try {
            userService.register(student);
            return ResponseEntity.ok("Registration successful! Please wait for admin approval.");
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
    }

    /**
     * Endpoint for user login.
     * @param username The username of the user.
     * @param password The password of the user.
     * @param role The role of the user (e.g., student, professor).
     * @return A response indicating the success or failure of the login attempt.
     */
    @PostMapping(value = "/login", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> login(@RequestParam String username,
                                         @RequestParam String password,
                                         @RequestParam String role) {
        try {
            userService.login(username, password, role);
            return ResponseEntity.ok("Login successful");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }

    /**
     * Endpoint for changing the user's password.
     * @param username The username of the user.
     * @param oldPassword The current password of the user.
     * @param newPassword The new password for the user.
     * @param role The role of the user (e.g., student, professor).
     * @return A response indicating the success or failure of the password change attempt.
     */
    @PostMapping(value = "/changePassword", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> changePassword(@RequestParam String username,
                                                 @RequestParam String oldPassword,
                                                 @RequestParam String newPassword,
                                                 @RequestParam String role) {
        try {
            userService.changePassword(username, oldPassword, newPassword, role);
            return ResponseEntity.ok("Password changed successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
