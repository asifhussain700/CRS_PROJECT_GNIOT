package com.gniot.crs.dao;

import java.sql.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.gniot.crs.bean.Course;
import com.gniot.crs.bean.Student;
import com.gniot.crs.utils.DBUtils;

public  class StudentDAOImpl implements StudentDAOInterface {


    // SQL query to insert a new student into the database
    private static final String INSERT_STUDENT_SQL = "INSERT INTO students (username, password, role, firstName, lastName, gender, age, tenthPercentage, twelfthPercentage, address, phoneNumber, emailId, approved) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
 
    final static String YELLOW = "\u001B[33m";
    final static String GREEN = "\u001B[32m";
    final static String RESET = "\u001B[0m";
    
    @Override
    public void addStudent(Student student) {
        try (Connection connection = DBUtils.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_STUDENT_SQL)) {
        	  // Set the parameters for the SQL query
        	preparedStatement.setString(1, student.getUsername());
            preparedStatement.setString(2, student.getStudentPassword());
            preparedStatement.setString(3, student.getRole());
            preparedStatement.setString(4, student.getFirstName());
            preparedStatement.setString(5, student.getLastName());
            preparedStatement.setString(6, student.getGender());
            preparedStatement.setInt(7, student.getAge());
            preparedStatement.setDouble(8, student.getTenthPercentage());
            preparedStatement.setDouble(9, student.getTwelfthPercentage()); 
            preparedStatement.setString(10, student.getAddress());
            preparedStatement.setString(11, student.getPhoneNumber());
            preparedStatement.setString(12, student.getEmailId());
            preparedStatement.setBoolean(13, student.Approved());
            // Execute the update
            preparedStatement.executeUpdate();
            System.err.println("Student Information saved in DataBase");
        } catch (SQLException ex) {
        	System.out.println("SQL Exception generated" +ex.getMessage());
        }
    }
    
    @Override
    public String findStudentByUsername(String username, String password) {
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Connection conn = null;
        try {

        	String sql = "SELECT * FROM students WHERE username = ? AND password = ? ";
            conn = DBUtils.getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, username);
            stmt.setString(2, password);
            rs = stmt.executeQuery();
            
            
            if (rs.next()) {
              
           int Approval = rs.getInt("approved");
         if(Approval == 0) 
        	 return "notApproved";
         else
        	 return "Approved";
            }  
            
            
        } catch (SQLException ex) {
           
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException ex) {
            	System.out.println("SQL Exception generated" +ex.getMessage());
            }
        }
        return "not fined";
    }
    
    
    @Override
    public Boolean updateStudentPassword(String username, String oldPassword, String newPassword) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        boolean success = false;

        try {
        	conn = DBUtils.getConnection();
            // First, verify the old password
            String sql = "SELECT * FROM students WHERE username = ? AND password = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, username);
            stmt.setString(2, oldPassword);
            rs = stmt.executeQuery();

            if (rs.next()) {
                // If old password matches, update the password
                sql = "UPDATE students SET password = ? WHERE username = ?";
                stmt = conn.prepareStatement(sql);
                stmt.setString(1, newPassword);
                stmt.setString(2, username);
                int rowsUpdated = stmt.executeUpdate();

                if (rowsUpdated > 0) {
                    success = true;
                } else {
                    System.err.println("Failed to update password.");
                }
            } else {
                System.err.println("Invalid username or old password.");
            }
        } catch (SQLException ex) {
        	System.err.println("SQL Exception generated" +ex.getMessage());
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException ex) {
            	System.err.println("SQL Exception generated" +ex.getMessage());
            }
        }
		return success;
    }

    @Override
    public String checkUserName(String username) {
        String sql = "SELECT COUNT(*) FROM students WHERE username = ?";
        try (Connection connection = DBUtils.getConnection();
        		PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next() && resultSet.getInt(1) > 0) {
                return "Student already exists";
            }
        } catch (SQLException ex) {
        	System.err.println("SQL Exception generated" +ex.getMessage());
        }
        return null;
    }
    
    public List<Course> getCourses() {
        List<Course> courses = new ArrayList<>();
        String sql = "SELECT courseCode, courseName,  courseType, courseFee FROM courses";

        try (Connection connection = DBUtils.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                int courseCode = resultSet.getInt("courseCode");
                String courseName = resultSet.getString("courseName");
                String courseType = resultSet.getString("courseType");
               
                Course course = new Course(courseCode, courseName,  courseType);
                courses.add(course);
            }

        } catch (SQLException ex) {
        	System.err.println("SQL Exception generated" +ex.getMessage());
        }

        return courses;
    }
    
  
    @Override
    public boolean registerForCourse(String username, int courseCode, String courseType) {
        String fetchIdSql = "SELECT id FROM students WHERE username = ?";
        String checkSql = "SELECT COUNT(*) FROM student_courses WHERE username = ? AND courseType = ?";
        String insertSql = "INSERT INTO student_courses (id, username, courseCode, courseType) VALUES (?, ?, ?, ?)";

        try (Connection connection = DBUtils.getConnection();
             PreparedStatement fetchIdStmt = connection.prepareStatement(fetchIdSql);
             PreparedStatement checkStmt = connection.prepareStatement(checkSql);
             PreparedStatement insertStmt = connection.prepareStatement(insertSql)) {

            // Fetch the student ID based on the username
            fetchIdStmt.setString(1, username);
            ResultSet idResultSet = fetchIdStmt.executeQuery();
            if (!idResultSet.next()) {
                System.err.println("No student found with the username: " + username);
                return false;
            }
            int studentId = idResultSet.getInt("id");

            // Check the number of courses the student has already registered for this type
            checkStmt.setString(1, username);
            checkStmt.setString(2, courseType);
            ResultSet resultSet = checkStmt.executeQuery();
            if (resultSet.next()) {
                int count = resultSet.getInt(1);
                if ((courseType.equals("primary") && count >= 4) || (courseType.equals("alternate") && count >= 2)) {
                    System.err.println("Exceeded the limit for " + courseType + " courses.");
                    return false;
                }
            }

            // Insert the new course registration
            insertStmt.setInt(1, studentId);
            insertStmt.setString(2, username);
            insertStmt.setInt(3, courseCode);
            insertStmt.setString(4, courseType);
            int rowsInserted = insertStmt.executeUpdate();
            if (rowsInserted > 0) {
                return true;
            } else {
                System.err.println("Insert failed, no rows inserted.");
                return false;
            }

        } catch (SQLException ex) {
            System.err.println("SQL error: " + ex.getMessage());
            return false;
        }
    }


    @Override
    public boolean dropCourse(String username, int courseCode) {
        String sql = "DELETE FROM student_courses WHERE username = ? AND courseCode = ?";
        
        try (Connection connection = DBUtils.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, username);
            preparedStatement.setInt(2, courseCode);
            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    @Override
    public List<Map<String, Object>> getCoursesWithGrades(String username) throws SQLException {
        List<Map<String, Object>> coursesWithGrades = new ArrayList<>();
        try (Connection conn = DBUtils.getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                     "SELECT c.courseCode, c.courseName, sc.grade " +
                     "FROM src_schema.student_courses sc " +
                     "JOIN src_schema.courses c ON sc.courseCode = c.courseCode " +
                     "WHERE sc.username = ?")) {
            stmt.setString(1, username);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Map<String, Object> course = new HashMap<>();
                    course.put("courseCode", rs.getInt("courseCode"));
                    course.put("courseName", rs.getString("courseName"));
                    course.put("grade", rs.getString("grade"));
                    coursesWithGrades.add(course);
                }
            }
        }
        return coursesWithGrades;
    }
    @Override
    public List<Map<String, Object>> getRegisteredCourses(String username) throws SQLException {
        List<Map<String, Object>> registeredCourses = new ArrayList<>();
        String query = "SELECT courses.courseCode, courses.courseName, courses.courseFee, student_courses.courseType "
                     + "FROM student_courses "
                     + "INNER JOIN courses ON student_courses.courseCode = courses.courseCode "
                     + "WHERE username = ?";
        try (Connection conn = DBUtils.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(query)) {
            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Map<String, Object> course = new HashMap<>();
                course.put("courseCode", resultSet.getInt("courseCode"));
                course.put("courseName", resultSet.getString("courseName"));
                course.put("courseFee", resultSet.getInt("courseFee")); // Fetch courseFee from courses table
                course.put("courseType", resultSet.getString("courseType"));
                registeredCourses.add(course);
            }
        }
        return registeredCourses;
    }


    
    @Override
    public Student getStudentInfo(String username) throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Student student = null;
        try {
            conn = DBUtils.getConnection();
            stmt = conn.prepareStatement("SELECT * FROM src_schema.students WHERE username = ?");
            stmt.setString(1, username);
            rs = stmt.executeQuery();
            if (rs.next()) {
                student = new Student(
                    rs.getString("username"),
                    rs.getString("password"),
                    rs.getString("role"),
                    rs.getString("firstName"),
                    rs.getString("lastName"),
                    rs.getString("gender"),
                    rs.getInt("age"),
                    rs.getDouble("tenthPercentage"),
                    rs.getDouble("twelfthPercentage"),
                    rs.getString("address"),
                    rs.getString("phoneNumber"),
                    rs.getString("emailId")
                );
            }
        } finally {
            
        }
        return student;
    }
    
    @Override
    public int getCourseFee(int courseCode) throws SQLException {
        String query = "SELECT courseFee FROM courses WHERE courseCode = ?";
        try (Connection conn = DBUtils.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(query)) {
            preparedStatement.setInt(1, courseCode);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt("courseFee");
            } else {
                throw new SQLException("Course not found.");
            }
        }
    }
    
    
    @Override
    public void updateStudentBalance(String username, int totalCourseFee) throws SQLException {
        String query = "UPDATE src_schema.students SET balance = COALESCE(balance, 0) + ? WHERE username = ?";
        try (Connection conn = DBUtils.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(query)) {
            preparedStatement.setInt(1, totalCourseFee);
            preparedStatement.setString(2, username);
            
            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected == 0) {
                throw new SQLException("Failed to update balance. Student not found for username: " + username);
            }
            
            System.out.println(GREEN + "Student balance updated successfully for username: " + username + RESET);
        } catch (SQLException ex) {
            System.err.println("Error updating student balance: " + ex.getMessage());
            throw ex; // Re-throw the exception to propagate it
        }
    }




}