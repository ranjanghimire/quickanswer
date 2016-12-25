package com.gmire.helper;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.gmire.model.AppUser;
import com.gmire.repository.AppUserRepository;

public class AnswerHelper {

	@Autowired
	private static AppUserRepository appUserRepository;

	public static void populateAnswerIdIntoAppUser(String id, String userId) {
		// Put id into APPUser's list of replied IDs.
		AppUser appUser = appUserRepository.findOne(userId);

		if (appUser == null)
			return;

		// If the list is empty create one and append,
		// else just append to the existing one
		if (appUser.getRepliedAnswersIDs() == null) {
			List<String> repList = new ArrayList<String>();
			repList.add(id);
			appUser.setRepliedAnswersIDs(repList);
		} else {
			appUser.getRepliedAnswersIDs().add(id);
		}
		// Save the appUser
		appUserRepository.save(appUser);

	}

}
