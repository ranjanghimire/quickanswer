package com.gmire.dto;

import java.util.List;

public class CategoryDto {
	String category;
	List<String> topics;
	
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public List<String> getTopics() {
		return topics;
	}
	public void setTopics(List<String> topics) {
		this.topics = topics;
	}
}
