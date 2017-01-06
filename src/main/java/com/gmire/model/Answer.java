package com.gmire.model;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class Answer {

	//TODO: Add timestamp to store time of answer.
	@Id
	@JsonIgnore
	private String id;
	
	private String answerId;

	private String mainAnswer;

	private Author author;
	
	private Long votes;

	private Long weight;
	
	private boolean isLiked;
	
	@JsonIgnore
	private List<String> likedByUserIDs; 

	//whether or not the answer is marked by some user to be accepted as a verified answer
	private boolean isMarkedForAcceptance;

	//After review, the answer can be marked as verified. 
	private boolean isVerifiedAnswer;

	public Long getVotes() {
		return votes;
	}


	public void setVotes(Long votes) {
		this.votes = votes;
	}


	public boolean isLiked() {
		return isLiked;
	}

	
	public List<String> getLikedByUserIDs() {
		return likedByUserIDs;
	}


	public void setLikedByUserIDs(List<String> likedByUserIDs) {
		this.likedByUserIDs = likedByUserIDs;
	}


	public void setLiked(boolean isLiked) {
		this.isLiked = isLiked;
	}

	public boolean isMarkedForAcceptance() {
		return isMarkedForAcceptance;
	}

	public void setMarkedForAcceptance(boolean isMarkedForAcceptance) {
		this.isMarkedForAcceptance = isMarkedForAcceptance;
	}

	public boolean isVerifiedAnswer() {
		return isVerifiedAnswer;
	}

	public void setVerifiedAnswer(boolean isVerifiedAnswer) {
		this.isVerifiedAnswer = isVerifiedAnswer;
	}

	public Long getWeight() {
		return weight;
	}

	public void setWeight(Long weight) {
		this.weight = weight;
	}

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

	public String getAnswerId() {
		return answerId;
	}

	public void setAnswerId(String id) {
		this.answerId = id;
	}

	public String getMainAnswer() {
		return mainAnswer;
	}

	public void setMainAnswer(String mainAnswer) {
		checkAndPopulateId();
		this.mainAnswer = mainAnswer;
	}

	public void checkAndPopulateId() {
		if (answerId == null || answerId.equals("")) {
			answerId = new ObjectId().toString();
		}
	}
}
