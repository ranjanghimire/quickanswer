package com.gmire.model;

import java.util.List;

import org.springframework.data.annotation.Id;

public class Question {

	@Id
	private String id;

	private String mainQuestion;

	private List<Answer> answers;

	private Author author;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getMainQuestion() {
		return mainQuestion;
	}

	public void setMainQuestion(String mainQuestion) {
		this.mainQuestion = mainQuestion;
	}

	public List<Answer> getAnswers() {
		return answers;
	}

	public void setAnswers(List<Answer> answers) {
		this.answers = answers;
	}

	public Author getAuthor() {
		return author;
	}

	public void setAuthor(Author author) {
		this.author = author;
	}

	public Question() {

	}
	
	public Question(String question){
		mainQuestion = question;
	}
	
	public Question(String question, Author author){
		mainQuestion = question;
		this.author = author;
	}

}
