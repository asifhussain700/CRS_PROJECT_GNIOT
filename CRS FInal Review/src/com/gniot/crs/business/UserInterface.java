/**
 * 
 */
package com.gniot.crs.business;

/**
 * 
 */
public interface UserInterface {
	public boolean updateDetails(int userID, String userName, String userDepartment);
	public boolean updatePassword(String password);

}
