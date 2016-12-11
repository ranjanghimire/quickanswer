package com.gmire.model;

import java.util.List;

import org.springframework.data.annotation.Id;

public class Question {

	@Id
	private String id;

	private String mainQuestion;

	private List<Answer> answers;

	// user who asked the question. The current user who asks the question is
	// the author
	private Author author;

	//Anytime the question is upvoted or viewed, etc., the weight goes up.
	//Also, questions asked by higher weight user has higher weight. 
	private Long weight;
	
	private String topic;

	private List<String> tags;

	private boolean isLocationSpecific;

	private List<Address> relevantLocations;

	// if any of the answer has been verified
	private boolean hasVerifiedAnswer;

	// if any of the answer is reported by some user as true and verified.
	// These are candidates for review and to set as hasVerifiedAnswer
	private boolean hasAcceptedAnswer;

	public boolean isLocationSpecific() {
		return isLocationSpecific;
	}

	public void setLocationSpecific(boolean isLocationSpecific) {
		this.isLocationSpecific = isLocationSpecific;
	}

	public List<Address> getRelevantLocations() {
		return relevantLocations;
	}

	public void setRelevantLocations(List<Address> relevantLocations) {
		this.relevantLocations = relevantLocations;
	}

	public boolean isHasVerifiedAnswer() {
		return hasVerifiedAnswer;
	}

	public void setHasVerifiedAnswer(boolean hasVerifiedAnswer) {
		this.hasVerifiedAnswer = hasVerifiedAnswer;
	}

	public boolean isHasAcceptedAnswer() {
		return hasAcceptedAnswer;
	}

	public void setHasAcceptedAnswer(boolean hasAcceptedAnswer) {
		this.hasAcceptedAnswer = hasAcceptedAnswer;
	}

	public Long getWeight() {
		return weight;
	}

	public void setWeight(Long weight) {
		this.weight = weight;
	}

	public String getTopic() {
		return topic;
	}

	public void setTopic(String topic) {
		this.topic = topic;
	}

	public List<String> getTags() {
		return tags;
	}

	public void setTags(List<String> tags) {
		this.tags = tags;
	}

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

	public Question(String question) {
		mainQuestion = question;
	}

	public Question(String question, Author author) {
		mainQuestion = question;
		this.author = author;
	}

}
