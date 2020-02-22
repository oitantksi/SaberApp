package com.omega.server.saberapp.service;

import java.util.Optional;

import com.omega.server.saberapp.entity.User;

/**
 * 
 * @author Ramon
 *Interfaz que tiene las funcionalidades principales de la aplicación
 *si crece demasiado es conveniente, agrupar las interfaces por funciónes que sirven 
 *o repositorios a los que llaman
 */
public interface SaberAppServiceI {

	public User getUser(String name);
	public Optional<User> getUserById(Long id);
	public User createUser(User user);

}
