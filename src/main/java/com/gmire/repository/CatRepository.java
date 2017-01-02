package com.gmire.repository;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.gmire.model.AppCategory;

@Repository
public interface CatRepository extends PagingAndSortingRepository<AppCategory, String> {
	public List<AppCategory> findByOrderByCategory();

	public void deleteByCategory(String category); 
}
