package com.omega.server.saberapp.persistence.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.core.CrudMethods;

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
}