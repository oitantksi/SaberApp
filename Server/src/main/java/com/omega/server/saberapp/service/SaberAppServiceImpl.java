package com.omega.server.saberapp.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.omega.server.saberapp.entity.User;
import com.omega.server.saberapp.persistence.repository.UserRepository;

@Service
public class SaberAppServiceImpl implements SaberAppService{

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

	
}
