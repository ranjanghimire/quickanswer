package com.gmire.model;

import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.Id;

public class AppUser {
	//TODO: NEW: Change the AppUser Model to use repliedQuestionsIDs instead of repliedAnswersIDs. 
	@Id
	private String id;

	private String userName;
	
	private String password;

	private String fullName;

	private Address address;
	
	private List<Message> messages;

	private List<String> askedQuestionsIDs;

	private List<String> repliedAnswersIDs;
	
	private List<String> likedIds;

	// users who have vouched this guy.
	private List<AppUser> vouchedByUsers;

	private List<Date> loginTime;

	// All the questions with tags he has liked, upvoted, etc.
	private List<String> desiredTags;

	// All the topics he has liked, upvoted, etc.
	private List<String> desiredTopics;

	// Anytime a user is vouched, the weight goes up bigly.
	// Anytime he answers a question, the weight goes up
	// Anytime his answer is accepted and verified, weight goes up bigly.
	private Long weight;

	// how many times this user has been vouched by others
	private Long vouchCount;
	

	public List<String> getLikedIds() {
		return likedIds;
	}

	public void setLikedIds(List<String> likedIds) {
		this.likedIds = likedIds;
	}

	public List<Message> getMessages() {
		return messages;
	}

	public void setMessages(List<Message> messages) {
		this.messages = messages;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Long getVouchCount() {
		return vouchCount;
	}

	public void setVouchCount(Long vouchCount) {
		this.vouchCount = vouchCount;
	}

	public List<AppUser> getVouchedByUsers() {
		return vouchedByUsers;
	}

	public void setVouchedByUsers(List<AppUser> vouchedByUsers) {
		this.vouchedByUsers = vouchedByUsers;
	}

	public List<String> getDesiredTopics() {
		return desiredTopics;
	}

	public void setDesiredTopics(List<String> desiredTopics) {
		this.desiredTopics = desiredTopics;
	}

	private List<String> typedSearches;

	public List<String> getTypedSearches() {
		return typedSearches;
	}

	public void setTypedSearches(List<String> typedSearches) {
		this.typedSearches = typedSearches;
	}

	public List<Date> getLoginTime() {
		return loginTime;
	}

	public void setLoginTime(List<Date> loginTime) {
		this.loginTime = loginTime;
	}

	public Long getWeight() {
		return weight;
	}

	public void setWeight(Long weight) {
		this.weight = weight;
	}

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

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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
