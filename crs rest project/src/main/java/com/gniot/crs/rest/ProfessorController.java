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
@RequestMapping("/professor")
public class ProfessorController {

    // Inject the ProfessorInterface to use its methods
    private final ProfessorInterface professorOperation;

    @Autowired
    public ProfessorController(ProfessorInterface professorOperation) {
        this.professorOperation = professorOperation;
    }

    /**
     * Endpoint to add a grade for a student.
     *
     * @param professorId The ID of the professor adding the grade.
     * @param courseCode  The code of the course for which the grade is being added.
     * @param username    The username of the student receiving the grade.
     * @param grade       The grade to be added.
     * @return A response indicating the success or failure of the grade addition.
     */
    @PostMapping(value = "/addGrade", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> addGrade(
            @RequestParam int professorId,
            @RequestParam int courseCode,
            @RequestParam String username,
            @RequestParam String grade) {

        // Validate grade
        if (!isValidGrade(grade)) {
            return new ResponseEntity<>("Invalid grade. Grade must be between A and F.", HttpStatus.BAD_REQUEST);
        }

        try {
            boolean success = professorOperation.addGrade(professorId, courseCode, username, grade);
            if (success) {
                return new ResponseEntity<>("Grade added successfully", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Failed to add grade. Please try again.", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (SQLException ex) {
            return new ResponseEntity<>("SQL Exception generated: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Endpoint to get the list of courses assigned to a professor.
     *
     * @param professorId The ID of the professor.
     * @return A response containing the list of assigned courses or indicating no content if none are found.
     */
    @GetMapping(value = "/getAssignedCourses", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Map<String, Object>>> getAssignedCourses(@RequestParam int professorId) {
        try {
            // Fetch assigned courses using the professor's ID
            List<Map<String, Object>> assignedCourses = professorOperation.getAssignedCourses(professorId);
            if (assignedCourses.isEmpty()) {
                // Return no content if no courses are assigned
                return ResponseEntity.noContent().build();
            } else {
                // Return the list of assigned courses
                return ResponseEntity.ok(assignedCourses);
            }
        } catch (Exception ex) {
            // Return a 500 status if an exception occurs
            return ResponseEntity.status(500).body(null);
        }
    }

    /**
     * Endpoint to get the list of students enrolled in a specific course.
     *
     * @param courseCode The code of the course.
     * @return A response containing the list of enrolled students or indicating no content if none are found.
     */
    @GetMapping(value = "/getEnrolledStudents", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Map<String, Object>>> getEnrolledStudents(@RequestParam int courseCode) {
        try {
            // Fetch enrolled students using the course code
            List<Map<String, Object>> enrolledStudents = professorOperation.getEnrolledStudents(courseCode);
            if (enrolledStudents.isEmpty()) {
                // Return no content if no students are enrolled
                return ResponseEntity.noContent().build();
            } else {
                // Return the list of enrolled students
                return ResponseEntity.ok(enrolledStudents);
            }
        } catch (Exception ex) {
            // Return a 500 status if an exception occurs
            return ResponseEntity.status(500).body(null);
        }
    }

    /**
     * Method to validate the grade.
     *
     * @param grade The grade to be validated.
     * @return True if the grade is valid (between A and F), false otherwise.
     */
    private boolean isValidGrade(String grade) {
        // Assuming grades are A to F
        return grade.matches("[A-F]");
    }
}
