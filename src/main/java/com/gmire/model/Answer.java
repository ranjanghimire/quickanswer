package com.gmire.model;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;

public class Answer {

	@Id
	private String id;
	private String mainAnswer;
	private Author author;

	public Author getAuthor() {
		return author;
	}

	public void setAuthor(Author author) {
		this.author = author;
	}

	public Answer() {

	}

	public Answer(String mainAnswer) {
		checkAndPopulateId();
		this.mainAnswer = mainAnswer;
	}

	public Answer(String mainAnswer, Author author) {
		checkAndPopulateId();
		this.mainAnswer = mainAnswer;
		this.author = author;
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
		checkAndPopulateId();
		this.mainAnswer = mainAnswer;
	}

	public void checkAndPopulateId() {
		if (id == null || id.equals("")) {
			id = new ObjectId().toString();
		}
	}
}
