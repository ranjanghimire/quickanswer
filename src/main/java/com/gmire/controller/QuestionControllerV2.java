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
	
	//Find only top N topics from all questions
		@RequestMapping(value="/v2/topics/ten", method=RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
		public ResponseEntity<List<String>> findAllTopicsTen(){
			
			List<String> retTopics = questionService.findAllTopicsTen();
			
			if (retTopics == null) {
				return new ResponseEntity<List<String>>(HttpStatus.NOT_FOUND);
			}
			
			return new ResponseEntity<List<String>>(retTopics, HttpStatus.OK);
		}
	
	//Find all topics from all questions
		@RequestMapping(value="/v2/topics", method=RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
		public ResponseEntity<List<String>> findAllTopics(){
			
			List<String> retTopics = questionService.findAllTopics();
			
			if (retTopics == null) {
				return new ResponseEntity<List<String>>(HttpStatus.NOT_FOUND);
			}
			
			return new ResponseEntity<List<String>>(retTopics, HttpStatus.OK);
		}
	
	// Find all questions
		@RequestMapping(value = "/v2/question/userid/{userId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
		public ResponseEntity<List<Question>> findAll(@PathVariable("userId") String userId) {
			List<Question> retQuestions = questionService.findAll();
			if (retQuestions == null) {
				return new ResponseEntity<List<Question>>(HttpStatus.NOT_FOUND);
			}
			
			retQuestions = popuLateIsLiked(retQuestions, userId); 
			
			return new ResponseEntity<List<Question>>(retQuestions, HttpStatus.OK);
		}
		
		
		//Find all questions by Topic
		@RequestMapping(value="/v2/question/topic/{topic}/userid/{userId}", method=RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
		public ResponseEntity<List<Question>> findByTopic(@PathVariable("topic") String topic, @PathVariable("userId") String userId){
			List<Question> retQuestions = questionService.findByTopicIgnoreCase(topic);
			
			if (retQuestions == null){
				return new ResponseEntity<List<Question>> (HttpStatus.NOT_FOUND);
			}
			
			retQuestions = popuLateIsLiked(retQuestions, userId); 
			
			return new ResponseEntity<List<Question>>(retQuestions, HttpStatus.OK);
		}

		//Find all questions asked by a given user
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
		
		
		//TODO: Find all questions given answerIDs. Derive answerIDs from User
		//TODO: NEW: Change the AppUser Model to use repliedQuestions instead of repliedAnswerList. 
		//Then use below method to easily get list of questions and return.
		@RequestMapping(value = "/v2/question/answer/user/{userId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
		public ResponseEntity<List<Question>> findAllQuestionForAnswerIDsByThisUser(@PathVariable("userId") String userId){
			
			AppUser appUser = appUserService.findOneByUserId(userId);
			
			if(appUser == null){
				return new ResponseEntity<List<Question>> (HttpStatus.NOT_FOUND);
			}
			
			List<String> answerIds = appUser.getRepliedAnswersIDs();
			
			List<Question> retQuestions = questionService.findByAnswersIdIn(answerIds);
			
			if (retQuestions == null){
				return new ResponseEntity<List<Question>> (HttpStatus.NOT_FOUND);
			}
			
			return new ResponseEntity<List<Question>>(retQuestions, HttpStatus.OK);
		}
		
		
		
		//Create a new question and update id into AppUser
		@RequestMapping(value = "/v2/question/userid/{userId}", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
		public ResponseEntity<Question> createQuestion(@RequestBody Question question, @PathVariable("userId") String userId) {
			
			System.out.println(userId);
			
			Question savedQuestion = questionService.create(question);
			
			populateQuestionIdIntoAppUser(question.getId(), userId);
			
			return new ResponseEntity<Question>(savedQuestion, HttpStatus.CREATED);
		}
		
		public void populateQuestionIdIntoAppUser(String id, String userId) {
			//Put id into APPUser's list of asked IDs.
			System.out.println(userId);
			AppUser appUser = appUserService.findOneByUserId(userId);
			
			if(appUser == null)
				return;
			
			//If the list is empty create one and append, 
			//else just append to the existing one
			if(appUser.getAskedQuestionsIDs() == null){
				List<String> askList = new ArrayList<String>();
				askList.add(id);
				appUser.setAskedQuestionsIDs(askList);
			}
			else{
				appUser.getAskedQuestionsIDs().add(id);
			}
			//Save the appUser
			appUserService.create(appUser);
		}
		
		public List<Question> popuLateIsLiked(List<Question> retQuestions, String userId) {
			
			List<Question> que = new ArrayList<Question>();
			
			for (Question q: retQuestions){
				if (q.getLikedByUserIDs() != null && !q.getLikedByUserIDs().isEmpty()){
					if (q.getLikedByUserIDs().contains(userId)){
						q.setLiked(true);
						que.add(q);
					}
					else{
						q.setLiked(false);
						que.add(q);
					}
				}
				else{
					que.add(q);
				}
				
			}
			return que;
		}
		
		
	
}
