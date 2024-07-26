package com.gniot.crs.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.stereotype.Service;

import com.gniot.crs.bean.Student;
import com.gniot.crs.constant.SQLConstant;
import com.gniot.crs.utils.DBUtils;

@Service

public class UserDAOImpl implements UserDAOInterface {

	  public void register(Student student) throws SQLException {
	        String checkUsernameSql = "SELECT COUNT(*) FROM students WHERE username = ?";
	        String insertStudentSql = "INSERT INTO students (username, password, role, firstName, lastName, gender, age, tenthPercentage, twelfthPercentage, address, phoneNumber, emailId) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

	        try (Connection connection = DBUtils.getConnection();
	             PreparedStatement checkUsernameStmt = connection.prepareStatement(checkUsernameSql)) {

	            // Check if username already exists
	            checkUsernameStmt.setString(1, student.getUsername());
	            try (ResultSet resultSet = checkUsernameStmt.executeQuery()) {
	                if (resultSet.next() && resultSet.getInt(1) > 0) {
	                    throw new SQLException("User already exists.");
	                }
	            }

	            // Insert new student
	            try (PreparedStatement insertStudentStmt = connection.prepareStatement(insertStudentSql)) {
	                insertStudentStmt.setString(1, student.getUsername());
	                insertStudentStmt.setString(2, student.getStudentPassword());
	                insertStudentStmt.setString(3, student.getRole());
	                insertStudentStmt.setString(4, student.getFirstName());
	                insertStudentStmt.setString(5, student.getLastName());
	                insertStudentStmt.setString(6, student.getGender());
	                insertStudentStmt.setInt(7, student.getAge());
	                insertStudentStmt.setDouble(8, student.getTenthPercentage());
	                insertStudentStmt.setDouble(9, student.getTwelfthPercentage());
	                insertStudentStmt.setString(10, student.getAddress());
	                insertStudentStmt.setString(11, student.getPhoneNumber());
	                insertStudentStmt.setString(12, student.getEmailId());
	                
	                insertStudentStmt.executeUpdate();
	            }
	        }
	    }
	
	 @Override
	    public void login(String username, String password, String role) throws Exception {
	        if ("admin".equals(role)) {
	            if (!"admin".equals(username) || !"admin123".equals(password)) {
	                throw new Exception("Invalid admin credentials");
	            }
	        } else {
	            String sql;
	            if ("student".equals(role)) {
	                sql = "SELECT approved FROM students WHERE username = ? AND password = ?";
	            } else if ("professor".equals(role)) {
	                sql = "SELECT COUNT(*) FROM professors WHERE professorName = ? AND professorPassword = ?";
	            } else {
	                throw new Exception("Invalid role");
	            }

	            try (Connection connection = DBUtils.getConnection();
	                 PreparedStatement statement = connection.prepareStatement(sql)) {
	                
	                statement.setString(1, username);
	                statement.setString(2, password);
	                
	                try (ResultSet resultSet = statement.executeQuery()) {
	                    if (resultSet.next()) {
	                        if ("student".equals(role)) {
	                            boolean approved = resultSet.getBoolean("approved");
	                            if (!approved) {
	                                throw new Exception("Your account is not approved yet. Please contact the admin.");
	                            }
	                        } else {
	                            int count = resultSet.getInt(1);
	                            if (count == 0) {
	                                throw new Exception("Invalid username or password");
	                            }
	                        }
	                    } else {
	                        throw new Exception("Invalid username or password");
	                    }
	                }
	            }
	        }
	    }
	 
	 
	 
	   public void changePassword(String username, String oldPassword, String newPassword, String role) throws Exception {
	        String checkUserSql;
	        String updatePasswordSql;

	        if ("student".equals(role)) {
	            checkUserSql = "SELECT password FROM students WHERE username = ?";
	            updatePasswordSql = "UPDATE students SET password = ? WHERE username = ? AND password = ?";
	        } else if ("professor".equals(role)) {
	            checkUserSql = "SELECT professorPassword FROM professors WHERE professorName = ?";
	            updatePasswordSql = "UPDATE professors SET professorPassword = ? WHERE professorName = ? AND professorPassword = ?";
	        } else {
	            throw new Exception("Invalid role");
	        }

	        try (Connection connection = DBUtils.getConnection();
	             PreparedStatement checkUserStmt = connection.prepareStatement(checkUserSql)) {

	            // Check if the user exists and if the old password is correct
	            checkUserStmt.setString(1, username);
	            try (ResultSet resultSet = checkUserStmt.executeQuery()) {
	                if (resultSet.next()) {
	                    String currentPassword = resultSet.getString(1);
	                    if (!oldPassword.equals(currentPassword)) {
	                        throw new Exception("Old password is incorrect");
	                    }
	                } else {
	                    throw new Exception("User not found");
	                }
	            }

	            // Update the password
	            try (PreparedStatement updatePasswordStmt = connection.prepareStatement(updatePasswordSql)) {
	                updatePasswordStmt.setString(1, newPassword);
	                updatePasswordStmt.setString(2, username);
	                updatePasswordStmt.setString(3, oldPassword);
	                
	                int rowsAffected = updatePasswordStmt.executeUpdate();
	                if (rowsAffected == 0) {
	                    throw new Exception("Password update failed");
	                }
	            }
	        }
	    }
}
