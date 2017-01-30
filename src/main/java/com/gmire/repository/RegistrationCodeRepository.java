package com.gmire.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.gmire.model.RegistrationCode;

public interface RegistrationCodeRepository extends PagingAndSortingRepository<RegistrationCode, String>{

	public RegistrationCode findByEmailAddress(String email);

}
