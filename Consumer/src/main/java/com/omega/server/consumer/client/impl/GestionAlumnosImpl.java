package com.omega.server.consumer.client.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.omega.server.consumer.client.GestionAlumnosI;
import com.omega.server.consumer.dto.UserDto;

public class GestionAlumnosImpl implements GestionAlumnosI {

	@Override
	public UserDto findAlumnoByEmail(String email, String token) {
		String uri="http://localhost:8080/user/email/{email}";

	    RestTemplate restTemplate = new RestTemplate();
	    HttpHeaders headers = new HttpHeaders();
	    headers.setContentType(MediaType.APPLICATION_JSON);
	    headers.set("Authorization", token);
	    
		Map<String, String> params = new HashMap<String, String>();
	    params.put("email", email);
		HttpEntity<UserDto> entity = new HttpEntity<UserDto>(headers);
		ResponseEntity<UserDto> response = restTemplate
		            .exchange(uri, HttpMethod.GET, entity, UserDto.class,params);
					
		return response.getBody();
	}

}
