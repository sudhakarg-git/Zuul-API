package com.belk.hello.dao;

import org.springframework.data.repository.CrudRepository;

import com.belk.hello.model.DAOUser;

public interface UserDao extends CrudRepository<DAOUser, Integer>{
	
	DAOUser findByUsername(String username);

}
