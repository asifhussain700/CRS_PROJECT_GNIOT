/**
 * 
 */
package com.gniot.crs.business;


import java.sql.SQLException;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import com.gniot.crs.bean.Course;
import com.gniot.crs.bean.Professor;
import com.gniot.crs.bean.Student;
import com.gniot.crs.dao.*;

/**
 * 
 */
public  class AdminOperation implements AdminInterface{

    private static Scanner scanner = new Scanner(System.in);
      private AdminDAOInterface adminDAO;
      
      public AdminOperation() {
    	  this.adminDAO = new AdminDAOImpl();
      }
      

      final static String YELLOW = "\u001B[33m";
      final static String GREEN = "\u001B[32m";
  	  final static String RESET = "\u001B[0m";
    

	// addprofessor
	public void addProfessor() {
		System.out.print("Enter Professor id: ");
        int professorid = Integer.parseInt(scanner.nextLine());
        System.out.print("Enter Professor DOJ: ");
        int professorDOJ = Integer.parseInt(scanner.nextLine());
        System.out.print("Enter Professor Department: ");
        String professorDepartment = scanner.nextLine();
        System.out.print("Enter Professor Designation: ");
        String professorDesignation = scanner.nextLine();
        System.out.print("Enter Professor Name: ");
        String professorName = scanner.nextLine();
        System.out.print("Enter Professor Email: ");
        String professorEmail = scanner.nextLine();
        System.out.print("Enter Professor Password: ");
        String professorPassword = scanner.nextLine();
        System.out.print("Enter role: ");
        String role = scanner.nextLine();
        
        Professor professor = new Professor(professorName, professorPassword, role);
        professor.setProfessorId(professorid);
        professor.setProfessorDOJ(professorDOJ);
        professor.setProfessorDepartment(professorDepartment);
        professor.setProfessorDesignation(professorDesignation);
        professor.setProfessorName(professorName);
        professor.setProfessorEmail(professorEmail);
        professor.setProfessorPassword(professorPassword);
        professor.setRole(role);
        
        adminDAO.addProfessor(professor);
        System.out.println(GREEN + "<---Professor " + professorName + " added successfully--->" + RESET);
	}

	

	@Override
    public void deleteProfessorByName() {
        System.out.println(YELLOW + "<----Delete Professor by ID---->" + RESET);
        try {
            List<Map<String, Object>> professors = adminDAO.getAllProfessors();
            if (professors.isEmpty()) {
                System.err.println("No professors found.");
                return;
            }

            // Display the list of professors in tabular form
            System.out.println(YELLOW + "List of Professors:" + RESET);
            
            System.out.println("-----------------------------");
            System.out.printf("%-10s | %-20s%n", "ID", "Name");
            System.out.println("-----------------------------");
            for (Map<String, Object> professor : professors) {
                System.out.println(String.format("%-10s | %-20s", 
                                                 professor.get("professorId"), 
                                                 professor.get("professorName")));
            }
            System.out.println("-----------------------------");

            // Prompt for the professor's name to delete
            System.out.print( "Enter the professor's ID to delete: ");
            int professorId = scanner.nextInt();
            scanner.nextLine();

            // Delete the professor
            boolean success = adminDAO.deleteProfessorByName(professorId);
            if (success) {
                System.out.println(GREEN + "<----Professor removed successfully.---->" + RESET);
            } else {
                System.err.println("Failed to delete professor.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
	 
	 @Override
	    public void deleteAllProfessors() {
	        System.out.println(YELLOW + "Deleting All Professors......." + RESET);
	        boolean success = adminDAO.deleteAllProfessors();
	        if (success) {
	            System.out.println(GREEN + "<----All professors deleted successfully.---->" + RESET);
	        } else {
	            System.err.println("Failed to delete all professors.");
	        }
	    }


		
	
	// Adding Course To Catalog
	 public void addCourseToCatalog() {
	     System.out.println(YELLOW + "<----Adding Course To Course Catalog---->" + RESET);

	     try {
	         // Input course details from user
	         System.out.print("Enter Course Code: ");
	         int courseCode = Integer.parseInt(scanner.nextLine());

	         System.out.print("Enter Course Name: ");
	         String courseName = scanner.nextLine();

	         System.out.print("Is offered (true/false): ");
	         String isOfferedStr = scanner.nextLine();
	         boolean isOffered = Boolean.parseBoolean(isOfferedStr);

	         System.out.print("Enter Course Type: ");
	         String courseType = scanner.nextLine();

	         // Create Course object with user-provided details
	         Course course = new Course(courseCode, courseName, courseType);
	         course.setCourseCode(courseCode);
	         course.setCourseName(courseName);
	         course.setisOffered(isOffered);
	         course.setCourseType(courseType);

	         // Call DAO method to add course to catalog
	         adminDAO.addCourseToCatalog(course);
	         System.out.println(GREEN + "<----Course Successfully Added To Catalog---->" + RESET);

	     } catch (NumberFormatException e) {
	         System.out.println("Error: Invalid input format. Please enter a valid number.");
	         // Optionally, log the exception or handle it as needed

	     }
	 }


	//Remove Course From Catalog
	
	@Override
	public void removeCourses() {
	    try {
	        List<Map<String, Object>> courses = adminDAO.getCourses();
	        if (courses.isEmpty()) {
	            System.out.println(YELLOW + "No courses found in the catalog." + RESET);
	            return;
	        }

	        // Display the course catalog
	        System.out.println(YELLOW + "<----Course Catalog:---->" + RESET);
	        System.out.println("-------------------------------|");
	        System.out.printf("%-15s | %-30s%n", "Course Code", "Course Name");
	        System.out.println("-------------------------------|");
	        for (Map<String, Object> course : courses) {
	            System.out.printf("%-15s | %-30s%n", 
	                              course.get("courseCode"), 
	                              course.get("courseName")
	                             );
	        }
	        System.out.println("------------------------------|");
	        // Prompt for the course name to remove
	        System.out.print("Enter the course name to remove: ");
	        String courseName = scanner.nextLine();

	        // Remove the course
	        boolean success = adminDAO.removeCourse(courseName);
	        if (success) {
	            System.out.println(GREEN + "<----Course removed successfully.---->" + RESET);
	        } else {
	            System.err.println("Failed to remove course. Please check the course name.");
	        }
	    } catch (SQLException e) {
	        System.err.println("SQL Exception generated: " + e.getMessage());
	    }
	}

	
	 public void deleteAllStudents() {
	        adminDAO.deleteAllStudents();
	    }
	 
	 
	 
	 public void approveStudents() {
		    List<Student> unapprovedStudents = adminDAO.getUnapprovedStudents();

		    if (unapprovedStudents.isEmpty()) {
		        System.out.println(YELLOW + "No students to approve." + RESET);
		        return;
		    }
                System.out.println(YELLOW +"<----Unapproved Students---->" + RESET);
		    // Print table header
		    System.out.println("------------------------------------------------------------------------");
		    System.out.printf("%-15s %-15s  %-15s %-15s %-15s%n", " | ", "Username", " | ", "Name", " | ");
		    System.out.println("------------------------------------------------------------------------");

		    // Print unapproved students
		    for (Student student : unapprovedStudents) {
		        System.out.printf("%-15s %-15s  %-15s  %-15s %-15s%n", " | ", student.getUsername(), " | ", student.getFirstName(), " | ");
		    }
		    System.out.println("------------------------------------------------------------------------");

		    System.out.print("Enter the username of the student to approve (or type 'all' to approve all): ");
		    String input = scanner.nextLine();

		    if (input.equalsIgnoreCase("all")) {
		        for (Student student : unapprovedStudents) {
		            adminDAO.approveStudent(student.getUsername());
		        }
		        System.out.println(GREEN + "<----All students approved successfully!---->" + RESET);
		    } else {
		        Student student = adminDAO.findStudentByUsername(input);
		        if (student != null && student.getRole().equalsIgnoreCase("student")) {
		            adminDAO.approveStudent(student.getUsername());
		            System.out.println(GREEN + "<----Student " + student.getUsername() + " approved successfully!---->" + RESET);
		        } else {
		            System.out.println(YELLOW + "Student not found or invalid role." + RESET);
		        }
		    }
		}

	 
	 @Override
	    public void assignCourseToProfessor() {
	        List<Map<String, Object>> courses = adminDAO.getCourses();
	        if (courses.isEmpty()) {
	            System.err.println(YELLOW + "<----No courses available.---->" + RESET);
	            return;
	        }
	        System.out.println(YELLOW +"<----Course Catalog:<---->" + RESET);
	        // Print table header
		    System.out.println("------------------------------------------------------------------------");
		    System.out.printf("%-15s %-15s  %-15s %-15s %-15s%n", " | ", "CourseCode", " | ", "CourseName", " | ");
		    System.out.println("------------------------------------------------------------------------");
	     
	     
	        for (Map<String, Object> course : courses) {
//	            System.out.println(course.get("courseCode") + " | " + course.get("courseName"));
	            System.out.printf("%-15s %-15s  %-15s  %-15s %-15s%n", " | ", course.get("courseCode"), " | ", course.get("courseName"), " | ");
	        }
	        System.out.println("------------------------------------------------------------------------");
	     
	        
	        System.out.print("Enter Course ID to assign: ");
	        int selectedCourseId = scanner.nextInt();
	        scanner.nextLine();

	        boolean validCourse = courses.stream().anyMatch(course -> course.get("courseCode").equals(selectedCourseId));
	        if (!validCourse) {
	            System.err.println("Invalid Course Code.");
	            return;
	        }

	        List<Map<String, Object>> professors = adminDAO.getProfessors();
	        if (professors.isEmpty()) {
	            System.out.println(YELLOW + "No professors available." + RESET);
	            return;
	        }

	        System.out.println(YELLOW + "<----Professor List---->" + RESET);
	     // Print table header
		    System.out.println("------------------------------------------------------------------------");
		    System.out.printf("%-15s %-15s  %-15s %-15s %-15s%n", " | ", "Professor ID", " | ", "Professor Name", " | ");
		    System.out.println("------------------------------------------------------------------------");
//	    
		    
	        for (Map<String, Object> professor : professors) {
	            
	            System.out.printf("%-15s %-15s  %-15s  %-15s %-15s%n", " | ", professor.get("professorId"), " | ", professor.get("professorName"), " | ");
	        }
	        System.out.println("------------------------------------------------------------------------");

	        System.out.print("Enter Professor ID to assign the course to: ");
	        int selectedProfessorId = scanner.nextInt();
	        scanner.nextLine();

	        boolean validProfessor = professors.stream().anyMatch(prof -> prof.get("professorId").equals(selectedProfessorId));
	        if (!validProfessor) {
	            System.err.println("Invalid Professor ID.");
	            return;
	        }

	        String assignedCourses = adminDAO.getAssignedCourses(selectedProfessorId);
	        if (assignedCourses == null || assignedCourses.isEmpty()) {
	            assignedCourses = String.valueOf(selectedCourseId);
	        } else {
	            assignedCourses += "," + selectedCourseId;
	        }

	        boolean success = adminDAO.updateAssignedCourses(selectedProfessorId, assignedCourses);
	        if (success) {
	            System.out.println(GREEN + "<----Course assigned successfully.---->" + RESET);
	        } else {
	            System.err.println("Failed to assign course. Please try again.");
	        }
	    }
	 
	 
	 @Override
	 public void removeCourseFromProfessor() {
	     // Display the list of professors
		 List<Map<String, Object>> professors = adminDAO.getProfessors();
		 if (professors.isEmpty()) {
		     System.out.println(YELLOW + "No professors found." + RESET);
		     return;
		 }

		 System.out.println(GREEN + "<----Professor List---->" + RESET);
		 System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------------");
		 System.out.printf("%-15s %-25s %-25s %-25s %-25s %-25s %-25s%n", " | ", "Professor ID", " | ", "Professor Name", " | ", "Assigned Courses", " | ");
		 System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------------");

		 for (Map<String, Object> professor : professors) {
		     System.out.printf("%-15s %-25s %-25s %-25s %-25s %-25s %-25s%n", " | ", professor.get("professorId"), " | ", professor.get("professorName"), " | ", professor.get("assignedCourses"), " | ");
		 }
		 System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------------");

		 // Prompt for the professor's ID
		 System.out.print("Enter the professor's ID to remove a course from: ");
		 int professorId = scanner.nextInt();
		 scanner.nextLine();

		 // Validate the professor ID
		 boolean validProfessor = professors.stream().anyMatch(prof -> prof.get("professorId").equals(professorId));
		 if (!validProfessor) {
		     System.err.println("Invalid Professor ID.");
		     return;
		 }

		 // Get and display the courses assigned to the selected professor
		 String assignedCourses = adminDAO.getAssignedCourses(professorId);
		 if (assignedCourses == null || assignedCourses.isEmpty()) {
		     System.out.println(YELLOW + "No courses assigned to this professor." + RESET);
		     return;
		 }

		 String[] courses = assignedCourses.split(",");
		 System.out.println(GREEN + "Courses assigned to Professor ID " + professorId + ":" + RESET);
		 for (String courseCode : courses) {
		     System.out.println(courseCode);
		 }

		 // Prompt for the course code to remove
		 System.out.print("Enter the course code to remove: ");
		 String courseCodeToRemove = scanner.nextLine();

		 // Validate the course code
		 boolean validCourse = false;
		 for (String courseCode : courses) {
		     if (courseCode.equals(courseCodeToRemove)) {
		         validCourse = true;
		         break;
		     }
		 }

		 if (!validCourse) {
		     System.err.println("Invalid Course Code.");
		     return;
		 }

		 // Remove the course from the professor's assigned courses
		 String updatedCourses = String.join(",", Arrays.stream(courses)
		                                                .filter(course -> !course.equals(courseCodeToRemove))
		                                                .toArray(String[]::new));

		 boolean success = adminDAO.updateAssignedCourses(professorId, updatedCourses);
		 if (success) {
		     System.out.println(GREEN + "<----Course removed successfully.---->" + RESET);
		 } else {
		     System.err.println("Failed to remove course. Please try again.");
		 }
	 }


	 @Override
	 public void setFeeForCourses() {
	     try {
	         System.out.println(YELLOW + "<---- Set Fee for Courses ---->" + RESET);

	         // Display the list of courses
	         List<Map<String, Object>> courses = adminDAO.getCourses();
	         if (courses.isEmpty()) {
	             System.out.println(YELLOW + "No courses available." + RESET);
	             return;
	         }

	         System.out.println(GREEN + "<----Course Catalog:---->" + RESET);
	         System.out.println("---------------------------------------------------------------");
	         System.out.println(String.format("%-15s | %-25s | %10s", "Course Code", "Course Name", "Current Fee"));
	         System.out.println("---------------------------------------------------------------");
	         for (Map<String, Object> course : courses) {
	             Object fee = course.get("courseFee");
	             if (fee instanceof Integer) {
	                 System.out.println(String.format("%-15s | %-25s | %10d", 
	                                                  course.get("courseCode"), 
	                                                  course.get("courseName"), 
	                                                  (Integer) fee));
	             } else if (fee instanceof Double) {
	                 System.out.println(String.format("%-15s | %-25s | %10.2f", 
	                                                  course.get("courseCode"), 
	                                                  course.get("courseName"), 
	                                                  (Double) fee));
	             }
	         }
	         System.out.println("---------------------------------------------------------------");

	         // Prompt for the course code
	         System.out.print("Enter the course code to set the fee for: ");
	         int courseCode = scanner.nextInt();
	         scanner.nextLine();

	         // Prompt for the new fee
	         System.out.print("Enter the new fee for the course: ");
	         int courseFee = scanner.nextInt();
	         scanner.nextLine();

	         // Set the new fee for the course
	         boolean success = adminDAO.setFeeForCourse(courseCode, courseFee);
	         if (success) {
	             System.out.println(GREEN + "<----Fee for course " + courseCode + " set to " + courseFee + " successfully---->" + RESET);
	         } else {
	             System.err.println("Failed to set the fee for the course. Please check if the course code is correct.");
	         }
	     } catch (InputMismatchException ex) {
	         System.err.println("Invalid input format. Please enter a valid course code and fee.");
	     } catch (Exception ex) {
	         System.err.println("An error occurred while setting the fee for the course: " + ex.getMessage());
	     }
	 }


}
