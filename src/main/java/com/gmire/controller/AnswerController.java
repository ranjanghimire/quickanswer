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

	// delete an answer for a given question
	@RequestMapping(value = "/question/{questionid}/answers/{answerid}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Question> deleteAnswerForQuestionId(@PathVariable("questionid") String questionId,
			@PathVariable("answerid") String answerId) {

		Question retQuestion = questionService.deleteAnswerForQuestion(questionId, answerId);

		if (retQuestion == null) {
			return new ResponseEntity<Question>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return new ResponseEntity<Question>(retQuestion, HttpStatus.OK);
	}

	// TODO: Add APIs for updating an answer
	@RequestMapping(value = "/question/{questionid}/answers", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Question> updateAnswersForQuestionId(@PathVariable("questionid") String questionId, @RequestBody Answer answer) {

		if (answer.getId() == null){
			return new ResponseEntity<Question>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		Question retQuestion = questionService.updateAnswerforQuestionId(questionId, answer);
		if (retQuestion == null) {
			return new ResponseEntity<Question>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return new ResponseEntity<Question>(retQuestion, HttpStatus.OK);
	}

}