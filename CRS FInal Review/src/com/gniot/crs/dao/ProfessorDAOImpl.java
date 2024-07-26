package com.gniot.crs.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.gniot.crs.utils.DBUtils;

public class ProfessorDAOImpl implements ProfessorDAOInterface {

	@Override
	public List<Map<String, Object>> getEnrolledStudents(int courseCode) throws SQLException {
	    List<Map<String, Object>> enrolledStudents = new ArrayList<>();
	    try (Connection conn = DBUtils.getConnection();
	         PreparedStatement stmt = conn.prepareStatement("SELECT s.username, s.id FROM src_schema.student_courses sc " +
	                                                         "JOIN src_schema.students s ON sc.username = s.username " +
	                                                         "WHERE sc.courseCode = ?")) {

	        stmt.setInt(1, courseCode);
	        try (ResultSet rs = stmt.executeQuery()) {
	            while (rs.next()) {
	                Map<String, Object> student = new HashMap<>();
	                student.put("username", rs.getString("username"));
	                student.put("id", rs.getInt("id"));
	                enrolledStudents.add(student);
	            }
	        }
	    }
	    return enrolledStudents;
	}



	@Override
	public List<Map<String, Object>> getAssignedCourses(int professorId) throws SQLException {
	    List<Map<String, Object>> assignedCourses = new ArrayList<>();
	    try (Connection conn = DBUtils.getConnection();
	         PreparedStatement stmt = conn.prepareStatement("SELECT assignedCourses FROM src_schema.professors WHERE professorId = ?")) {

	        stmt.setInt(1, professorId);
	        try (ResultSet rs = stmt.executeQuery()) {
	            if (rs.next()) {
	                String assignedCoursesString = rs.getString("assignedCourses");
	                if (assignedCoursesString != null && !assignedCoursesString.isEmpty()) {
	                    String[] assignedCourseCodes = assignedCoursesString.split(","); // Splitting the assigned courses string
	                    for (String courseCode : assignedCourseCodes) {
	                        Map<String, Object> course = new HashMap<>();
	                        course.put("courseCode", courseCode);
	                        course.put("courseName", getCourseNameByCode(courseCode)); // Get course name from courses table using course code
	                        assignedCourses.add(course);
	                    }
	                }
	            }
	        }
	    }
	    return assignedCourses;
	}

    
 // Helper method to retrieve course name by course code
    private String getCourseNameByCode(String courseCode) throws SQLException {
        try (Connection conn = DBUtils.getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT courseName FROM src_schema.courses WHERE courseCode = ?")) {

            stmt.setString(1, courseCode);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("courseName");
                }
            }
        }
        return null;
    }
    
    
    @Override
    public boolean addGrade(String username, int courseCode, String grade) throws SQLException {
        try (Connection conn = DBUtils.getConnection();
             PreparedStatement stmt = conn.prepareStatement("UPDATE src_schema.student_courses " +
                     "SET grade = ? " +
                     "WHERE username = ? " +
                     "AND courseCode = ?")) {

            stmt.setString(1, grade);
            stmt.setString(2, username);
            stmt.setInt(3, courseCode);
            int rowsUpdated = stmt.executeUpdate();
            return rowsUpdated > 0;
        }
    }

}
