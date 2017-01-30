package com.gmire.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gmire.model.RegistrationCode;
import com.gmire.repository.RegistrationCodeRepository;

@Service
public class RegistrationCodeService {
	
	@Autowired
	private RegistrationCodeRepository registrationCodeRepository;

	public void save(RegistrationCode regCode) {
		registrationCodeRepository.save(regCode);
	}

	public RegistrationCode findByEmail(String email) {
		RegistrationCode regCode = registrationCodeRepository.findByEmailAddress(email);
		return regCode;
	}

}
