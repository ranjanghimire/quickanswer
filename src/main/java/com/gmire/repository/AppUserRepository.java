package com.gmire.repository;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.gmire.model.AppUser;

@Repository
public interface AppUserRepository extends PagingAndSortingRepository<AppUser, String> {

	public List<AppUser> findAll();

	public List<AppUser> findByUserNameIgnoreCaseLike(String userName);
	
	public AppUser findByUserNameAndPassword(String userName, String password);

	public AppUser findByUserName(String userName);
	
}
