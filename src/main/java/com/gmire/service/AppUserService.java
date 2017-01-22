package com.gmire.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gmire.model.AppUser;
import com.gmire.model.Message;
import com.gmire.repository.AppUserRepository;

@Service
public class AppUserService {

	@Autowired
	AppUserRepository appUserRepository;

	// Create User
	public AppUser create(AppUser appUser) {
		AppUser savedUser = appUserRepository.save(appUser);
		return savedUser;
	}

	// delete all users
	public void deleteAll() {
		appUserRepository.deleteAll();
	}

	// delete a user by id
	public void delete(String id) {
		appUserRepository.delete(id);

	}

	//Update a user
	public AppUser update(AppUser appUser) {
		AppUser appUserPersisted = appUserRepository.findOne(appUser.getId());
		if (appUserPersisted == null) {
			// cannot update a user that hasn't been persisted
			return null;
		}
		AppUser updateUser = appUserRepository.save(appUser);
		return updateUser;
	}

	//Find all users
	public List<AppUser> findAll() {
		return appUserRepository.findAll();
	}

	//Search user by username
	public List<AppUser> findByUserNameIgnoreCaseLike(String userName) {
		List<AppUser> retList = new ArrayList<AppUser>();
		retList = appUserRepository.findByUserNameIgnoreCaseLike(userName);
		return retList;
	}

	public AppUser findByUserNameAndPassword(String userName, String password) {
		AppUser retUser =  appUserRepository.findByUserNameAndPassword(userName, password);
		
		if (retUser == null){
			return null;
		}
		
		if (retUser.getMessages() != null && !retUser.getMessages().isEmpty()){
			List<Message> messages = retUser.getMessages();

			Collections.sort(messages, (Message a, Message b) -> b.getMessageTime().compareTo(a.getMessageTime()));
			
			retUser.setMessages(messages);
		}
		
		return retUser;
	}

	//update user's password. The user must exist at first place.
	public AppUser updateUserPassword(String userName, String password) {
		AppUser retUser = appUserRepository.findByUserName(userName);
		
		if (retUser == null){
			return null;
		}
		
		retUser.setPassword(password);
		
		retUser = appUserRepository.save(retUser);
		
		return retUser;
	}

	public AppUser findOneByUserId(String id) {

		AppUser retUser = appUserRepository.findOne(id);
		
		if (retUser == null){
			return null;
		}
		
		if (retUser.getMessages() != null && !retUser.getMessages().isEmpty()){
			List<Message> messages = retUser.getMessages();

			Collections.sort(messages, (Message a, Message b) -> b.getMessageTime().compareTo(a.getMessageTime()));
			
			retUser.setMessages(messages);
		}
		
		return retUser;
	}

	//Update FollowedTopics in AppUser when someone follows a topic
	//Also update that topic in the desired topic as well
	//Should be able to remove as well
	public AppUser followTopics(String userId, boolean followFlag, List<String> topicList) {
		
		//validate topicList
		if (topicList== null || topicList.isEmpty()){
			//return null so that error will be returned.
			return null;
		}
		
		AppUser appUser = appUserRepository.findOne(userId);
		
		//if appUser is null, return straightaway. 
		if (appUser == null){
			return null;
		}
		
		//If only followFlag is true, add the topics, else remove
		if (followFlag == true){
			//If followedTopics is null, create new list and add
			if (appUser.getFollowedTopics() == null){
				appUser.setFollowedTopics(topicList);
			}		
			//else search if the topics already exist and append if not
			else{
				for (String topic: topicList){
					if (!appUser.getFollowedTopics().contains(topic)){
						appUser.getFollowedTopics().add(topic);
					}
				}
			}
		}
		//If followFlag is false, remove the topics
		else{
			//If followedTopics is null, can't remove anything
			if (appUser.getFollowedTopics() !=null && !appUser.getFollowedTopics().isEmpty()){
				for(String topic: topicList){
					if(appUser.getFollowedTopics().contains(topic)){
						appUser.getFollowedTopics().remove(topic);
					}
				}
			}
		}
		
		//save this updated user and return
		appUserRepository.save(appUser);
		return appUser;
	}

	public AppUser bookmarkQuestion(String userId, String questionId, boolean bookmarkFlag) {
		// TODO Auto-generated method stub
		// Validate input parameters
		if (userId == null || userId.equals("") || questionId == null || questionId.equals("")){
			return null;
		}
				
		//Get the user from userId
		AppUser appUser = appUserRepository.findOne(userId);
		
		//If user is null, return right away
		if (appUser == null){
			return null;
		}
		
		//If bookmark flag is true, add the questionid to followedIDs list
		if (bookmarkFlag == true){
			//If bookmarkId list is null, create a new list and set
			if (appUser.getBookmarkedIDs() == null){
				
				List<String> newList = new ArrayList<String>();
				newList.add(questionId);
				
				appUser.setBookmarkedIDs(newList);
			}
			
			//else search if the questionid already exists and add it if not
			else{
				if (!appUser.getBookmarkedIDs().contains(questionId)){
					appUser.getBookmarkedIDs().add(questionId);
				}
			}
		}
		
		
		
		//if bookmark flag is false, remove
		else{
			//If bookmarkedId list is null or empty, can't remove anything
			if (appUser.getBookmarkedIDs() != null && !appUser.getBookmarkedIDs().isEmpty()){
				if (appUser.getBookmarkedIDs().contains(questionId)){
					appUser.getBookmarkedIDs().remove(questionId);
				}
			}
		}
		
		//Save this updated user and return
		
		appUserRepository.save(appUser);
		return appUser;
	}

}

















