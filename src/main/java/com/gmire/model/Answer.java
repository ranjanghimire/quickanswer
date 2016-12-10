package com.gmire.model;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;

public class Answer {

	@Id
	private String id;
	private String mainAnswer;

	public Answer(){
		
	}
	
	public Answer (String mainAnswer){
		if (id == null || id.equals("")){
			id = new ObjectId().toString();
		}
		this.mainAnswer = mainAnswer;
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getMainAnswer() {
		return mainAnswer;
	}

	public void setMainAnswer(String mainAnswer) {
		if (id == null || id.equals("")){
			id = new ObjectId().toString();
		}
		this.mainAnswer = mainAnswer;
	}
}
