package com.gmire.dto;

public class WordSearchDto {
	
	private String id;
	
	private String mainQuestion;
	
	private String topic;
	
	private String category;

	public String getMainQuestion() {
		return mainQuestion;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setMainQuestion(String mainQuestion) {
		this.mainQuestion = mainQuestion;
	}

	public String getTopic() {
		return topic;
	}

	public void setTopic(String topic) {
		this.topic = topic;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}
}
