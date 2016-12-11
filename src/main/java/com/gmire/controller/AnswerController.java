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

import com.gmire.model.Answer;
import com.gmire.model.Question;
import com.gmire.service.AnswerService;
import com.gmire.service.QuestionService;

@RestController
public class AnswerController {
	
	@Autowired
	QuestionService questionService;

	@Autowired
	AnswerService answerService;
	
	// Add to question's answerList when answer is submitted
		@RequestMapping(value = "/question/{questionid}/answers", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
		public ResponseEntity<Question> addAnswersForQuestionId(@PathVariable("questionid") String id,
				@RequestBody Answer answer) {

			Question updateQuestion = questionService.addAnswersForQuestionId(id, answer);

			if (updateQuestion == null)
				return new ResponseEntity<Question>(HttpStatus.INTERNAL_SERVER_ERROR);

			return new ResponseEntity<Question>(updateQuestion, HttpStatus.OK);
			// TODO: Also, update User's statistics on the fly

		}
		
		// Get all answers for a given question
		@RequestMapping(value = "/question/{id}/answers", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
		public ResponseEntity<List<Answer>> getAnswersforGivenQuestion(@PathVariable("id") String id) {
			List<Answer> answer = answerService.findAll(id);
			return new ResponseEntity<List<Answer>>(answer, HttpStatus.OK);
		}
}
