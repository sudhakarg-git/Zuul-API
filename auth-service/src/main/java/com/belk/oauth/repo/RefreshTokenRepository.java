package com.belk.oauth.repo;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.belk.oauth.dao.MongoRefreshToken;

@Repository
public interface RefreshTokenRepository extends MongoRepository<MongoRefreshToken, String>{

	MongoRefreshToken findByTokenId(String tokenId);
	
	void deleteByTokenId(String tokenId);
}
