package com.gmire.controller;

import java.util.ArrayList;
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

import com.gmire.model.Answer;
import com.gmire.model.AppUser;
import com.gmire.model.Notification;
import com.gmire.model.Question;
import com.gmire.repository.AppUserRepository;
import com.gmire.service.NotificationService;
import com.gmire.service.QuestionService;

@RestController
public class AnswerControllerV2 {

	@Autowired
	QuestionService questionService;
	
	@Autowired
	AppUserRepository appUserRepository;
	
	@Autowired
	NotificationService notiService;
	
	//v2/answer/userid' + id
	//Increment likes of answer
	@RequestMapping(value="/v2/answer/userid/{userId}", method=RequestMethod.PUT, produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Question> incrementAnswerLikes(@RequestBody Answer answer, @PathVariable("userId") String userId){
		
		Question incQuestion = questionService.incrementAnswerLikes(answer, userId);
		
		return new ResponseEntity<Question>(incQuestion, HttpStatus.OK);
	}
	
	// Add to question's answerList when answer is submitted
	//Also update the answerId to user's repliedAnswer list. 
		@RequestMapping(value = "/v2/question/{questionid}/answers/user/{userId}", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
		public ResponseEntity<Question> addAnswersForQuestionId(@PathVariable("questionid") String id,
				@RequestBody Answer answer, @PathVariable("userId") String userId) {

			Question updateQuestion = questionService.addAnswersForQuestionId(id, answer);

			if (updateQuestion == null)
				return new ResponseEntity<Question>(HttpStatus.INTERNAL_SERVER_ERROR);
			
			populateAnswerIdIntoAppUser(answer.getAnswerId(), userId);
			
			//Send notifications to the author and likedUsers
			//Args: questionId, fromUserId, notiType
			notiService.createNotification(id, userId, "answer");			

			return new ResponseEntity<Question>(updateQuestion, HttpStatus.OK);
		}
		
		public void populateAnswerIdIntoAppUser(String id, String userId) {
			// Put id into APPUser's list of replied IDs.
			AppUser appUser = appUserRepository.findOne(userId);

			if (appUser == null)
				return;

			// If the list is empty create one and append,
			// else just append to the existing one
			if (appUser.getRepliedAnswersIDs() == null) {
				List<String> repList = new ArrayList<String>();
				repList.add(id);
				appUser.setRepliedAnswersIDs(repList);
			} else {
				appUser.getRepliedAnswersIDs().add(id);
			}
			// Save the appUser
			appUserRepository.save(appUser);

		}
		
		//delete all notifications
		@RequestMapping(value = "/v2/notification", method = RequestMethod.DELETE)
		public ResponseEntity<Notification> deleteAllNotifications() {
			notiService.deleteAll();
			return new ResponseEntity<Notification>(HttpStatus.NO_CONTENT);
		}

}
