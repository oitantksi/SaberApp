package com.omega.server.consumer.client.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.omega.server.consumer.client.GestionPreguntasI;
import com.omega.server.consumer.dto.MateriaDto;
import com.omega.server.consumer.dto.PreguntaDto;
import com.omega.server.consumer.dto.RespuestaDto;
import com.omega.server.consumer.dto.UserDto;
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
	public PreguntaDto[] getAllPreguntas(String token) {
		String uri="http://localhost:8080/preguntes/totes";

	    RestTemplate restTemplate = new RestTemplate();
	    HttpHeaders headers = new HttpHeaders();
	    headers.set("Authorization", token);
	    headers.setContentType(MediaType.APPLICATION_JSON);
	    

	    
		HttpEntity<PreguntaDto[]> entity = new HttpEntity<PreguntaDto[]>(headers);
	    
		ResponseEntity<PreguntaDto[]> response = restTemplate
		            .exchange(uri, HttpMethod.GET, entity, PreguntaDto[].class);
				
		
		return response.getBody();
	}

	@Override
	public PreguntaDto savePregunta(String token, PreguntaDto pregunta) {
		final String uri = "http://localhost:8080/pregunta";
	    RestTemplate restTemplate = new RestTemplate();
	    // Add the Jackson message converter
	    restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
	    // create request body    
	    // set headers
	    HttpHeaders headers = new HttpHeaders();
	    headers.setContentType(MediaType.APPLICATION_JSON);
	    headers.set("Authorization", token);
	    HttpEntity<PreguntaDto> entity = new HttpEntity<PreguntaDto>(pregunta, headers);
//	    String token=login("master@omega.com", "omega");
	    
	    // send request and parse result
	    ResponseEntity<PreguntaDto> response = restTemplate
	            .exchange(uri, HttpMethod.POST, entity, PreguntaDto.class);
	    
	    return response.getBody();
	}

	@Override
	public RespuestaDto[] getRespuestasAlumno(String token, String idAlumno) {
		String uri="http://localhost:8080/respostes/alumno/{idAlumno}";

	    RestTemplate restTemplate = new RestTemplate();
	    HttpHeaders headers = new HttpHeaders();
	    headers.set("Authorization", token);
	    headers.setContentType(MediaType.APPLICATION_JSON);
	    
	    Map<String, String> params = new HashMap<String, String>();
	    params.put("idAlumno", idAlumno);
	    
		HttpEntity<RespuestaDto[]> entity = new HttpEntity<RespuestaDto[]>(headers);
	    
		ResponseEntity<RespuestaDto[]> response = restTemplate
		            .exchange(uri, HttpMethod.GET, entity, RespuestaDto[].class, params);
				
		
		return response.getBody();
	}
	
	@Override
	public RespuestaDto registraRespuestaAlumno(String token,RespuestaDto respuesta){
		final String uri = "http://localhost:8080/respostes/alumno";
	    RestTemplate restTemplate = new RestTemplate();
	    // Add the Jackson message converter
	    restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
	    // create request body    
	    // set headers
	    HttpHeaders headers = new HttpHeaders();
	    headers.setContentType(MediaType.APPLICATION_JSON);
	    headers.set("Authorization", token);
	    HttpEntity<RespuestaDto> entity = new HttpEntity<RespuestaDto>(respuesta, headers);
//	    String token=login("master@omega.com", "omega");
	    
	    // send request and parse result
	    ResponseEntity<RespuestaDto> response = restTemplate
	            .exchange(uri, HttpMethod.POST, entity, RespuestaDto.class);
	    
	    return response.getBody();
	}
}
