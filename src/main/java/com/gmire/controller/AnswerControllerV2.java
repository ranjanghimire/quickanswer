package com.gmire.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.gmire.helper.AnswerHelper;
import com.gmire.model.Answer;
import com.gmire.model.Question;
import com.gmire.service.QuestionService;

public class AnswerControllerV2 {

	@Autowired
	QuestionService questionService;
	
	// Add to question's answerList when answer is submitted
	//Also update the answerId to user's repliedAnswer list. 
		@RequestMapping(value = "/question/{questionid}/answers/user/{userId}", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
		public ResponseEntity<Question> addAnswersForQuestionId(@PathVariable("questionid") String id,
				@RequestBody Answer answer, @PathVariable("userId") String userId) {

			Question updateQuestion = questionService.addAnswersForQuestionId(id, answer);

			if (updateQuestion == null)
				return new ResponseEntity<Question>(HttpStatus.INTERNAL_SERVER_ERROR);
			
			AnswerHelper.populateAnswerIdIntoAppUser(answer.getId(), userId);

			return new ResponseEntity<Question>(updateQuestion, HttpStatus.OK);
		}

}
