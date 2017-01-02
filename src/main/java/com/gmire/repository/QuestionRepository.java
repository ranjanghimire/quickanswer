package com.gmire.repository;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.gmire.model.Question;

@Repository
public interface QuestionRepository extends PagingAndSortingRepository<Question, String> {

	public List<Question> findByMainQuestionIgnoreCaseLike(String question);
	
	public List<Question> findByTopicIgnoreCaseOrderByVotesDesc(String topic);
	
	public List<Question> findByTopicIgnoreCaseLike(String word);
	
	public List<Question> findByCategoryIgnoreCaseLike(String word);
	
	public List<Question> findByCategoryIgnoreCaseOrderByVotesDesc(String category);

	public List<Question> findAll();
	
	public List<Question> findTop10ByOrderByIdDesc();
	
	public Question findById(String questionId);

	public List<Question> findByIdIn(List<String> askedQuestionsIDs);

	public List<Question> findByAnswersIdIn(List<String> answers);

	public List<Question> findAllByOrderByVotesDesc();

	public Long countByAuthorAppUserId(String userId);

	public Long countByAnswersAuthorAppUserId(String userId);

	public List<Question> findByAnswersAuthorAppUserId(String userId);

	public List<Question> findByAnswersIsNull();

	

}
