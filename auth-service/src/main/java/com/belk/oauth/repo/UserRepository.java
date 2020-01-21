package com.belk.oauth.repo;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.belk.oauth.dao.User;

@Repository
public interface UserRepository extends MongoRepository<User, String>{

	Optional<User> findByUsername(String username);
}
