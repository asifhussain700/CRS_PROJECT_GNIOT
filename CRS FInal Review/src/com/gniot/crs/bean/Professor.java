package com.gniot.crs.bean;

public class Professor {
	// Declare all the properties of Professor

	private int professorId;
	private int professorDOJ;
	private String professorDepartment;
	private String professorDesignation;
	private String professorName;
	private String professorEmail;
	private String professorPassword;
	private String role;

	 // Constructor for professor
  public Professor(String professorName, String professorPassword, String role) {
      this.professorName = professorName;
      this.professorPassword = professorPassword;
      this.role = role;
  }

	
	
	public int getProfessorId() {
		return professorId;
	}

	public void setProfessorId(int professorId) {
		this.professorId = professorId;
	}

	public int getProfessorDOJ() {
		return professorDOJ;
	}

	public void setProfessorDOJ(int professorDOJ) {
		this.professorDOJ = professorDOJ;
	}

	public String getProfessorDepartment() {
		return professorDepartment;
	}

	public void setProfessorDepartment(String professorDepartment) {
		this.professorDepartment = professorDepartment;
	}

	public String getprofessorDesignation() {
		return professorDesignation;
	}

	public void setProfessorDesignation(String professorDesignation) {
		this.professorDesignation = professorDesignation;
	}

	public String getProfessorName() {
		return professorName;
	}

	public void setProfessorName(String professorName) {
		this.professorName = professorName;
	}

	public String getProfessorEmail() {
		return professorEmail;
	}

	public void setProfessorEmail(String professorEmail) {
		this.professorEmail = professorEmail;
	}

	public String getProfessorPassword() {
		return professorPassword;
	}

	public void setProfessorPassword(String professorPassword) {
		this.professorPassword = professorPassword;
	}
	public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
