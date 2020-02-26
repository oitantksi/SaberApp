package com.omega.server.consumer.client;

import org.springframework.http.ResponseEntity;

import com.omega.server.consumer.dto.UserDto;

public interface LoginRegistroI {

	
	/**
	 * 
	 * @param Email del usuario
	 * @param password
	 * @return String con token de sesión
	 */
	public String login(String email, String password);
	/**
	 * 
	 * @param datosUsuario
	 * @return los mismos datos si fue correcta el alta
	 * @exception excepción con mensaje de error
	 */
	public UserDto registraAlumno(UserDto datosUsuario) throws Exception;
	/**
	 * 
	 * @param datosUsuario
	 * @return los mismos datos si fue correcta el alta
	 * @exception excepción con mensaje de error
	 */
	public UserDto registraProfesor(UserDto datosUsuario) throws Exception;
	/**
	 * Clásico donde los haya para hacer pruebas
	 * @param Hola!!!!!
	 * @param token, clave de sesión
	 * @return Devuelve la bienvenida al mundo
	 */
	public ResponseEntity<String> helloworld(String name, String token);
	
}
