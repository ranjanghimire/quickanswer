package com.gmire.model;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;

public class Author {

	@Id
	private String id;
	private String authorName;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getAuthorName() {
		return authorName;
	}

	public void setAuthorName(String authorName) {
		if (id == null || id.equals("")){
			id = new ObjectId().toString();
		}
		this.authorName = authorName;
	}
	
	public Author(){
		
	}
	
	public Author(String authorName){
		if (id == null || id.equals("")){
			id = new ObjectId().toString();
		}
		this.authorName = authorName;
	}
}
