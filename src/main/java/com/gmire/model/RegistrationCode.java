package com.gmire.model;

import org.springframework.data.annotation.Id;

public class RegistrationCode {
	
	@Id
	private String id;

	private String emailAddress;
	
	private String code;
	
	private boolean active;

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}
	
	
}
