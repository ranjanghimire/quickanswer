package com.gmire.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gmire.model.Answer;
import com.gmire.model.Author;
import com.gmire.model.Question;
import com.gmire.repository.QuestionRepository;

@Service
public class QuestionService {

	@Autowired
	private QuestionRepository qRepo;

	@Autowired
	private AuthorService authorService;
	

	public List<Question> findByMainQuestionIgnoreCaseLike(String question) {
		List<Question> retList = new ArrayList<Question>();
		retList = qRepo.findByMainQuestionIgnoreCaseLike(question);
		return retList;
	}

	public List<Question> findAll() {
		return qRepo.findAll();
	}

	public Question create(Question question) {

		// If id of author id is not present, search for Author with given
		// appUserId
		// If found, use existing author id. If not create a new objectId.

		if (question.getAuthor().getId() == null) {
			Author author = authorService.findOneByAppUserId(question.getAuthor().getAppUserId());
			if (author != null) { // Create new object id and assign it to the
									// author
				question.getAuthor().setId(author.getId());
			} else {
				question.getAuthor().setId(new ObjectId().toString());
			}
		}

		Question savedQuestion = qRepo.save(question);
		return savedQuestion;
	}

	public Question addAnswersForQuestionId(String id, Answer answer) {
		Question questionPersisted = qRepo.findOne(id);
		if (questionPersisted == null) {
			return null;
		}

		if (answer.getId() == null) {
			answer.setId(new ObjectId().toString());
		}

		// If id of author id is not present, search for Author with given
		// appUserId
		// If found, use existing author id. If not create a new objectId.
		if (answer.getAuthor() != null) {
			if (answer.getAuthor().getId() == null) {
				Author author = authorService.findOneByAppUserId(answer.getAuthor().getAppUserId());
				if (author != null) {
					answer.getAuthor().setId(author.getId());
				} else {
					answer.getAuthor().setId(new ObjectId().toString());
				}
			}
		}

		// If the list of answers is empty, it will throw NPE
		if (questionPersisted.getAnswers() == null) {
			List<Answer> ans = new ArrayList<Answer>();
			ans.add(answer);
			questionPersisted.setAnswers(ans);
		} else {
			questionPersisted.getAnswers().add(answer);
		}

		Question updateQuestion = qRepo.save(questionPersisted);

		return updateQuestion;
	}

	public void delete(String id) {
		qRepo.delete(id);
	}

	public void deleteAll() {
		qRepo.deleteAll();
	}

	public Question update(Question question) {
		Question questionPersisted = qRepo.findOne(question.getId());
		if (questionPersisted == null) {
			// cannot update question that hasn't been persisted
			return null;
		}
		Question updateQuestion = qRepo.save(question);
		return updateQuestion;
	}

	public Question deleteAnswerForQuestion(String questionId, String answerId) {

		Question question = qRepo.findById(questionId);

		if (question == null) {
			return null;
		}

		Iterator<Answer> itr = question.getAnswers().iterator();

		while (itr.hasNext()) {
			Answer remAns = (Answer) itr.next();
			if (remAns.getId().equals(answerId)) {
				itr.remove();
				break;
			}
		}

		Question retQuestion = qRepo.save(question);

		return retQuestion;
	}

	public Question updateAnswerforQuestionId(String questionId, Answer answer) {

		Question question = qRepo.findById(questionId);

		if (question == null) {
			return null;
		}

		Iterator<Answer> itr = question.getAnswers().iterator();

		String answerId = answer.getId();

		while (itr.hasNext()) {
			Answer remAns = (Answer) itr.next();
			if (remAns.getId().equals(answerId)) {
				itr.remove();
				answer.setId(answerId);
				question.getAnswers().add(answer);
				break;
			}
		}

		Question retQuestion = qRepo.save(question);

		return retQuestion;
	}

	public Question deleteAllAnswersForQuestionId(String questionId) {

		Question question = qRepo.findById(questionId);

		if (question == null) {
			return null;
		}

		question.getAnswers().clear();

		Question retQuestion = qRepo.save(question);

		return retQuestion;
	}

	public List<Question> findByTopicIgnoreCase(String topic) {

		List<Question> retList = new ArrayList<Question>();
		retList = qRepo.findByTopicIgnoreCase(topic);
		return retList;
	}

	public Question incrementLikes(Question question, String userId) {
		//Find the question by given id. 
		Question incQuestion = qRepo.findOne(question.getId());
		
		//Save the likesCount to it. 
		if(question.getVotes() != null && question.getVotes() > 0){
			incQuestion.setVotes(question.getVotes());
		}
				
		//Add userId to the list likedByUserIDs.
		
		if(incQuestion.getLikedByUserIDs() == null){
			List<String> newUserList = new ArrayList<String>();
			newUserList.add(userId);
			incQuestion.setLikedByUserIDs(newUserList);
		}
		else{
			if (!incQuestion.getLikedByUserIDs().contains(userId))
				incQuestion.getLikedByUserIDs().add("userId");	
		}
		
		//save the incQuestion
		return qRepo.save(incQuestion);
		
		//return the updated question.
	}

	public List<Question> findByIdIn(List<String> askedQuestionsIDs) {

		if (askedQuestionsIDs == null || askedQuestionsIDs.isEmpty()){
			return null;
		}
		
		List<Question> retQuestions = qRepo.findByIdIn(askedQuestionsIDs);
		
		return retQuestions;
	}


}
