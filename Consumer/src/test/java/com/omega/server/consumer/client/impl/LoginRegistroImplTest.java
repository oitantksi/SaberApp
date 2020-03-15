package com.omega.server.consumer.client.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;

import com.omega.server.consumer.client.LoginRegistroI;
import com.omega.server.consumer.dto.CentreDto;
import com.omega.server.consumer.dto.UserDto;

@SpringBootTest
@TestInstance(Lifecycle.PER_CLASS)
public class LoginRegistroImplTest {

	
	
	@Autowired
	LoginRegistroI loginRegistro;
	
	@AfterAll
	
	public void borrarRegistros() {
		String token=loginRegistro.login("master@omega.com", "omega");//usuario de sistema para obtener token de seguridad

		String email="franperez@omega.com";
		loginRegistro.deleteUsuario(email, token);
		email="pedroperez@omega.com";
		loginRegistro.deleteUsuario(email, token);
	}
	
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
	
	/**
	 * Al intentar meter el mismo usuario por segunda vez fallará
	 */
	@Test 
	public void whenRegisterUserFalse_thenReturnException() {
		UserDto datosUsuario=new UserDto(null, "Fran", "Perez", "franperez@omega.com", "fran", "fran", "IOC", 'A');
		loginRegistro.registraUsuario(datosUsuario);
		UserDto userDto=new UserDto();
		try {
			userDto = loginRegistro.registraUsuario(datosUsuario);
		} catch (HttpServerErrorException e) {
			assertEquals(500, e.getRawStatusCode());
		}	
		
		
	}
	@Test
	public void whenDeleteUserTrue_thenItsNotInDB() {
		
		String token=loginRegistro.login("master@omega.com", "omega");//usuario de sistema para obtener token de seguridad

		String email="fernandoperez@omega.com";
			
		UserDto datosUsuario=new UserDto(null, "Fernando", "Perez", "fernandoperez@omega.com", "Fer", "fer", "IOC", 'A');
		
		UserDto userDto=new UserDto();
		try {
			userDto = loginRegistro.registraUsuario(datosUsuario);
		} catch (Exception e) {
			e.printStackTrace();
		}	
		Long borrado=loginRegistro.deleteUsuario(email, token);
		
		UserDto alumnoBorrado=loginRegistro.findUsuarioByEmail(email, token);
		
		assertEquals(1, borrado);
		assertEquals(null, alumnoBorrado);
		
		
	}
	
	@Test
	public void whenFindByEmailTrue_thenReturnUser() {
		
		String token=loginRegistro.login("master@omega.com", "omega");//usuario de sistema para obtener token de seguridad

		String email="montse@omega.com";
		
		UserDto alumno=loginRegistro.findUsuarioByEmail(email, token);
		
		assertEquals(alumno.getName(), "montse", "Ha encontrado el usuario con email: "+email);
		
	}
	
	@Test
	public void whenFindByEmailBadToken_thenReturnError() {
		
		String token="token falso";//usuario de sistema para obtener token de seguridad

		String email="montse@omega.com";
		
		try {
			UserDto alumno=loginRegistro.findUsuarioByEmail(email, token);
		}catch(HttpClientErrorException e){
			assertEquals(403, e.getRawStatusCode());	
		}
	}
	@Test 
	public void whenFindByEmailFalse_thenReturnError() {
		
		String token=loginRegistro.login("master@omega.com", "omega");//usuario de sistema para obtener token de seguridad

		String email="ramonmailfalso@omega.com";
		

		UserDto alumno=loginRegistro.findUsuarioByEmail(email, token);
			
		assertEquals(null, alumno);
		
		
	}
	@Test
	public void whenFindAll_thenReturnCentersArray() {
		
		CentreDto[] list=loginRegistro.getAllCentres();
	
		assertEquals("IOC", list[0].getCentre());
		
	}
	
	
	

	
	
	public static void main (String []args) throws Exception {
			
		UserDto datosUsuario=new UserDto(null, "Pedro", "Perez", "pedroperez@omega.com", "PereGran", "pepe", "IOC", 'A');
		UserDto datosUsuario1=new UserDto(null, "Pedro1", "Perez", "pedroperez1@omega.com", "PereGran1", "pepe", "IOC", 'A');
		UserDto datosUsuario2=new UserDto(null, "Pedro2", "Perez", "pedroperez2@omega.com", "PereGran2", "pepe", "IOC", 'A');
		UserDto datosUsuario3=new UserDto(null, "Pedro3", "Perez", "pedroperez3@omega.com", "PereGran3", "pepe", "IOC", 'A');
		UserDto datosUsuario4=new UserDto(null, "Pedro4", "Perez", "pedroperez4@omega.com", "PereGran4", "pepe", "IOC", 'A');
		UserDto datosUsuario5=new UserDto(null, "Pedro5", "Perez", "pedroperez5@omega.com", "PereGran5", "pepe", "IOC", 'A');
		UserDto datosUsuario6=new UserDto(null, "Pedro6", "Perez", "pedroperez6@omega.com", "PereGran6", "pepe", "IOC", 'A');
		UserDto datosUsuario7=new UserDto(null, "Pedro7", "Perez", "pedroperez7@omega.com", "PereGran7", "pepe", "IOC", 'A');
		
		LoginRegistroImpl loginRegistroImpl= new LoginRegistroImpl();
		try {
			loginRegistroImpl.registraUsuario(datosUsuario);
			loginRegistroImpl.registraUsuario(datosUsuario1);
			loginRegistroImpl.registraUsuario(datosUsuario2);
			loginRegistroImpl.registraUsuario(datosUsuario3);
			loginRegistroImpl.registraUsuario(datosUsuario4);
			loginRegistroImpl.registraUsuario(datosUsuario5);
			loginRegistroImpl.registraUsuario(datosUsuario6);
			loginRegistroImpl.registraUsuario(datosUsuario7);
			
		}catch (Exception e) {	
			e.printStackTrace();			
		}		
	}

}
