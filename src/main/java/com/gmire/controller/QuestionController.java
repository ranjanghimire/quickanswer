package com.gmire.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.gmire.model.Author;
import com.gmire.model.Question;
import com.gmire.repository.QuestionRepository;
import com.gmire.service.QuestionService;

@RestController("/")
public class QuestionController {

	@Autowired
	QuestionService questionService;

	@Autowired
	QuestionRepository qRepo;

	@RequestMapping(value = "question", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Question>> findAll() {
		List<Question> retQuestion = questionService.findAll();
		if (retQuestion == null) {
			return new ResponseEntity<List<Question>>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<List<Question>>(retQuestion, HttpStatus.OK);
	}

	@RequestMapping(value = "question/{question}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Question>> findByMainQuestion(@PathVariable String question) {

		qRepo.deleteAll();

		qRepo.save(new Question("hello question", new Author("Samuel")));
		qRepo.save(new Question("testing"));

		List<Question> retQuestion = questionService.findByMainQuestionIgnoreCaseLike(question);

		if (retQuestion == null) {
			return new ResponseEntity<List<Question>>(HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<List<Question>>(retQuestion, HttpStatus.OK);
	}

}
