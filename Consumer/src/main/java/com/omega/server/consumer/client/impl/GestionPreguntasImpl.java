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

import com.omega.server.consumer.client.GestionPreguntasI;
import com.omega.server.consumer.dto.MateriaDto;
import com.omega.server.consumer.dto.PreguntaDto;
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

	@Override
	public PreguntaDto[] getPreguntasByMateria(String token, String materia) {
		String uri="http://localhost:8080/preguntes/{materia}";

	    RestTemplate restTemplate = new RestTemplate();
	    HttpHeaders headers = new HttpHeaders();
	    headers.set("Authorization", token);
	    headers.setContentType(MediaType.APPLICATION_JSON);
	    
	    Map<String, String> params = new HashMap<String, String>();
	    params.put("materia", materia);
	    
		HttpEntity<PreguntaDto[]> entity = new HttpEntity<PreguntaDto[]>(headers);
	    
		ResponseEntity<PreguntaDto[]> response = restTemplate
		            .exchange(uri, HttpMethod.GET, entity, PreguntaDto[].class, params);
				
		
		return response.getBody();
	}

	@Override
	public PreguntaDto savePregunta(String token, PreguntaDto pregunta) {
		// TODO Auto-generated method stub
		return null;
	}

}
