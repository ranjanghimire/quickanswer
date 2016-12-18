package com.gmire.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gmire.model.Author;
import com.gmire.repository.AuthorRepository;

@Service
public class AuthorService {

	@Autowired
	private AuthorRepository authorRepository;

	// Method to get Author by Id
	public Author findOneById(String id) {
		return authorRepository.findOne(id);
	}

	// Method to get Author by AppUserId
	public Author findOneByAppUserId(String appId) {
		return authorRepository.findOneByAppUserId(appId);

	}

}
