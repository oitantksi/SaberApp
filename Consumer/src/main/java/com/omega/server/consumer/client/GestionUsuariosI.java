package com.omega.server.consumer.client;

import com.omega.server.consumer.dto.UserDto;

public interface GestionUsuariosI {

	/**
	 * Modifica datos del usuario menos el password
	 * @param userDto
	 * @param email
	 * @param token
	 * @return
	 */
	public UserDto actualizarUsuario(UserDto userDto, String email, String token);
	/**
	 * Modifica el password del usuario.
	 * @param userDto
	 * @param email
	 * @param token
	 * @param passwordAnterior
	 * @param nuevoPassword
	 * @return
	 */
	public UserDto actualizarPasswordUsuario(String email, String passwordAnterior, String nuevoPassword,String token);
	
}
