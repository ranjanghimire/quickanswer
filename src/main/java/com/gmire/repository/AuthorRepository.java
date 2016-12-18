package com.gmire.repository;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.gmire.model.Author;

@Repository
public interface AuthorRepository extends PagingAndSortingRepository<Author, String> {

	public List<Author> findAll();
	public Author findOneByAppUserId(String userId);	
	
}

