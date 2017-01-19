package com.gmire.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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

}
