package com.belk.jwt.dao;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.belk.jwt.model.User;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

    Optional<User> findByEmail(@Param("email") String email);
    
    Optional<User> findByName(@Param("name") String userName);
    
    

}
