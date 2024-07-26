/**
 * 
 */
package com.gniot.crs.business;

import java.sql.SQLException;

import com.gniot.crs.bean.Student;
import com.gniot.crs.bean.User;


/**
 * 
 */
public interface UserInterface {
	
	void register(Student student) throws SQLException;
	void login(String username, String password, String role) throws Exception;
	void changePassword(String username, String oldPassword, String newPassword, String role) throws Exception;
	

}
