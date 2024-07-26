package com.gniot.crs.rest;

import com.gniot.crs.bean.*;
import com.gniot.crs.business.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/student")
public class StudentController {

    private final StudentInterface studentOperation;

    @Autowired
    public StudentController(StudentInterface studentOperation) {
        this.studentOperation = studentOperation;
    }

    /**
     * Endpoint to browse the course catalog.
     * @return A list of available courses.
     */
    @GetMapping(value = "/browseCatalog", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Course>> browseCatalog() {
        try {
            List<Course> courses = studentOperation.browseCatalog();
            if (courses == null || courses.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT); // No content if no courses are found
            }
            return new ResponseEntity<>(courses, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR); // Internal server error if an exception occurs
        }
    }

    /**
     * Endpoint to drop a course for a student.
     * @param username The username of the student.
     * @param courseCode The course code of the course to be dropped.
     * @return A response indicating the success or failure of the operation.
     */
    @DeleteMapping(value = "/dropCourse", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> dropCourse(@RequestParam String username, @RequestParam int courseCode) {
        try {
            boolean success = studentOperation.dropCourse(username, courseCode);
            if (success) {
                return ResponseEntity.ok("Successfully dropped the course with code: " + courseCode);
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to drop the course. Please check if the course code is correct.");
            }
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while dropping the course: " + ex.getMessage());
        }
    }

    

    /**
     * Endpoint to view the grades of a student.
     * @param username The username of the student.
     * @return A list of grades or an error message if the retrieval fails.
     */
    @GetMapping(value = "/viewGrades", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> viewGrades(@RequestParam String username) {
        try {
            List<Map<String, Object>> grades = studentOperation.viewGrades(username);
            if (grades.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("You are not registered in any courses.");
            }
            return ResponseEntity.ok(grades);
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to retrieve grades: " + ex.getMessage());
        }
    }

    /**
     * Endpoint to get account information of a student.
     * @param username The username of the student.
     * @return The account information of the student or an error message if the retrieval fails.
     */
    @GetMapping(value = "/accountInfo", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> accountInfo(@RequestParam String username) {
        try {
            Student student = studentOperation.accountInfo(username);
            if (student != null) {
                return ResponseEntity.ok(student);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Student not found.");
            }
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to retrieve student information: " + ex.getMessage());
        }
    }
    
    /**
     * Endpoint to register a student for primary and alternate courses.
     * @param username The username of the student.
     * @param primaryCourseCodes A list of primary course codes.
     * @param alternateCourseCodes A list of alternate course codes.
     * @return A response indicating the result of the registration process.
     */
    @PostMapping(value = "/registerForCourse", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> registerForCourse(@RequestParam String username,
                                                    @RequestParam List<Integer> primaryCourseCodes,
                                                    @RequestParam List<Integer> alternateCourseCodes) {
        String result = studentOperation.registerForCourse(username, primaryCourseCodes, alternateCourseCodes);
        return ResponseEntity.ok(result);
    }
}
