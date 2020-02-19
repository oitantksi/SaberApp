package com.omega.server.saberapp.service;

import java.util.Optional;

import com.omega.server.saberapp.entity.User;


public interface SaberAppService {

	public User getUser(String name);
	public Optional<User> getUserById(Long id);
}
