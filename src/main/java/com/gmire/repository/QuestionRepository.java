package com.gmire.repository;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.gmire.model.Question;

@Repository
public interface QuestionRepository extends PagingAndSortingRepository<Question, String> {

	public List<Question> findByMainQuestionIgnoreCaseLike(String question);
	
	public List<Question> findByTopicIgnoreCase(String topic);

	public List<Question> findAll();

	public Question findById(String questionId);

	public List<Question> findByIdIn(List<String> askedQuestionsIDs);

}
