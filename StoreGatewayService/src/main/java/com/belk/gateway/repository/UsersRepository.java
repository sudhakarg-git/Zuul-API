package com.belk.gateway.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.belk.gateway.domain.UsersInfo;

@Repository
public interface UsersRepository extends CrudRepository<UsersInfo, Long> {

	UsersInfo findByUsername(String username);

}
