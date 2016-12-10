package com.gmire.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gmire.model.Question;
import com.gmire.repository.QuestionRepository;

@Service
public class QuestionService {
	
	@Autowired
	private QuestionRepository qRepo;
	
	public List<Question> findByMainQuestionIgnoreCaseLike(String question){
		List<Question> retList = new ArrayList<Question>();
		retList = qRepo.findByMainQuestionIgnoreCaseLike(question);
		return retList;
	}
	
	public List<Question> findAll(){
		return qRepo.findAll();
	}

}
