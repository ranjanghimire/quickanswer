package com.gmire;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.gmire.model.Question;
import com.gmire.repository.QuestionRepository;

@SpringBootApplication
public class Application {

	@Autowired
	private static QuestionRepository qRepo;
	
	public static void main(String[] args) {
		
		SpringApplication.run(Application.class, args);
	}

}
