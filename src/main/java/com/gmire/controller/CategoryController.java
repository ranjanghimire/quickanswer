package com.gmire.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.gmire.model.AppCategory;
import com.gmire.service.CatService;

@RestController
public class CategoryController {

	@Autowired
	private CatService catService;
	
	//find all categories
	@RequestMapping(value = "/v1/category", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<String>> getAllCategories() {
		
		List<AppCategory> appCatList = catService.findAllCategories();
		
		if (appCatList == null){
			return new ResponseEntity<List<String>>(HttpStatus.NOT_FOUND);
		}
		
		List<String> retList = new ArrayList<String> ();
		
		for(AppCategory appCat : appCatList){
			retList.add(appCat.getCategory());
		}		

		return new ResponseEntity<List<String>>(retList, HttpStatus.OK);
	}
	
	//delete a category by category name
	@RequestMapping(value="/v1/category/{category}", method=RequestMethod.DELETE)
	public ResponseEntity<AppCategory> deletCategory(@PathVariable("category") String category){
		catService.deleteByCategory(category);
		return new ResponseEntity<AppCategory> (HttpStatus.NO_CONTENT);
	}
	
	//delete all categories
	@RequestMapping(value="/v1/category", method=RequestMethod.DELETE)
	public ResponseEntity<AppCategory> deleteAllCategories(){
		catService.deleteAll();
		return new ResponseEntity<AppCategory> (HttpStatus.NO_CONTENT);
	}

	//Create a new category
	@RequestMapping(value="/v1/category", method=RequestMethod.POST, consumes=MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<AppCategory> createCategory(@RequestBody AppCategory appCategory){
		AppCategory savedCategory = catService.create(appCategory);
		return new ResponseEntity<AppCategory> (savedCategory, HttpStatus.CREATED);
	}
	
	@RequestMapping(value="/v1/category", method=RequestMethod.PUT,consumes=MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE )
	public ResponseEntity<AppCategory> updateCategory(@RequestBody AppCategory appCategory){
		AppCategory updateCategory = catService.updateCategory(appCategory);
		
		return new ResponseEntity<AppCategory> (updateCategory, HttpStatus.OK);
	}
	
}
