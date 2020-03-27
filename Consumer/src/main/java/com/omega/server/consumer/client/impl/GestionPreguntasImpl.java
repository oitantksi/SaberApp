package com.omega.server.consumer.client.impl;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.omega.server.consumer.client.GestionPreguntasI;
import com.omega.server.consumer.dto.MateriaDto;
@Service
public class GestionPreguntasImpl implements GestionPreguntasI{

	public GestionPreguntasImpl() {
		
	}
	
	@Override
	public MateriaDto[] getAllMateries(String token) {
		String uri="http://localhost:8080/materies";

	    RestTemplate restTemplate = new RestTemplate();
	    HttpHeaders headers = new HttpHeaders();
	    headers.set("Authorization", token);
		HttpEntity<MateriaDto[]> entity = new HttpEntity<MateriaDto[]>(headers);
	    headers.setContentType(MediaType.APPLICATION_JSON);
		ResponseEntity<MateriaDto[]> response = restTemplate
		            .exchange(uri, HttpMethod.GET, entity, MateriaDto[].class);
				
		
		return response.getBody();
	}

}
