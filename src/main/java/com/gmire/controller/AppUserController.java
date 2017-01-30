package com.gmire.controller;

import java.util.List;
import java.util.Random;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.gmire.model.AppUser;
import com.gmire.model.Notification;
import com.gmire.model.RegistrationCode;
import com.gmire.service.AppUserService;
import com.gmire.service.EmailService;
import com.gmire.service.NotificationService;
import com.gmire.service.RegistrationCodeService;

@RestController
public class AppUserController {
	
	private static Logger LOG = LoggerFactory.getLogger(AppUserController.class);
	
	@Value("${spring.mail.username}")
    private String fromEmail;

	@Autowired
	private AppUserService appUserService;
	
	@Autowired
	private EmailService emailService;
	
	@Autowired
	private RegistrationCodeService registrationCodeService;
	
	@Autowired
	private NotificationService notificationService;
	
	
	//Get all notifications for this user
	@RequestMapping(value="/user/notification/{userId}", method=RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Notification>> getNotifications(@PathVariable("userId") String userId, Pageable pageable){
		
		List<Notification> noti = notificationService.getAllNotifications(userId, pageable);
		
		if (noti == null){
			return new ResponseEntity<List<Notification>>(HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<List<Notification>>(noti, HttpStatus.OK);
	}
	
	//Send email when 'Forgot Password'
	@RequestMapping(value="/user/forgot/{email}", method=RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Boolean> sendForgotEmail(@PathVariable("email") String email){
		
		Boolean retValue = true;
		
		//Validate email
		if(!isValidEmailAddress(email)){
			return new ResponseEntity<Boolean>(false, HttpStatus.OK); 
		}
		
		//Generate a random 6 digit code
		String code  = generateRandomString();	
		
		RegistrationCode regCode = new RegistrationCode();
		regCode.setCode(code);
		regCode.setEmailAddress(email);
		regCode.setActive(true);
				
		//Save this code somewhere (Create a new model 'RegistrationCode')
		registrationCodeService.save(regCode);
		
		//Send email with the generated code
		String subject="Forgot Password - Quick Answer";
		String text = "Your temporary code is: \"" + code + "\". Please enter it in the app to reset the password.";
		
		try{
			emailService.sendNotification(fromEmail, email, subject, text);
		}
		catch(Exception e){
			LOG.info(e.getMessage());
			retValue = false;
		}
		
		return new ResponseEntity<Boolean>(retValue, HttpStatus.OK); 
	}
	
	//Upon code submission, validate the code and send boolean response
	@RequestMapping(value="/user/email/{email}/code/{code}", method=RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Boolean> validateRegCode(@PathVariable("email") String email, @PathVariable("code") String code){
		Boolean retValue = true;
		//Validate code
		if (code == null || code.equals("") || code.length() < 6){
			return new ResponseEntity<Boolean>(false, HttpStatus.OK);
		}
		
		//Retrieve the regCode from db and validate
		RegistrationCode regCode = registrationCodeService.findByEmail(email);
		
		if (regCode == null){
			return new ResponseEntity<Boolean>(false, HttpStatus.OK);
		}
		
		if (!code.equals(regCode.getCode())){
			retValue = false;
		}
		
		//Send true if exists
		return new ResponseEntity<Boolean>(retValue, HttpStatus.OK); 
	}

	//Update user's password by username
	@RequestMapping(value="/user/{username}/{password}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<AppUser> updatePassword(@PathVariable("username") String userName, @PathVariable("password")String password){
		
		if (userName.equals("") || userName == null || password.equals("") || password == null){
			return new ResponseEntity<AppUser>(HttpStatus.NOT_FOUND);
		}
		
		AppUser retUser = appUserService.updateUserPassword(userName, password);
		
		if (retUser == null) {
			return new ResponseEntity<AppUser>(HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<AppUser>(retUser, HttpStatus.OK);
	}
	
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
	
	//Retrieve a username by provided appUserId
	@RequestMapping(value = "/user/userId/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<AppUser> findOneByUserId(@PathVariable("id") String id){
		
		AppUser retUser = appUserService.findOneByUserId(id);
		
		if (retUser == null){
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
	
	//Update FollowedTopics in AppUser when someone follows a topic
	//Also update that topic in the desired topic as well
	//Should be able to remove as well
	//Accept parameters: listOfTopics, userId, followFlag(true/false -> Add / Remove from list)
	@RequestMapping(value="/user/userid/{userId}/follow/{flag}", method=RequestMethod.PUT, consumes=MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<AppUser> followTopics(@PathVariable("userId") String userId, @PathVariable("flag") boolean followFlag, @RequestBody List<String> topicList){
		
		AppUser updateUser = appUserService.followTopics(userId, followFlag, topicList);
		
		if (updateUser == null) {
			return new ResponseEntity<AppUser>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<AppUser>(updateUser, HttpStatus.OK);
	}
	
	//Bookmark a question
	//Accept parameters: questionId, userId, bookmarkFlag
	//Return AppUser
	@RequestMapping(value="/user/userid/{userId}/questionid/{questionId}/bookmark/{flag}", method=RequestMethod.PUT, consumes=MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE )
	public ResponseEntity<AppUser> bookmarkQuestion(@PathVariable("userId") String userId, 
			@PathVariable("questionId") String questionId, @PathVariable("flag") boolean bookmarkFlag ){
		
		AppUser updateUser = appUserService.bookmarkQuestion(userId, questionId, bookmarkFlag);
		
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
	
	public static boolean isValidEmailAddress(String email) {
	   boolean result = true;
	   try {
	      InternetAddress emailAddr = new InternetAddress(email);
	      emailAddr.validate();
	   } catch (AddressException ex) {
	      result = false;
	   }
	   return result;
	}
	
	public String generateRandomString(){
		
		String CHAR_LIST = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        
	    StringBuffer randStr = new StringBuffer();
	    Random random = new Random();
	    
	    for(int i=0; i< 6; i++){
	        int number = random.nextInt(36);
	        char ch = CHAR_LIST.charAt(number);
	        randStr.append(ch);
	    }
	    return randStr.toString();
    }

}
