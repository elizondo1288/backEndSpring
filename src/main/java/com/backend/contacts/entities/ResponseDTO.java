package com.backend.contacts.entities;

public class ResponseDTO {

	//success or 
	private String message;
	
	//200 for success, 500 for error
	private int status;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}
	
	
}
