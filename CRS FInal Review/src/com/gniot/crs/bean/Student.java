package com.gniot.crs.bean;

public class Student {
    private int studentId;
    private String username;
    private static String studentPassword;
    private String role;
    private boolean Approved;
    private String firstName;
    private String lastName;
    private String gender;
    private int age;
    private double tenthPercentage;
    private double twelfthPercentage;
    private String address;
    private String phoneNumber;
    private String emailId;
	
    

    // Constructor for new student registration
    public Student(String username , String studentPassword, String role, String firstName, String lastName, String gender, int age, double tenthPercentage, double twelfthPercentage, String address, String phoneNumber, String emailId) {
        this.username = username;
        this.studentPassword = studentPassword;
        this.role = (role != null) ? role : "";
        
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.age = age;
        this.tenthPercentage = tenthPercentage;
        this.twelfthPercentage = twelfthPercentage;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.emailId = emailId;
        this.Approved = false;// Default to false for new students
    }
    
    
   
    // Getters and setters for all fields
    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }


    public String getStudentPassword() {
        return (studentPassword != null) ? studentPassword : "";
    }

    public void setStudentPassword(String password) {
        this.studentPassword = (password != null) ? password : "";
    }

    public String getRole() {
        return (role != null) ? role : "";
    }

    public void setRole(String role) {
        this.role = (role != null) ? role : "";
    }

    public boolean Approved() {
        return Approved;
    }

    public void setApproved(boolean Approved) {
        this.Approved = Approved;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public double getTenthPercentage() {
        return tenthPercentage;
    }

    public void setTenthPercentage(double tenthPercentage) {
        this.tenthPercentage = tenthPercentage;
    }

    public double getTwelfthPercentage() {
        return twelfthPercentage;
    }

    public void setTwelfthPercentage(double twelfthPercentage) {
        this.twelfthPercentage = twelfthPercentage;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }
}
