package com.gmire.model;

import org.springframework.data.annotation.Id;

public class AppCategory {
	@Id
	String id;
	
	String category;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}
	
}
