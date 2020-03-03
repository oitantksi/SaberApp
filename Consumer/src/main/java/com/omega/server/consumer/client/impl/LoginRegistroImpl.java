package com.omega.server.consumer.client.impl;

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
	public UserDto registraAlumno(UserDto datosUsuario) throws Exception {
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
	public UserDto registraProfesor(UserDto datosUsuario) throws Exception {
		// TODO Auto-generated method stub
		return null;
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

}
