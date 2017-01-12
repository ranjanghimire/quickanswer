package com.gmire.model;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class Message {
	String fromUserName;
	String fromUserId;
	String mainMessage;
	boolean isRead;
		
	@JsonFormat(pattern="yyyy-MM-dd HH:mm")
	Date messageTime;
	
	String subject;
	
	public boolean isRead() {
		return isRead;
	}
	public void setRead(boolean isRead) {
		this.isRead = isRead;
	}
	
	public Date getMessageTime() {
		return messageTime;
	}
	public void setMessageTime(Date messageTime) {
		this.messageTime = messageTime;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getFromUserName() {
		return fromUserName;
	}
	public void setFromUserName(String fromUserName) {
		this.fromUserName = fromUserName;
	}
	public String getFromUserId() {
		return fromUserId;
	}
	public void setFromUserId(String fromUserId) {
		this.fromUserId = fromUserId;
	}
	public String getMainMessage() {
		return mainMessage;
	}
	public void setMainMessage(String mainMessage) {
		this.mainMessage = mainMessage;
	}
	
	
	
}
