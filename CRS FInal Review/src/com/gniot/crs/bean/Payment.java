package com.gniot.crs.bean;

public class Payment {
	 private int StudentId;
	 public int getStudentId() {
		return StudentId;
	}
	public void setStudentId(int studentId) {
		StudentId = studentId;
	}
	public String getReferenceId() {
		return ReferenceId;
	}
	public void setReferenceId(String referenceId) {
		ReferenceId = referenceId;
	}
	public float getAmount() {
		return Amount;
	}
	public void setAmount(float amount) {
		Amount = amount;
	}
	public boolean isStatus() {
		return Status;
	}
	public void setStatus(boolean status) {
		Status = status;
	}
	private String ReferenceId;
	 private float Amount;
	 private boolean Status;

}
