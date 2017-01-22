package com.gmire.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.gmire.dto.CategoryDto;
import com.gmire.dto.WordSearchDto;
import com.gmire.helper.DtoHelper;
import com.gmire.model.Answer;
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
	
	//Return count of questions asked by a user
	@RequestMapping (value="/v2/question/count/userid/{userId}", method=RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Long> countQuestionByUser(@PathVariable("userId") String userId){
		Long retValue = questionService.countByAuthorAppUserId(userId);
		
		if (retValue == null){
			return new ResponseEntity<Long>(HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<Long>(retValue, HttpStatus.OK);
	}
	
	//Return count of answers replied by a user
	@RequestMapping(value="/v2/question/answer/count/userid/{userId}", method=RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Long> countAnswersByUser(@PathVariable("userId") String userId){
		Long retValue = questionService.countByAnswersAuthorAppUserId(userId);
		
		if (retValue == null){
			return new ResponseEntity<Long>(HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<Long>(retValue, HttpStatus.OK);
	}
	
	//Search by given word
	//Display top 10 topics, 5 categories and 10 questions
	//Bottomline, return combined questions
	@RequestMapping(value="/v2/topics/search/{word}", method=RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<WordSearchDto>> searchByWord(@PathVariable("word") String word){
		
		List<Question> retQuestions = questionService.searchByWord(word);
		
		if (retQuestions == null) {
			return new ResponseEntity<List<WordSearchDto>>(HttpStatus.NOT_FOUND);
		}
		
		List<WordSearchDto> wto = DtoHelper.transform(retQuestions);
		
		return new ResponseEntity<List<WordSearchDto>>(wto, HttpStatus.OK);	
	}
	
	//Topics Page Category Implementation. Return Category and its topics
	@RequestMapping(value="/v2/category/{category}", method=RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<CategoryDto>> listTopicsByCategory(@PathVariable("category") String category){
		List<Question> retQuestions = questionService.findByCategoryIgnoreCase(category);
		
		if (retQuestions == null) {
			return new ResponseEntity<List<CategoryDto>>(HttpStatus.NOT_FOUND);
		}
		
		List<CategoryDto> categoryDtoList = populateCategoryDto(retQuestions);
		
		if (categoryDtoList == null || categoryDtoList.isEmpty()){
			return new ResponseEntity<List<CategoryDto>>(HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<List<CategoryDto>>(categoryDtoList, HttpStatus.OK);
	}
	
	//TODO: Return top N categories with N or less topics each.
	@RequestMapping(value="/v2/category", method=RequestMethod.GET, produces= MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<CategoryDto>> listTopTopicsByTopCategory(){
		
		List<Question> retQuestions = questionService.findDistinctTop500ByOrderByVotesDesc();
		
		if (retQuestions == null) {
			return new ResponseEntity<List<CategoryDto>>(HttpStatus.NOT_FOUND);
		}
		
		List<CategoryDto> categoryDtoList = populateCategoryDto(retQuestions);
		
		return new ResponseEntity<List<CategoryDto>>(categoryDtoList, HttpStatus.OK);
	}
	
	//Report a question
	@RequestMapping(value="/v2/question/{questionId}/report", method=RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Question> reportQuestion(@PathVariable("questionId") String questionId){
		questionService.reportQuestion(questionId);		
		return new ResponseEntity<Question>(HttpStatus.NO_CONTENT);
	}
	
	
	private List<CategoryDto> populateCategoryDto(List<Question> retQuestions) {

		List<CategoryDto> listDto = new ArrayList<CategoryDto>();
		
		HashMap<String, Set<String>> hashMap = new HashMap<String, Set<String>>();
		
		for (Question question: retQuestions){
			if (question.getCategory() != null){
				if (!hashMap.containsKey(question.getCategory())) {
				    Set<String> list = new HashSet<String>();
				    list.add(question.getTopic());
		
				    hashMap.put(question.getCategory(), list);
				} else {
					//put only 10 topics max
					if (hashMap.get(question.getCategory()).size() < 10)
						hashMap.get(question.getCategory()).add(question.getTopic());
				}
			}
		}
		
		if (hashMap != null && !hashMap.isEmpty()){
		
			for (String key: hashMap.keySet()){
				CategoryDto catDto = new CategoryDto();
				catDto.setCategory(key);
				catDto.setTopics(new ArrayList<String>(hashMap.get(key)));
				listDto.add(catDto);
			}
		}
		
		return listDto;
	
	}

	//Find only top ten topics from all questions
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
		public ResponseEntity<List<Question>> findAll(@PathVariable("userId") String userId, Pageable pageable) {
			List<Question> retQuestions = questionService.findAll(pageable);
			if (retQuestions == null) {
				return new ResponseEntity<List<Question>>(HttpStatus.NOT_FOUND);
			}
			
			retQuestions = popuLateIsLiked(retQuestions, userId); 
			
			return new ResponseEntity<List<Question>>(retQuestions, HttpStatus.OK);
		}
		
		//Find all questions by category
		@RequestMapping(value="/v2/question/category/{category}/userid/{userId}", method=RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
		public ResponseEntity<List<Question>> findByCategory(@PathVariable("category") String category, @PathVariable("userId") String userId){
			
			List<Question> retQuestions = questionService.findByCategoryIgnoreCase(category);
			
			if (retQuestions == null){
				return new ResponseEntity<List<Question>> (HttpStatus.NOT_FOUND);
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
		
		//Find a question by Id
		@RequestMapping(value="/v2/question/{questionId}", method=RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
		public ResponseEntity<Question> findbyId(@PathVariable("questionId") String questionId){
			
			Question question = questionService.findById(questionId);
			
			if (question == null){
				return new ResponseEntity<Question> (HttpStatus.NOT_FOUND);
			}
			
			return new ResponseEntity<Question> (question, HttpStatus.OK);
		}
		
		//Find likes of giver user id
		@RequestMapping(value="/v2/questions/likes/userid/{userId}", method=RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
		public ResponseEntity<List<Question>> findByIds(@PathVariable("userId") String userId){
			List<Question> retQuestions = questionService.findByQuestionIds(userId);
			
			if (retQuestions == null){
				return new ResponseEntity<List<Question>> (HttpStatus.NOT_FOUND);
			}
			
			retQuestions = popuLateIsLiked(retQuestions, userId); 
			
			return new ResponseEntity<List<Question>>(retQuestions, HttpStatus.OK);
			
		}
		
		// Find all unanswered questions
		@RequestMapping(value="/v2/question/unanswered/userid/{userId}", method=RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
		public ResponseEntity<List<Question>> findAllUnansweredQuestions(@PathVariable("userId") String userId) {
			
			//find all questions with no answers
			List<Question> retQuestions = questionService.findByAnswersIsNull();
			
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
			
			retQuestions = popuLateIsLiked(retQuestions, userId); 
			
			return new ResponseEntity<List<Question>>(retQuestions, HttpStatus.OK);
		}
		
		//Find all questions bookmarked by a given user
		@RequestMapping(value="/v2/question/bookmarks/user/{userId}", method=RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
		public ResponseEntity<List<Question>> findAllQuestionsBookmarkedByThisUser(@PathVariable("userId") String userId){
			AppUser appUser = appUserService.findOneByUserId(userId);
			
			if(appUser == null){
				return new ResponseEntity<List<Question>> (HttpStatus.NOT_FOUND);
			}
			
			List<Question> retQuestions = questionService.findByIdIn(appUser.getBookmarkedIDs());
			
			if (retQuestions == null){
				return new ResponseEntity<List<Question>> (HttpStatus.NOT_FOUND);
			}
			
			retQuestions = popuLateIsLiked(retQuestions, userId); 
			
			return new ResponseEntity<List<Question>>(retQuestions, HttpStatus.OK);
			
		}
		
		
		//Find all questions that the user has replied to. Given appUserId. 
		@RequestMapping(value = "/v2/question/answer/user/{userId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
		public ResponseEntity<List<Question>> findAllQuestionForAnswerIDsByThisUser(@PathVariable("userId") String userId){
						
			List<Question> retQuestions = questionService.findByAnswersAuthorAppUserId(userId);
			
			if (retQuestions == null){
				return new ResponseEntity<List<Question>> (HttpStatus.NOT_FOUND);
			}
			
			retQuestions = popuLateIsLiked(retQuestions, userId); 
			
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
			//TODO: check if userId is null
			
			List<Question> que = new ArrayList<Question>();
			
			//populate likes of question
			for (Question q: retQuestions){
				if (q.getLikedByUserIDs() != null && !q.getLikedByUserIDs().isEmpty()){
					if (q.getLikedByUserIDs().contains(userId)){
						q.setLiked(true);
					}
				}
				
				//populate likes of answers
				if(q.getAnswers() != null){
					for (Answer ans : q.getAnswers()){
						if (ans.getLikedByUserIDs() != null && !ans.getLikedByUserIDs().isEmpty()){
							if(ans.getLikedByUserIDs().contains(userId)){
								ans.setLiked(true);
							}
						}
					}
				}
				
				//populate ifFollowed flag for topic
				if (q.getTopic() != null && q.getTopic() != ""){
					//If User's followedTopics contains this question's topic, set followed flag to true
					if (appUserService.findOneByUserId(userId).getFollowedTopics() != null
							&& appUserService.findOneByUserId(userId).getFollowedTopics().contains(q.getTopic())){
						q.setFollowed(true);
					}
					else{
						q.setFollowed(false);
					}
				}
				
				//populate isBookmarked flag
				//If the question id is in the appUser's bookmarkedIds list, set to true
				if(appUserService.findOneByUserId(userId).getBookmarkedIDs() != null &&
						appUserService.findOneByUserId(userId).getBookmarkedIDs().contains(q.getId())){
					q.setBookmarked(true);
				}
				else{
					q.setBookmarked(false);
				}
				
				que.add(q);
			}
			return que;
		}
		
}


