package com.omega.server.consumer.client.impl;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.omega.server.consumer.client.GestionUsuariosI;
import com.omega.server.consumer.client.LoginRegistroI;
import com.omega.server.consumer.dto.UserDto;
/**
 * 
 * @author Ramon
 *Test de integración para probar funcionamiento del consumidor de servicios rest
 *sin el servidor arrancado no funcionaran
 */
@SpringBootTest
public class GestionUsuariosImplIT {
	
	@Autowired
	GestionUsuariosI gestionUsuarios;
	@Autowired
	LoginRegistroI loginRegistro;
	
	@Test
	public void whenUpdateUserTrue_thenReturnUser() {
		String token=loginRegistro.login("master@omega.com", "omega");
		UserDto datosUsuario=new UserDto(null, "Pedro", "Perez", "pedroperez@omega.com", "PereGran", "pepe", "IOC", 'A');
		String email= "pedroperez@omega.com";
		String emailUpdate="pedroperezUpdated@omega.com";
		//Usurio que se registra
		UserDto userDto=new UserDto();
		userDto = loginRegistro.registraUsuario(datosUsuario);
		//Nuevos datos del usuario a modificar
		UserDto userToModify= new UserDto(null, "PedroUpdated", "PerezUpdated", "pedroperezUpdated@omega.com", "PereGranUpdated", "pepeUpdated", "IOC", 'A');
		
		//actualizamos el usuario
		userDto= gestionUsuarios.actualizarUsuario(userToModify, email, token);
		//tratamos de recuperar el usuario sin el mail modificado, ya no existirá
		assertEquals(null, loginRegistro.findUsuarioByEmail(email, token));		
		assertEquals(userToModify.getEmail(), emailUpdate);
		
	}


		
	
	

	
	
}
