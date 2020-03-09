package com.omega.server.saberapp.service.impl;

import static java.util.Collections.emptyList;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.omega.server.saberapp.entity.Centre;
import com.omega.server.saberapp.entity.User;
import com.omega.server.saberapp.persistence.repository.CentresRepository;
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
	 @Autowired
	 private BCryptPasswordEncoder bCryptPasswordEncoder;
	 @Autowired 
	 private CentresRepository centreRepository;
	
	@Override
	public User getUser(String name) {
		
		return userRepository.findByName(name);
	}
	@Override
	public User getUserByEmail(String email) {
		return userRepository.findByEmail(email);
		
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
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		com.omega.server.saberapp.entity.User usuarioEntity = userRepository.findByEmail(email);
		if (usuarioEntity == null) {
			throw new UsernameNotFoundException(email);
		}
		return new org.springframework.security.core.userdetails.User(usuarioEntity.getEmail(), usuarioEntity.getPassword(), emptyList());
	}
	
	@Override
	public List<Centre> getCentres(){
			
		return centreRepository.findAll();
	}
	
}
