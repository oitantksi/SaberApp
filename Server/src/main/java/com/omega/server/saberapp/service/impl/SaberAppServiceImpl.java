package com.omega.server.saberapp.service.impl;

import static java.util.Collections.emptyList;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.omega.server.saberapp.entity.User;
import com.omega.server.saberapp.persistence.repository.UserRepository;
import com.omega.server.saberapp.service.SaberAppServiceI;
/**
 * 
 * @author Ramon
 *Lleva a cabo las acciones que se dan en la base de datos de la aplicación
 */
@Service
public class SaberAppServiceImpl implements SaberAppServiceI, UserDetailsService {

	 @Autowired
	 private UserRepository userRepository;
	 
	 private BCryptPasswordEncoder bCryptPasswordEncoder;
	
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
		
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		return userRepository.save(user);
		
	}

	@Override
	public String login(String nickname, String password) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public UserDetails loadUserByUsername(String nickname) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		com.omega.server.saberapp.entity.User usuarioEntity = userRepository.findByNickname(nickname);
		if (usuarioEntity == null) {
			throw new UsernameNotFoundException(nickname);
		}
		return new org.springframework.security.core.userdetails.User(usuarioEntity.getNickname(), usuarioEntity.getPassword(), emptyList());
	}
	
	
}
