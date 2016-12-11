package com.gmire.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gmire.model.Answer;
import com.gmire.repository.QuestionRepository;

@Service
public class AnswerService {

	@Autowired
	private QuestionRepository qRepo;

	public List<Answer> findAll(String questionId) {
		// TODO: Handle errors if id is null
		List<Answer> answers = qRepo.findOne(questionId).getAnswers();

		return answers;
	}

}
