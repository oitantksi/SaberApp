package com.omega.server.consumer.client;

import com.omega.server.consumer.dto.UserDto;

public interface GestionAlumnosI {
	public UserDto findAlumnoByEmail(String email, String token);
	
	
}
