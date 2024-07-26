/**
 * 
 */
package com.gniot.crs.business;

/**
 * 
 */
public class UserOperation implements UserInterface {
	// update detail
	public boolean updateDetails(int userID, String userName, String userDepartment) {

		System.out.println("<----Update Detail---->");
		return true;
	}

	// update password
	public boolean updatePassword(String password) {

		System.out.println("<----Update Detail---->");
		return true;
	}

}
