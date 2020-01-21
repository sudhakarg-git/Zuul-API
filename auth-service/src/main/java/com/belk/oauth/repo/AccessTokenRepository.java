package com.belk.oauth.repo;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.belk.oauth.dao.MongoAccessToken;

@Repository
public interface AccessTokenRepository extends MongoRepository<MongoAccessToken, String>{
	
	MongoAccessToken findByTokenId(String tokenId);
	
	void deleteByTokenId(String tokenId);
	
	MongoAccessToken findByRefreshToken(String refreshToken);
	
	void deleteByRefreshToken(String refreshToken);
	
	MongoAccessToken findByAuthenticationId(String authenticationId);
	
	List<MongoAccessToken> findByClintIdandUserName(String clientId,String username);
	
	List<MongoAccessToken> findByClintId(String clientId);

}
