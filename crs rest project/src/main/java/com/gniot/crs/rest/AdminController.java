package com.gniot.crs.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.gniot.crs.business.*;
import com.gniot.crs.bean.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    // Dependency injection of the AdminInterface implementation
    private final AdminInterface adminOperation;

    @Autowired
    public AdminController(AdminInterface adminOperation) {
        this.adminOperation = adminOperation;
    }

    /**
     * Endpoint to add a new professor.
     * 
     * @param professor The professor to be added.
     * @return A response indicating the success or failure of the addition.
     */
    @PostMapping(value = "/addProfessor", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> addProfessor(@RequestBody Professor professor) {
        try {
            adminOperation.addProfessor(professor);
            return new ResponseEntity<>("Professor added successfully", HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to add professor: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Endpoint to delete all professors.
     * 
     * @return A response indicating the success or failure of the deletion.
     */
    @DeleteMapping(value = "/deleteAllProfessors", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> deleteAllProfessors() {
        try {
            boolean success = adminOperation.deleteAllProfessors();
            if (success) {
                return new ResponseEntity<>("All professors deleted successfully", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Failed to delete all professors", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to delete all professors: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Endpoint to delete a professor by ID.
     * 
     * @param professorId The ID of the professor to be deleted.
     * @return A response indicating the success or failure of the deletion.
     */
    @DeleteMapping(value = "/deleteProfessorById", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> deleteProfessorById(@RequestParam int professorId) {
        try {
            adminOperation.deleteProfessorByName(professorId);
            return new ResponseEntity<>("Professor removed successfully", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to delete professor: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Endpoint to add a course to the catalog.
     * 
     * @param course The course to be added.
     * @return A response indicating the success or failure of the addition.
     */
    @PostMapping(value = "/addCourseToCatalog", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> addCourseToCatalog(@RequestBody Course course) {
        try {
            adminOperation.addCourseToCatalog(course);
            return new ResponseEntity<>("Course added successfully to catalog", HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to add course to catalog: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Endpoint to remove a course by name.
     * 
     * @param courseName The name of the course to be removed.
     * @return A response indicating the success or failure of the removal.
     */
    @DeleteMapping(value = "/removeCourse", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> removeCourse(@RequestParam String courseName) {
        try {
            boolean success = adminOperation.removeCourses(courseName);
            if (success) {
                return new ResponseEntity<>("Course removed successfully", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Failed to remove course. Please check the course name.", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to remove course: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Endpoint to approve a list of students.
     * 
     * @param usernames The list of usernames to be approved.
     * @return A response indicating the success or failure of the approval.
     */
    @PostMapping(value = "/approveStudents", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> approveStudents(@RequestBody List<String> usernames) {
        try {
            adminOperation.approveStudents(usernames);
            return new ResponseEntity<>("Students approved successfully", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to approve students: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Endpoint to delete all students.
     * 
     * @return A response indicating the success or failure of the deletion.
     */
    @DeleteMapping(value = "/deleteAllStudents", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> deleteAllStudents() {
        try {
            adminOperation.deleteAllStudents();
            return new ResponseEntity<>("All students deleted successfully", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to delete all students: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Endpoint to assign a course to a professor.
     * 
     * @param courseId    The ID of the course to be assigned.
     * @param professorId The ID of the professor to whom the course will be assigned.
     * @return A response indicating the success or failure of the assignment.
     */
    @PostMapping(value = "/assignCourseToProfessor", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> assignCourseToProfessor(@RequestParam int courseId, @RequestParam int professorId) {
        try {
            adminOperation.assignCourseToProfessor(courseId, professorId);
            return new ResponseEntity<>("Course assigned successfully", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to assign course: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Endpoint to remove a course from a professor.
     * 
     * @param professorId The ID of the professor.
     * @param courseCode  The code of the course to be removed.
     * @return A response indicating the success or failure of the removal.
     */
    @DeleteMapping(value = "/removeCourseFromProfessor", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> removeCourseFromProfessor(@RequestParam int professorId, @RequestParam String courseCode) {
        try {
            adminOperation.removeCourseFromProfessor(professorId, courseCode);
            return new ResponseEntity<>("Course removed successfully", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to remove course: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Endpoint to set the fee for a course.
     * 
     * @param courseCode The code of the course.
     * @param fee        The fee to be set for the course.
     * @return A response indicating the success or failure of the fee setting.
     */
    @PutMapping(value = "/setFeeForCourse", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> setFeeForCourse(@RequestParam int courseCode, @RequestParam int fee) {
        try {
            adminOperation.setFeeForCourses(courseCode, fee);
            return new ResponseEntity<>("Fee for course " + courseCode + " set successfully", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to set fee: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
