package com.gmire.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.gmire.model.AppUser;
import com.gmire.model.Notification;
import com.gmire.model.Question;
import com.gmire.repository.NotificationRepository;

@Service
public class NotificationService {
	
	@Autowired
	private NotificationRepository notiRepo;
	
	@Autowired
	AppUserService appUserService;
	
	@Autowired
	QuestionService qService;
	
	@Async
	public void createNotification(String questionId, String userId, String notificationType){
		
		AppUser appUser = appUserService.findOneByUserId(userId);
		
		//Get Question from questionId
		Question question = qService.findById(questionId);
		
		//Create a new notification based on these
		Notification notification = new Notification();
		
		notification.setFromUserId(userId);
		notification.setFromUserName(appUser.getUserName());
		notification.setNotificationType(notificationType);

		List<String> toList = new ArrayList<String> ();
		if (question.getLikedByUserIDs() != null && !question.getLikedByUserIDs().isEmpty()){
			toList.addAll(question.getLikedByUserIDs());
		}
		
		toList.add(question.getAuthor().getAppUserId());
		
		notification.setQuestion(question);
		notification.setNotificationTime(new Date());
		
		notification.setToUserId(toList);
				
		//Save the notification		
		notiRepo.save(notification);
	}

	public List<Notification> getAllNotifications(String userId, Pageable pageable) {
		
		List<Notification> notifications = notiRepo.findByToUserIdOrderByNotificationTimeDesc(userId, pageable);
		
		return notifications;
	}
	
	public void deleteAll() {
		notiRepo.deleteAll();
	}

}
