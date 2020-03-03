package com.omega.server.consumer.client.impl;

import org.springframework.beans.factory.annotation.Autowired;

import com.omega.server.consumer.client.LoginRegistroI;
import com.omega.server.consumer.dto.UserDto;

public class LoginRegistroImplTest {

	
	
	@Autowired
	LoginRegistroI loginRegistro;
	
	
	
	
	public static void main (String []args) throws Exception {
			
		UserDto datosUsuario=new UserDto(null, "Pedro", "Perez", "pedroperez@omega.com", "PereGran", "pepe", "IOC", 'A');
		LoginRegistroImpl loginRegistroImpl= new LoginRegistroImpl();
		try {
			loginRegistroImpl.registraAlumno(datosUsuario);
			
		}catch (Exception e) {	
			e.printStackTrace();			
		}		
	}
}
