package com.gniot.crs.bean;

public class RegisterationCourse {
	
	private String courseCode;
	private int semester;
	private int studentId;
	private String grade;
	
	public String getcourseCode() {
		return courseCode;
	}

	public void setCourseCode(String courseCode) {
		this.courseCode = courseCode;
	}
	public int getsemester() {
		return semester;
	}

	public void setsemester(int semester) {
		this.semester = semester;
	}
	public int getstudentId() {
		return studentId;
	}

	public void setstudentId(int studentId) {
		this.studentId = studentId;
	}
	public String getgrade() {
		return grade;
	}

	public void setgrade(String grade) {
		this.grade = grade;
	}
	}
