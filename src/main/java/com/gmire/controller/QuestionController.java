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

import com.gmire.model.Question;
import com.gmire.service.AnswerService;
import com.gmire.service.QuestionService;

@RestController("/")
public class QuestionController {

	@Autowired
	QuestionService questionService;

	@Autowired
	AnswerService answerService;

	// Update a question
	@RequestMapping(value = "question/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Question> updateQuestion(@RequestBody Question question) {
		Question updateQuestion = questionService.update(question);
		if (updateQuestion == null) {
			return new ResponseEntity<Question>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<Question>(updateQuestion, HttpStatus.OK);
	}

	// Delete All Questions
	@RequestMapping(value = "question", method = RequestMethod.DELETE)
	public ResponseEntity<Question> deleteAllQuestions() {
		questionService.deleteAll();
		return new ResponseEntity<Question>(HttpStatus.NO_CONTENT);
	}

	// Delete a question by id
	@RequestMapping(value = "question/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Question> deleteQuestion(@PathVariable("id") String id) {
		questionService.delete(id);
		return new ResponseEntity<Question>(HttpStatus.NO_CONTENT);
	}

	// Create a Question
	@RequestMapping(value = "question", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Question> createQuestion(@RequestBody Question question) {
		Question savedQuestion = questionService.create(question);
		return new ResponseEntity<Question>(savedQuestion, HttpStatus.CREATED);
		// TODO: Also update user's statistics
	}

	// Find all questions
	@RequestMapping(value = "question", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Question>> findAll() {
		List<Question> retQuestion = questionService.findAll();
		if (retQuestion == null) {
			return new ResponseEntity<List<Question>>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<List<Question>>(retQuestion, HttpStatus.OK);
	}

	// Search by particular question
	@RequestMapping(value = "question/{question}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Question>> findByMainQuestion(@PathVariable String question) {

		List<Question> retQuestion = questionService.findByMainQuestionIgnoreCaseLike(question);

		if (retQuestion == null) {
			return new ResponseEntity<List<Question>>(HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<List<Question>>(retQuestion, HttpStatus.OK);
	}

}
