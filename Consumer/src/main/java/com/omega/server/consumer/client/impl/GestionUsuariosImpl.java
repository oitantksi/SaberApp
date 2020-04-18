package com.omega.server.consumer.client.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.omega.server.consumer.client.GestionUsuariosI;
import com.omega.server.consumer.dto.PasswordDto;
import com.omega.server.consumer.dto.UserDto;

@Service
public class GestionUsuariosImpl implements GestionUsuariosI {

	
	
	@Override
	public UserDto actualizarUsuario(UserDto datosUsuario, String email, String token){
		
		String uri="http://localhost:8080/user/{email}";

	    RestTemplate restTemplate = new RestTemplate();
	 // Add the Jackson message converter

	    HttpHeaders headers = new HttpHeaders();
	    headers.setContentType(MediaType.APPLICATION_JSON);
	    headers.set("Authorization", token);
	    
	    Map<String, String> params = new HashMap<String, String>();
	    params.put("email", email);
		HttpEntity<UserDto> entity = new HttpEntity<UserDto>(datosUsuario,headers);
		ResponseEntity<UserDto> response = restTemplate
		            .exchange(uri, HttpMethod.PUT, entity, UserDto.class,params);
		return response.getBody();
	
	}

	@Override
	public UserDto actualizarPasswordUsuario(String email, String passwordAnterior, String nuevoPassword,
			String token) {
		
		String uri="http://localhost:8080/user/password/{email}";

	    RestTemplate restTemplate = new RestTemplate();
	    // Add the Jackson message converter

	    HttpHeaders headers = new HttpHeaders();
	    headers.setContentType(MediaType.APPLICATION_JSON);
	    headers.set("Authorization", token);
	    PasswordDto passwordDto=new PasswordDto(nuevoPassword, passwordAnterior);
	    Map<String, String> params = new HashMap<String, String>();
	    params.put("email", email);
		HttpEntity<PasswordDto> entity = new HttpEntity<PasswordDto>(passwordDto,headers);
		ResponseEntity<UserDto> response = restTemplate
		            .exchange(uri, HttpMethod.PUT, entity, UserDto.class,params);
		return response.getBody();
	
	}


	
	
	

}
