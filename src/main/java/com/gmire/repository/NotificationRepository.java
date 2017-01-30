package com.gmire.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.gmire.model.Notification;

public interface NotificationRepository extends PagingAndSortingRepository<Notification, String>{

	List<Notification> findByToUserIdOrderByNotificationTimeDesc(String userId, Pageable pageable);

}
