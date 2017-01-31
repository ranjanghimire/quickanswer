package com.gmire.model;

import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.Id;

import com.fasterxml.jackson.annotation.JsonFormat;

public class Notification {
	
	@Id
	private String id;
	
	//e.g. message, like, replied, etc.
	private String notificationType;
	
	private Question question;
	
	//who to deliver
	private List<String> toUserId;
	
	//action taken by
	private String fromUserId;
	private String fromUserName;
	
	private boolean isViewed;

	@JsonFormat(pattern="yyyy-MM-dd HH:mm")
	private Date notificationTime;
	
	public String getFromUserName() {
		return fromUserName;
	}

	public void setFromUserName(String fromUserName) {
		this.fromUserName = fromUserName;
	}

	public boolean isViewed() {
		return isViewed;
	}

	public void setViewed(boolean isViewed) {
		this.isViewed = isViewed;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNotificationType() {
		return notificationType;
	}

	public void setNotificationType(String notificationType) {
		this.notificationType = notificationType;
	}

	public Question getQuestion() {
		return question;
	}

	public void setQuestion(Question question) {
		this.question = question;
	}

	public List<String> getToUserId() {
		return toUserId;
	}

	public void setToUserId(List<String> toUserId) {
		this.toUserId = toUserId;
	}

	public String getFromUserId() {
		return fromUserId;
	}

	public void setFromUserId(String fromUserId) {
		this.fromUserId = fromUserId;
	}

	public Date getNotificationTime() {
		return notificationTime;
	}

	public void setNotificationTime(Date notificationTime) {
		this.notificationTime = notificationTime;
	}
	
	
}
