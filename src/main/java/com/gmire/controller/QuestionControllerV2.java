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

import com.gmire.helper.QuestionHelper;
import com.gmire.model.AppUser;
import com.gmire.model.Question;
import com.gmire.service.AppUserService;
import com.gmire.service.QuestionService;

@RestController
public class QuestionControllerV2 {
	
	@Autowired
	QuestionService questionService;
	
	@Autowired
	AppUserService appUserService;
	
	// Find all questions
		@RequestMapping(value = "/v2/question/userid/{userId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
		public ResponseEntity<List<Question>> findAll(@PathVariable("userId") String userId) {
			List<Question> retQuestions = questionService.findAll();
			if (retQuestions == null) {
				return new ResponseEntity<List<Question>>(HttpStatus.NOT_FOUND);
			}
			
			retQuestions = QuestionHelper.popuLateIsLiked(retQuestions, userId); 
			
			return new ResponseEntity<List<Question>>(retQuestions, HttpStatus.OK);
		}
		
		
		//Find all questions by Topic
		@RequestMapping(value="/v2/question/topic/{topic}/userid/{userId}", method=RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
		public ResponseEntity<List<Question>> findByTopic(@PathVariable("topic") String topic, @PathVariable("userId") String userId){
			List<Question> retQuestions = questionService.findByTopicIgnoreCase(topic);
			
			if (retQuestions == null){
				return new ResponseEntity<List<Question>> (HttpStatus.NOT_FOUND);
			}
			
			retQuestions = QuestionHelper.popuLateIsLiked(retQuestions, userId); 
			
			return new ResponseEntity<List<Question>>(retQuestions, HttpStatus.OK);
		}

		//TODO: Find all questions asked by a given user
		@RequestMapping(value = "/v2/question/user/{userId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
		public ResponseEntity<List<Question>> findAllAskedByThisUser(@PathVariable("userId") String userId) {
			
			AppUser appUser = appUserService.findOneByUserId(userId);
			
			if(appUser == null){
				return new ResponseEntity<List<Question>> (HttpStatus.NOT_FOUND);
			}
						
			List<Question> retQuestions = questionService.findByIdIn(appUser.getAskedQuestionsIDs());
			
			if (retQuestions == null){
				return new ResponseEntity<List<Question>> (HttpStatus.NOT_FOUND);
			}
			
			return new ResponseEntity<List<Question>>(retQuestions, HttpStatus.OK);
		}
		
		
		//Create a new question and update id into AppUser
		@RequestMapping(value = "/v2/question/userid/{userId}", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
		public ResponseEntity<Question> createQuestion(@RequestBody Question question, @PathVariable("userId") String userId) {
			Question savedQuestion = questionService.create(question);
			
			QuestionHelper.populateQuestionIdIntoAppUser(question.getId(), userId);
			
			return new ResponseEntity<Question>(savedQuestion, HttpStatus.CREATED);
		}
		
		
		
		
		
	
}
