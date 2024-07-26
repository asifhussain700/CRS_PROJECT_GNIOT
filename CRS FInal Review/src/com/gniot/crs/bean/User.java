package com.gniot.crs.bean;




public class User {
    private String username;
    private String password;
    private String role;
    
   

    // Constructor for predefined admin
    
    public User(String username, String password, String role, boolean Approved) {
        this.username = username;
        this.password = password;
        this.role = role;
    
    }
      
// Constructor for user log in

        public User(String username, String password, String role) {
            this.username = username;
            this.password = password;
            this.role = role;
        }

       

//    // Existing getters and setters
        
        
   public String getUsername() {
       return username;
   }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

   public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

  
}
