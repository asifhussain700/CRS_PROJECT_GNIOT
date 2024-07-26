package com.gniot.crs.bean;

public class PaymentNotification {
	private int StudentId;
	public int getStudentId() {
		return StudentId;
	}
	public void setStudentId(int studentId) {
		StudentId = studentId;
	}
	public int getReferenceId() {
		return ReferenceId;
	}
	public void setReferenceId(int referenceId) {
		ReferenceId = referenceId;
	}
	public int getNotificationId() {
		return NotificationId;
	}
	public void setNotificationId(int notificationId) {
		NotificationId = notificationId;
	}
	public String getNotificationMessage() {
		return notificationMessage;
	}
	public void setNotificationMessage(String notificationMessage) {
		this.notificationMessage = notificationMessage;
	}
	private int ReferenceId;
	private int NotificationId;
	private String notificationMessage;
}
