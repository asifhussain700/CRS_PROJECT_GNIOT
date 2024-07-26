/**
 * 
 */
package com.gniot.crs.constant;

/**
 * 
 */
public class SQLConstant {
public static final String INSERT_COURSES = "INSERT INTO courses (course_id, course_name, course_code) VALUES (?, ?, ?)";
public static final String FETCH_LOGIN = "SELECT * FROM students WHERE username = ? AND password = ?";
}