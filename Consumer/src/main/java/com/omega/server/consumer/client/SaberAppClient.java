package com.omega.server.consumer.client;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.omega.server.consumer.dto.UserDto;
/**
 * 
 * @author Ramon
 *Se utilizó para pruebas iniciales de comunicación 
 *ahora sustituida por interfaces GestionAlumnos, GestionProfesores y LoginRegistro
 */
@Deprecated 
public class SaberAppClient {

	
	public static void main(String[]args) {
		SaberAppClient saberAppClient=new SaberAppClient();
		
		System.out.println("Calling get for login...");
		String token=saberAppClient.login("ramon@omega.com", "omega");
		System.out.println(saberAppClient.login("ramon@omega.com", "omega"));
		
		System.out.println("Calling get for helloworld...");
		System.out.println(saberAppClient.helloworld("Omega",token).toString());
		
		System.out.println("Calling get for user...by id");
		UserDto user=saberAppClient.getUserById(Long.parseLong("1"), token);
		System.out.println(user.toString());
	
		System.out.println("Calling get for user...by name");
		user=saberAppClient.getUserByName("montse", token);
		System.out.println(user.toString());
		
		
		
	}



	public String login(String usuario, String password) {
		
		 final String uri = "http://localhost:8080/login";
		    RestTemplate restTemplate = new RestTemplate();
		    // Add the Jackson message converter
		    restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
		    // create request body
		    
		    String input = "{\"email\":\""+usuario+ "\",\"password\":\""+password+"\"}";


		    // set headers
		    HttpHeaders headers = new HttpHeaders();
		    headers.setContentType(MediaType.APPLICATION_JSON);
		    headers.set("Authorization", "Basic " + "xxxxxxxxxxxx");
		    HttpEntity<String> entity = new HttpEntity<String>(input, headers);

		    // send request and parse result
		    ResponseEntity<String> response = restTemplate
		            .exchange(uri, HttpMethod.POST, entity, String.class);
                    
                    System.out.println("\nRESPONSE:\n" + response);
		    
		    return response.getHeaders().getFirst("Authorization");
	}
	
	
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
	
	public UserDto getUserById(Long id, String token) {
		String uri="http://localhost:8080/user/id/{id}";

	    RestTemplate restTemplate = new RestTemplate();
	    HttpHeaders headers = new HttpHeaders();
	    headers.setContentType(MediaType.APPLICATION_JSON);
	    headers.set("Authorization", token);
	    
		Map<String, Long> params = new HashMap<String, Long>();
	    params.put("id", id);
		HttpEntity<UserDto> entity = new HttpEntity<UserDto>(headers);
		ResponseEntity<UserDto> response = restTemplate
		            .exchange(uri, HttpMethod.GET, entity, UserDto.class,params);
			
			
		return response.getBody();
		
		
		
	}
	public UserDto getUserByName(String name, String token) {
		String uri="http://localhost:8080/user/{user}";
	    RestTemplate restTemplate = new RestTemplate();
	    HttpHeaders headers = new HttpHeaders();
	    headers.setContentType(MediaType.APPLICATION_JSON);
	    headers.set("Authorization", token);
		Map<String, String> params = new HashMap<String, String>();
	    params.put("user", name);
		
	    HttpEntity<UserDto> entity = new HttpEntity<UserDto>(headers);
		ResponseEntity<UserDto> response = restTemplate
		            .exchange(uri, HttpMethod.GET, entity, UserDto.class,params);
			
			
		return response.getBody();	
	}

	
}
