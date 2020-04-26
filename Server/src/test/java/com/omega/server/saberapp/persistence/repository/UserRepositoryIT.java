package com.omega.server.saberapp.persistence.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.omega.server.saberapp.entity.User;

@SpringBootTest
public class UserRepositoryIT {

	@Autowired
	private UserRepository userRepository;
	
	@Test
	public void whenFindByEmail_thenReturnUser() {
		
		String email= "montse@omega.com";
		User found = userRepository.findByEmail(email);
		assertEquals(found.getName(), "montse", "Ha encontrado el usuario con email: "+email);
		
	}
//	@Test
//	public void whenDeleteByEmail_thenReturnLong() {
//		String email="ramon@omega.com";
//		Long num= userRepository.deleteByEmail(email);
//		 assertEquals(1, num);
//		
//	}
	@Test
	public void foundAllUsers() {
		
		List<User> users=userRepository.findAll();
		assertEquals(5, users.size());
	}
}
