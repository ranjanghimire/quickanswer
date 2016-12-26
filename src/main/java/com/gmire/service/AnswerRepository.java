package com.gmire.service;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.gmire.model.Answer;

public interface AnswerRepository extends PagingAndSortingRepository<Answer, String> {
	public List<Answer> findByIdIn(List<String> ids);
}
