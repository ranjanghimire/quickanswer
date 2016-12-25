package com.gmire.model;

import org.springframework.data.annotation.Id;

public class Author {

	@Id
	private String id;

	private String appUserId;
	
	private String appUserName;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	public String getAppUserName() {
		return appUserName;
	}

	public void setAppUserName(String appUserName) {
		this.appUserName = appUserName;
	}

	public String getAppUserId() {
		return appUserId;
	}

	public void setAppUserId(String appUserId) {
		this.appUserId = appUserId;
	}

	public Author() {

	}

}
