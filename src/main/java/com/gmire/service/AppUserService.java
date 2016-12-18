package com.gmire.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gmire.model.AppUser;
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
		
		return retUser;
	}

}
