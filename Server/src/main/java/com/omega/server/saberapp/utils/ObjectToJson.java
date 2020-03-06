package com.omega.server.saberapp.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.omega.server.consumer.dto.UserDto;

public class ObjectToJson {
	
	public static void main (String[] args) {
		
		ObjectMapper mapper = new ObjectMapper();
		UserDto datosUsuario=new UserDto(null, "Pedro", "Perez", "pedroperez@omega.com", "PereGran", "pepe", "IOC", 'A');
		// Java object to JSON string
		try {
			System.out.println(mapper.writeValueAsString(datosUsuario));
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		
	}

}
