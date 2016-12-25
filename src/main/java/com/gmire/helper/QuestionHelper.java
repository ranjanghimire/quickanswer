package com.gmire.helper;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.gmire.model.AppUser;
import com.gmire.model.Question;
import com.gmire.repository.AppUserRepository;

public class QuestionHelper {
	
	@Autowired
	private static AppUserRepository appUserRepository;

	public static List<Question> popuLateIsLiked(List<Question> retQuestions, String userId) {
		
		List<Question> que = new ArrayList<Question>();
		
		for (Question q: retQuestions){
			if (q.getLikedByUserIDs() != null && !q.getLikedByUserIDs().isEmpty()){
				if (q.getLikedByUserIDs().contains(userId)){
					q.setLiked(true);
					que.add(q);
				}
				else{
					q.setLiked(false);
					que.add(q);
				}
			}
			else{
				que.add(q);
			}
			
		}
		return que;
	}

	public static void populateQuestionIdIntoAppUser(String id, String userId) {
		//Put id into APPUser's list of asked IDs.
		AppUser appUser = appUserRepository.findOne(userId);
		
		if(appUser == null)
			return;
		
		//If the list is empty create one and append, 
		//else just append to the existing one
		if(appUser.getAskedQuestionsIDs() == null){
			List<String> askList = new ArrayList<String>();
			askList.add(id);
			appUser.setAskedQuestionsIDs(askList);
		}
		else{
			appUser.getAskedQuestionsIDs().add(id);
		}
		//Save the appUser
		appUserRepository.save(appUser);
	}

}
