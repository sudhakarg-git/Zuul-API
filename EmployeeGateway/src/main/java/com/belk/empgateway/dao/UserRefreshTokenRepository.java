package com.belk.empgateway.dao;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.belk.empgateway.model.UserRefreshToken;

@Repository
public interface UserRefreshTokenRepository extends CrudRepository<UserRefreshToken, Long> {

    Optional<UserRefreshToken> findByToken(String token);

}
