package com.backend.contacts.entities;

public class UpdateDTO extends ContactDTO {

	
	private String oldEmail;
	
	public String getOldEmail() {
		return oldEmail;
	}
	public void setOldEmail(String oldEmail) {
		this.oldEmail = oldEmail;
	}
	
}
