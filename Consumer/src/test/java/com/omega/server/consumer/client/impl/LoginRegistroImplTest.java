package com.omega.server.consumer.client.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.HttpClientErrorException;

import com.omega.server.consumer.client.LoginRegistroI;
import com.omega.server.consumer.dto.UserDto;

@SpringBootTest
public class LoginRegistroImplTest {

	
	
	@Autowired
	LoginRegistroI loginRegistro;
	
	@Test
	public void whenLoginTrue_thenReturnStringToken() {
		
		String token=loginRegistro.login("master@omega.com", "omega");
		
		Boolean correcto=token.matches("Bearer.*");
		assert(correcto);
		
		
	}
	
	@Test
	public void whenLoginFalse_thenReturnException(){
		try {	
			String token=loginRegistro.login("master@omega.com", "passwordFalso");		
		}catch(HttpClientErrorException e){
			assertEquals(403, e.getRawStatusCode());			
		}
	}
	
	@Test 
	public void whenRegisterUserTrue_thenReturnUser() {
		UserDto datosUsuario=new UserDto(null, "Pedro", "Perez", "pedroperez@omega.com", "PereGran", "pepe", "IOC", 'A');
		
		UserDto userDto=new UserDto();
		try {
			userDto = loginRegistro.registraUsuario(datosUsuario);
		} catch (Exception e) {
			e.printStackTrace();
		}	
		assertEquals(datosUsuario.getEmail(), userDto.getEmail());
		
	}
	@Test 
	public void whenRegisterUserFalse_thenReturnException() {
		UserDto datosUsuario=new UserDto(null, "Pedro", "Perez", "pedroperez@omega.com", "PereGran", "pepe", "IOC", 'A');
		
		UserDto userDto=new UserDto();
		try {
			userDto = loginRegistro.registraUsuario(datosUsuario);
		} catch (HttpClientErrorException e) {
			assertEquals(500, e.getRawStatusCode());
		}	
		
		
	}
	@Test
	public void whenFindByEmail_thenReturnUser() {
		
		String token=loginRegistro.login("master@omega.com", "omega");//usuario de sistema para obtener token de seguridad

		String email="montse@omega.com";
		
		UserDto alumno=loginRegistro.findUsuarioByEmail(email, token);
		
		assertEquals(alumno.getName(), "montse", "Ha encontrado el usuario con email: "+email);
		
	}
	
	@Test
	public void whenFindByEmail_thenReturnError() {
		
		String token="token falso";//usuario de sistema para obtener token de seguridad

		String email="montse@omega.com";
		
		try {
			UserDto alumno=loginRegistro.findUsuarioByEmail(email, token);
		}catch(HttpClientErrorException e){
			assertEquals(403, e.getRawStatusCode());	
		}
	}
	
	public static void main (String []args) throws Exception {
			
		UserDto datosUsuario=new UserDto(null, "Pedro", "Perez", "pedroperez@omega.com", "PereGran", "pepe", "IOC", 'A');
		LoginRegistroImpl loginRegistroImpl= new LoginRegistroImpl();
		try {
			loginRegistroImpl.registraUsuario(datosUsuario);
			
		}catch (Exception e) {	
			e.printStackTrace();			
		}		
	}
}
