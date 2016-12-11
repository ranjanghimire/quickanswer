package com.gmire.model;

import java.util.List;

import org.springframework.data.annotation.Id;

public class AppUser {

	@Id
	private String Id;

	private String userName;

	private String fullName;

	private Address address;
	
	private Long weight;

	public Long getWeight() {
		return weight;
	}

	public void setWeight(Long weight) {
		this.weight = weight;
	}

	private List<String> desiredTags;

	public List<String> getDesiredTags() {
		return desiredTags;
	}

	public void setDesiredTags(List<String> desiredTags) {
		this.desiredTags = desiredTags;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	private List<String> askedQuestionsIDs;

	private List<String> repliedAnswersIDs;

	public String getId() {
		return Id;
	}

	public void setId(String id) {
		Id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public List<String> getAskedQuestionsIDs() {
		return askedQuestionsIDs;
	}

	public void setAskedQuestionsIDs(List<String> askedQuestionsIDs) {
		this.askedQuestionsIDs = askedQuestionsIDs;
	}

	public List<String> getRepliedAnswersIDs() {
		return repliedAnswersIDs;
	}

	public void setRepliedAnswersIDs(List<String> repliedAnswersIDs) {
		this.repliedAnswersIDs = repliedAnswersIDs;
	}

}
