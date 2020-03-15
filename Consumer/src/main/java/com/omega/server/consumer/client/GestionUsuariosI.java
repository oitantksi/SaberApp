package com.omega.server.consumer.client;

import com.omega.server.consumer.dto.UserDto;

public interface GestionUsuariosI {

	public UserDto actualizarUsuario(UserDto userDto, String email, String token);
	
}
