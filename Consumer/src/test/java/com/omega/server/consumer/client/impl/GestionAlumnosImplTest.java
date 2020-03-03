package com.omega.server.consumer.client.impl;

import com.omega.server.consumer.dto.UserDto;

public class GestionAlumnosImplTest {

	public static void main(String[]args) {
		
		GestionAlumnosImpl gestionAlumnosImpl=new GestionAlumnosImpl();
		LoginRegistroImpl loginRegistroImpl=new LoginRegistroImpl();
		
		String token=loginRegistroImpl.login("master@omega.com", "omega");
		
		UserDto alumno=gestionAlumnosImpl.findAlumnoByEmail("montse@omega.com", token);
		
		System.out.println(alumno.toString());
		
	}
	
	
}
