package com.gmire.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.gmire.model.AppUser;
import com.gmire.service.AppUserService;

@RestController
public class AppUserController {

	@Autowired
	private AppUserService appUserService;

	//Authenticate a username with the provided password
	//Return the user if it exists, otherwise NOT FOUND	
	@RequestMapping(value = "/user/{username}/{password}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<AppUser> findByUserNameAndPassword(@PathVariable("username") String userName, @PathVariable("password") String password) {

		AppUser retUser = appUserService.findByUserNameAndPassword(userName, password);

		if (retUser == null) {
			return new ResponseEntity<AppUser>(HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<AppUser>(retUser, HttpStatus.OK);
	}
	
	//Search user by username
	@RequestMapping(value = "/user/{username}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<AppUser>> findByUserName(@PathVariable("username") String userName) {

		List<AppUser> retUsers = appUserService.findByUserNameIgnoreCaseLike(userName);

		if (retUsers == null) {
			return new ResponseEntity<List<AppUser>>(HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<List<AppUser>>(retUsers, HttpStatus.OK);
	}
	
	//Retrieve all users
	@RequestMapping(value = "/user", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<AppUser>> findAll() {
		List<AppUser> retUser = appUserService.findAll();
		if (retUser == null) {
			return new ResponseEntity<List<AppUser>>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<List<AppUser>>(retUser, HttpStatus.OK);
	}
	
	//Update a user
	@RequestMapping(value = "/user/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<AppUser> updateUser(@RequestBody AppUser appUser) {
		AppUser updateUser = appUserService.update(appUser);
		if (updateUser == null) {
			return new ResponseEntity<AppUser>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<AppUser>(updateUser, HttpStatus.OK);
	}
	
	// Delete a User by id
	@RequestMapping(value = "/user/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<AppUser> deleteUser(@PathVariable("id") String id) {
		appUserService.delete(id);
		return new ResponseEntity<AppUser>(HttpStatus.NO_CONTENT);
	}

	// delete all Users
	@RequestMapping(value = "/user", method = RequestMethod.DELETE)
	public ResponseEntity<AppUser> deleteAllUsers() {
		appUserService.deleteAll();
		return new ResponseEntity<AppUser>(HttpStatus.NO_CONTENT);
	}

	// Create a User
	@RequestMapping(value = "/user", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<AppUser> createUser(@RequestBody AppUser appUser) {
		AppUser savedUser = appUserService.create(appUser);
		return new ResponseEntity<AppUser>(savedUser, HttpStatus.CREATED);
	}

}
