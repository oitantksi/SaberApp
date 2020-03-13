package com.omega.server.consumer.client.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.omega.server.consumer.client.LoginRegistroI;
import com.omega.server.consumer.dto.CentreDto;
import com.omega.server.consumer.dto.CentresListDto;
import com.omega.server.consumer.dto.UserDto;

@Service
public class LoginRegistroImpl implements LoginRegistroI{

	public LoginRegistroImpl() {
	}

	@Override
	public String login(String email, String password) {
		final String uri = "http://localhost:8080/login";
	    RestTemplate restTemplate = new RestTemplate();
	    // Add the Jackson message converter
	    restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
	    // create request body    
	    String input = "{\"email\":\""+email+ "\",\"password\":\""+password+"\"}";
	    // set headers
	    HttpHeaders headers = new HttpHeaders();
	    headers.setContentType(MediaType.APPLICATION_JSON);
	    headers.set("Authorization", "Basic " + "xxxxxxxxxxxx");
	    HttpEntity<String> entity = new HttpEntity<String>(input, headers);

	    // send request and parse result
	    ResponseEntity<String> response = restTemplate
	            .exchange(uri, HttpMethod.POST, entity, String.class);
	    
	    return response.getHeaders().getFirst("Authorization");
	}

	@Override
	public UserDto registraUsuario(UserDto datosUsuario){
		final String uri = "http://localhost:8080/user";
	    RestTemplate restTemplate = new RestTemplate();
	    // Add the Jackson message converter
	    restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
	    // create request body    
	    // set headers
	    HttpHeaders headers = new HttpHeaders();
	    headers.setContentType(MediaType.APPLICATION_JSON);
	    HttpEntity<UserDto> entity = new HttpEntity<UserDto>(datosUsuario, headers);
//	    String token=login("master@omega.com", "omega");
//	    headers.set("Authorization", token);
	    // send request and parse result
	    ResponseEntity<UserDto> response = restTemplate
	            .exchange(uri, HttpMethod.POST, entity, UserDto.class);
	    
	    return response.getBody();
	}
	@Override
	public Long deleteUsuario(String email, String token) {
		String uri="http://localhost:8080/user/{email}";

	    RestTemplate restTemplate = new RestTemplate();
	 // Add the Jackson message converter

	    HttpHeaders headers = new HttpHeaders();
	    headers.setContentType(MediaType.APPLICATION_JSON);
	    headers.set("Authorization", token);
	    
	    Map<String, String> params = new HashMap<String, String>();
	    params.put("email", email);
		HttpEntity<Long> entity = new HttpEntity<Long>(headers);
		ResponseEntity<Long> response = restTemplate
		            .exchange(uri, HttpMethod.DELETE, entity, Long.class,params);
		return response.getBody();
		
	}

	@Override
	public UserDto findUsuarioByEmail(String email, String token) {
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
	@Override
	public ResponseEntity<String> helloworld(String name, String token) {
		String uri="http://localhost:8080/helloworld";
		UriComponentsBuilder uriComp=UriComponentsBuilder.fromHttpUrl(uri).queryParam("name", name);
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
	    headers.setContentType(MediaType.APPLICATION_JSON);
	    headers.set("Authorization", token);
	   
	    HttpEntity<String> entity = new HttpEntity<String>(headers);
	    ResponseEntity<String> response = restTemplate
	            .exchange(uriComp.toUriString(), HttpMethod.GET, entity, String.class);
		
		return response;
	}

	@Override 
	public CentreDto[] getAllCentres(){
		String uri="http://localhost:8080/centres";

	    RestTemplate restTemplate = new RestTemplate();
	    HttpHeaders headers = new HttpHeaders();
	    
		HttpEntity<CentreDto[]> entity = new HttpEntity<CentreDto[]>(headers);
	    headers.setContentType(MediaType.APPLICATION_JSON);
		ResponseEntity<CentreDto[]> response = restTemplate
		            .exchange(uri, HttpMethod.GET, entity, CentreDto[].class);
				
		
		return response.getBody();
		
	}
}
