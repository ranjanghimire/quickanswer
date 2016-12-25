package com.gmire.helper;

import java.util.ArrayList;
import java.util.List;

import com.gmire.model.Question;

public class QuestionHelper {

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

}
