package com.gniot.crs.business;

import com.gniot.crs.bean.Student;
import com.gniot.crs.bean.User;
import com.gniot.crs.dao.UserDAOInterface;

import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserOperation implements UserInterface {
    private final UserDAOInterface userDAO;

    @Autowired
    public UserOperation(UserDAOInterface userDAO) {
        this.userDAO = userDAO;
    }

  
    @Override
    public void register(Student sudent) throws SQLException {
        userDAO.register(sudent);
    }
    
    @Override
    public void login(String username, String password, String role) throws Exception {
        userDAO.login(username, password, role);
    }

    @Override
    public void changePassword(String username, String oldPassword, String newPassword, String role) throws Exception {
        userDAO.changePassword(username, oldPassword, newPassword, role);
    }

  


}
