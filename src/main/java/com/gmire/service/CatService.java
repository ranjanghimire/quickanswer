package com.gmire.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gmire.model.AppCategory;
import com.gmire.repository.CatRepository;

@Service
public class CatService {
	
	@Autowired
	private CatRepository catRepo;

	public List<AppCategory> findAllCategories() {

		List<AppCategory> appCategories = catRepo.findByOrderByCategory();
		
		return appCategories;
	}

	public void deleteByCategory(String category) {

		catRepo.deleteByCategory(category);
		
	}

	public void deleteAll() {
		catRepo.deleteAll();
		
	}

	public AppCategory create(AppCategory appCategory) {
		AppCategory appCat = catRepo.save(appCategory);
		return appCat;
	}

	public AppCategory updateCategory(AppCategory appCategory) {
		AppCategory appCatPersisted = catRepo.findOne(appCategory.getId());
		if (appCatPersisted == null) {
			// cannot update a user that hasn't been persisted
			return null;
		}
		AppCategory updateCategory = catRepo.save(appCategory);
		return updateCategory;
	}

	
	
}
