package com.omega.server.consumer.client.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.HttpClientErrorException;

import com.omega.server.consumer.client.GestionAlumnosI;
import com.omega.server.consumer.client.LoginRegistroI;
import com.omega.server.consumer.dto.UserDto;
/**
 * 
 * @author Ramon
 *Test de integración para probar funcionamiento del consumidor de servicios rest
 *sin el servidor arrancado no funcionaran
 */
@SpringBootTest
public class GestionAlumnosImplIT {

	@Autowired
	GestionAlumnosI gestionAlumnos;
	
	@Autowired
	LoginRegistroI loginRegistro;
	
	@Test
	public void whenFindByEmail_thenReturnUser() {
		
		String token=loginRegistro.login("master@omega.com", "omega");//usuario de sistema para obtener token de seguridad

		String email="montse@omega.com";
		
		UserDto alumno=gestionAlumnos.findAlumnoByEmail(email, token);
		
		assertEquals(alumno.getName(), "montse", "Ha encontrado el usuario con email: "+email);
		
	}
	
	@Test
	public void whenFindByEmail_thenReturnError() {
		
		String token="token falso";//usuario de sistema para obtener token de seguridad

		String email="montse@omega.com";
		
		try {
			UserDto alumno=gestionAlumnos.findAlumnoByEmail(email, token);
		}catch(HttpClientErrorException e){
			assertEquals(403, e.getRawStatusCode());	
		}
		
	}
	
	/**
	 * 
	 * @param args
	 * Pruebas iniciales realizadas
	 * 
	 * 
	 */
	public static void main(String[]args) {
		
		GestionAlumnosImpl gestionAlumnosImpl=new GestionAlumnosImpl();
		LoginRegistroImpl loginRegistroImpl=new LoginRegistroImpl();
		
		String token=loginRegistroImpl.login("master@omega.com", "omega");
		
		UserDto alumno=gestionAlumnosImpl.findAlumnoByEmail("montse@omega.com", token);
		
		System.out.println(alumno.toString());
		
	}
	
	
}
