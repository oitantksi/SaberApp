package com.omega.server.consumer.client;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.omega.server.consumer.dto.CentreDto;
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
	public UserDto registraUsuario(UserDto datosUsuario);
	
	/**
	 * Borra un usuario de la base de datos
	 * @param datosUsuario
	 * @param token
	 */
	public Long deleteUsuario(String email, String token);
	/**
	 * 
	 * @param email
	 * @param token
	 * @return recupera usuario de base de datos
	 */
	public UserDto findUsuarioByEmail(String email, String token);
	/**
	 * Clásico donde los haya para hacer pruebas
	 * @param Hola!!!!!
	 * @param token, clave de sesión
	 * @return Devuelve la bienvenida al mundo
	 */
	public ResponseEntity<String> helloworld(String name, String token);
	
	/**
	 * Recupera los centros adscritos a la app
	 * @return listado de centros válidos
	 */
	public CentreDto[] getAllCentres();
	
	
}
