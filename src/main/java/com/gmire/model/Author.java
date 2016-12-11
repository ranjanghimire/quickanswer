package com.gmire.model;

import org.springframework.data.annotation.Id;

public class Author {

	@Id
	private String id;

	private String appUserId;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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
