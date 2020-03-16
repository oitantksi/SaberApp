package com.omega.server.saberapp.persistence.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.repository.CrudRepository;

import com.omega.server.saberapp.entity.User;


/**
 * 
 * @author Ramon
 *Repositorio de usuarios.
 */
public interface UserRepository extends CrudRepository<User, Long>{

	User findByName(String name);
	User findByEmail(String mail);
	List<User> findAll();
	User findByNickname(String nickname);
	@Transactional
	long deleteByEmail(String mail);
	
	
}