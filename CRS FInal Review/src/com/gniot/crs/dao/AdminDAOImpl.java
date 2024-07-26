package com.gniot.crs.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.gniot.crs.bean.Course;
import com.gniot.crs.bean.Professor;
import com.gniot.crs.bean.Student;
import com.gniot.crs.utils.DBUtils;

public  class AdminDAOImpl implements AdminDAOInterface {
    
    private static final String INSERT_COURSE_SQL = "INSERT INTO courses (courseCode, courseName,  isOffered, courseType) VALUES (?, ?, ?, ?)";
    private static final String SELECT_UNAPPROVED_STUDENTS_SQL = "SELECT * FROM students WHERE approved = 0";
    private static final String APPROVE_STUDENT_SQL = "UPDATE students SET approved = 1 WHERE username = ?";

    private static final String INSERT_PROFESSOR_SQL = "INSERT INTO professors (professorID, professorDOJ, professorDepartment, professorDesignation, professorName, professorEmail, professorPassword) VALUES (?, ?, ?, ?, ?, ?, ?)";
    final static String YELLOW = "\u001B[33m";
    final static String GREEN = "\u001B[32m";
    final static String RESET = "\u001B[0m";
    
    
    
    @Override
    public void addCourseToCatalog(Course course) {
        try (Connection connection = DBUtils.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_COURSE_SQL)) {

            preparedStatement.setInt(1, course.getCourseCode());
            preparedStatement.setString(2, course.getCourseName());
            preparedStatement.setBoolean(3, course.getisOffered());
            preparedStatement.setString(4, course.getCourseType());

            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println(GREEN + "Course added successfully to catalog." + RESET);
            } else {
                System.out.println("Failed to add course to catalog. No rows affected.");
            }

        } catch (SQLException ex) {
            System.out.println("SQL Exception: " + ex.getMessage());
            // Consider logging the exception or rethrowing a custom exception
        }
    }

    
    
    @Override
    public boolean removeCourse(String courseName) throws SQLException {
        Connection conn = null;
        PreparedStatement deleteStudentCoursesStmt = null;
        PreparedStatement deleteCourseStmt = null;
        try {
            conn = DBUtils.getConnection();
            conn.setAutoCommit(false);

            // Find course code for the given course name
            int courseCode = getCourseCodeByName(conn, courseName);

            // Delete related rows in student_courses
            deleteStudentCoursesStmt = conn.prepareStatement("DELETE FROM src_schema.student_courses WHERE courseCode = ?");
            deleteStudentCoursesStmt.setInt(1, courseCode);
            deleteStudentCoursesStmt.executeUpdate();

            // Delete the course from courses table
            deleteCourseStmt = conn.prepareStatement("DELETE FROM src_schema.courses WHERE courseName = ?");
            deleteCourseStmt.setString(1, courseName);
            int rowsAffected = deleteCourseStmt.executeUpdate();

            conn.commit();
            return rowsAffected > 0;
        } catch (SQLException e) {
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException ex) {
                    System.out.println("SQL Exception generated: " + ex.getMessage());
                }
            }
            throw e;
        } finally {
            if (deleteStudentCoursesStmt != null) {
                deleteStudentCoursesStmt.close();
            }
            if (deleteCourseStmt != null) {
                deleteCourseStmt.close();
            }
            if (conn != null) {
                conn.setAutoCommit(true);
                conn.close();
            }
        }
    }

    
    @Override
    public List<Student> getUnapprovedStudents() {
   List<Student> unapprovedStudents = new ArrayList<>();

        // SQL query to fetch unapproved students
        String sql = "SELECT * FROM students WHERE approved = 0";

        try (Connection connection = DBUtils.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(SELECT_UNAPPROVED_STUDENTS_SQL);
             ResultSet rs = preparedStatement.executeQuery()) {

            while (rs.next()) {
                // Create a Student object for each unapproved student
                Student student = new Student(sql, sql, sql, sql, sql, sql, 0, 0, 0, sql, sql, sql);
                student.setUsername(rs.getString("username"));
                student.setFirstName(rs.getString("firstName"));
                student.setLastName(rs.getString("lastName"));
                // Set other properties as needed

                // Add the student to the list of unapproved students
                unapprovedStudents.add(student);
            }
        } catch (SQLException ex) {
        	System.out.println("SQL Exception generated" +ex.getMessage());
            // Handle SQLException as needed
        }

        return unapprovedStudents;
    }
    
    
    @Override
    public void approveStudent(String studentId) {
        try (Connection connection = DBUtils.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(APPROVE_STUDENT_SQL)) {
            preparedStatement.setString(1, studentId);
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
        	System.out.println("SQL Exception generated" +ex.getMessage());
        }
    }

    @Override
    public Student findStudentByUsername(String username) {
        Student student = null;
        String sql = "SELECT * FROM students WHERE username = ?";
        try (Connection connection = DBUtils.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, username);
            try (ResultSet rs = preparedStatement.executeQuery()) {
                if (rs.next()) {
                    student = new Student(sql, sql, sql, sql, sql, sql, 0, 0, 0, sql, sql, sql);
                    student.setUsername(rs.getString("username"));
                    student.setFirstName(rs.getString("firstName"));
                    student.setLastName(rs.getString("lastName"));
                    student.setRole(rs.getString("role"));
                    // Set other properties as needed
                }
            }
        } catch (SQLException ex) {
        	System.out.println("SQL Exception generated" +ex.getMessage());
        }
        return student;
    }
    
    
    
    
    
    @Override
    public List<Map<String, Object>> getAllProfessors() throws SQLException {
        List<Map<String, Object>> professors = new ArrayList<>();
        try (Connection conn = DBUtils.getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT professorId, professorName FROM src_schema.professors")) {
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Map<String, Object> professor = new HashMap<>();
                    professor.put("professorId", rs.getInt("professorId"));
                    professor.put("professorName", rs.getString("professorName"));
                    professors.add(professor);
                }
            }
        }
        return professors;
    }
    @Override
    public void deleteAllStudents() {
        String deleteStudentCoursesQuery = "DELETE FROM src_schema.student_courses";
        String deleteStudentsQuery = "DELETE FROM src_schema.students";
        
        try (Connection connection = DBUtils.getConnection();
             PreparedStatement deleteStudentCoursesStmt = connection.prepareStatement(deleteStudentCoursesQuery);
             PreparedStatement deleteStudentsStmt = connection.prepareStatement(deleteStudentsQuery)) {
            
            // Start transaction
            connection.setAutoCommit(false);

            // Delete from student_courses
            int studentCoursesRowsDeleted = deleteStudentCoursesStmt.executeUpdate();
            System.out.println(GREEN + studentCoursesRowsDeleted + " rows deleted from student_courses." + RESET);

            // Delete from students
            int studentsRowsDeleted = deleteStudentsStmt.executeUpdate();
            System.out.println(GREEN + studentsRowsDeleted + " students deleted successfully."+ RESET);
            
            // Commit transaction
            connection.commit();
            
        } catch (SQLException ex) {
            System.err.println("SQL Exception generated: " + ex.getMessage());
}
        }
    


    
    @Override
    public void addProfessor(Professor professor) {
        try (Connection connection = DBUtils.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_PROFESSOR_SQL)) {
            preparedStatement.setInt(1, professor.getProfessorId());
            preparedStatement.setInt(2, professor.getProfessorDOJ());
            preparedStatement.setString(3, professor.getProfessorDepartment());
            preparedStatement.setString(4, professor.getprofessorDesignation());
            preparedStatement.setString(5, professor.getProfessorName());
            preparedStatement.setString(6, professor.getProfessorEmail());
            preparedStatement.setString(7, professor.getProfessorPassword());
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
        	System.out.println("SQL Exception generated" +ex.getMessage());
        }
    }
	

    @Override
    public boolean deleteProfessorByName(int professorId) {
        String sql = "DELETE FROM professors WHERE professorId = ?";

        try (Connection connection = DBUtils.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, professorId);
            int rowsDeleted = preparedStatement.executeUpdate();
            return rowsDeleted > 0;

        } catch (SQLException ex) {
        	System.out.println("SQL Exception generated" +ex.getMessage());
            return false;
        }
    }

    @Override
    public boolean deleteAllProfessors() {
        String sql = "DELETE FROM professors";

        try (Connection connection = DBUtils.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            int rowsDeleted = preparedStatement.executeUpdate();
            return rowsDeleted > 0;

        } catch (SQLException ex) {
        	System.out.println("SQL Exception generated" +ex.getMessage());
            return false;
        }
    }
    
    
    
    @Override
    public List<Map<String, Object>> getCourses() {
        List<Map<String, Object>> courses = new ArrayList<>();
        try (Connection conn = DBUtils.getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT courseCode, courseName, courseFee FROM src_schema.courses");
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Map<String, Object> course = new HashMap<>();
                course.put("courseCode", rs.getInt("courseCode"));
                course.put("courseName", rs.getString("courseName"));
                course.put("courseFee", rs.getInt("courseFee"));
                courses.add(course);
            }
        } catch (SQLException ex) {
        	System.out.println("SQL Exception generated" +ex.getMessage());
        }
        return courses;
    }
    private int getCourseCodeByName(Connection conn, String courseName) throws SQLException {
        PreparedStatement stmt = null;
        try {
            stmt = conn.prepareStatement("SELECT courseCode FROM src_schema.courses WHERE courseName = ?");
            stmt.setString(1, courseName);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("courseCode");
            } else {
                throw new SQLException("Course not found: " + courseName);
            }
        } finally {
            if (stmt != null) {
                stmt.close();
            }
        }
    }

    @Override
    public List<Map<String, Object>> getProfessors() {
        List<Map<String, Object>> professors = new ArrayList<>();
        try (Connection conn = DBUtils.getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT professorId, assignedCourses, professorName FROM src_schema.professors");
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Map<String, Object> professor = new HashMap<>();
                professor.put("professorId", rs.getInt("professorId"));
                professor.put("professorName", rs.getString("professorName"));
                professor.put("assignedCourses", rs.getString("assignedCourses"));
                professors.add(professor);
            }
        } catch (SQLException ex) {
        	System.out.println("SQL Exception generated" +ex.getMessage());
        }
        return professors;
    }

    @Override
    public String getAssignedCourses(int professorId) {
        String assignedCourses = "";
        try (Connection conn = DBUtils.getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT assignedCourses FROM src_schema.professors WHERE professorId = ?")) {

            stmt.setInt(1, professorId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    assignedCourses = rs.getString("assignedCourses");
                }
            }
        } catch (SQLException ex) {
        	System.out.println("SQL Exception generated" +ex.getMessage());
        }
        return assignedCourses;
    }

    @Override
    public boolean updateAssignedCourses(int professorId, String assignedCourses) {
        try (Connection conn = DBUtils.getConnection();
             PreparedStatement stmt = conn.prepareStatement("UPDATE src_schema.professors SET assignedCourses = ? WHERE professorId = ?")) {

            stmt.setString(1, assignedCourses);
            stmt.setInt(2, professorId);
            int rowsUpdated = stmt.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException ex) {
        	System.out.println("SQL Exception generated" +ex.getMessage());
        }
        return false;
    }
    
 @Override
 public boolean setFeeForCourse(int courseCode, int courseFee) {
	    try (Connection conn = DBUtils.getConnection();
	     
	
	         PreparedStatement pstmt = conn.prepareStatement("UPDATE src_schema.courses SET courseFee = ? WHERE courseCode = ?")) {
	            pstmt.setInt(1, courseFee);
	            pstmt.setInt(2, courseCode);
	            int rowsUpdated = pstmt.executeUpdate();
	            return rowsUpdated > 0; 
	        }catch (SQLException ex) {
	        	System.out.println("SQL Exception generated" +ex.getMessage());
	        }
	        return false;
	    } 
	}


