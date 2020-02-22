package com.omega.server.saberapp.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.omega.server.saberapp.entity.User;
import com.omega.server.saberapp.persistence.repository.UserRepository;
import com.omega.server.saberapp.service.SaberAppServiceI;

@Service
public class SaberAppServiceImpl implements SaberAppServiceI{

	 @Autowired
	 private UserRepository userRepository;
	
	@Override
	public User getUser(String name) {
		
		return userRepository.findByName(name);
	}
	
	@Override
	public Optional<User> getUserById(Long id) {
		
		return userRepository.findById(id);
		
	}
	
	@Override
	public User createUser(User user) {
		
		return userRepository.save(user);
		
	}
	
	
}
