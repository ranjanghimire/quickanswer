package com.gmire.helper;

import java.util.ArrayList;
import java.util.List;

import com.gmire.dto.WordSearchDto;
import com.gmire.model.Question;

public class DtoHelper {

	public static List<WordSearchDto> transform(List<Question> retQuestions) {
		// For each question, copy to DTO and return
		
		List<WordSearchDto> wtoList = new ArrayList<WordSearchDto>();
		
		for (Question question : retQuestions){			
			WordSearchDto wto = new WordSearchDto();
			wto.setId(question.getId());
			wto.setCategory(question.getCategory());
			wto.setMainQuestion(question.getMainQuestion());
			wto.setTopic(question.getTopic());
			wtoList.add(wto);			
		}
		
		return wtoList;
	}

}
