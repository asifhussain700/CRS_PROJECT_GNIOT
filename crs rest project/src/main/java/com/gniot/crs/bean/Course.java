package com.gniot.crs.bean;

public class Course {
	//Declare all properties of Course
	private int courseCode;
	private String courseName;
	private String professor;
	private Boolean isOffered;
	private String courseType;
	private int courseFee;
	
	
	 // Constructor for course
	  public Course(int courseCode, String courseName,  String courseType) {
	      this.courseCode = courseCode;
	      this.courseName = courseName;
	      this.courseType = courseType;
	      
	  }
	
	public int getCourseCode() {
		return courseCode;
	}

	public void setCourseCode(int courseCode) {
		this.courseCode = courseCode;
	}
	
	public String getCourseName() {
		return courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}
	public String getProfessor() {
		return professor;
	}

	public void setProfessor(String professor) {
		this.professor = professor;
	}
	public Boolean getisOffered() {
		return isOffered;
	}

	public void setisOffered(Boolean isOffered) {
		this.isOffered = isOffered;
	}
	
	public String getCourseType() {
		return courseType;
	}

	public void setCourseType(String courseType) {
		this.courseType = courseType;
	}

	public int getCourseFee() {
		return courseFee;
	}
	public void setCourseFee(int courseFee) {
		this.courseFee = courseFee;
	}
	
	}

