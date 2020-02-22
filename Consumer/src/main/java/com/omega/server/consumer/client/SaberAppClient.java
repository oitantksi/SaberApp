package com.omega.server.consumer.client;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.omega.server.consumer.dto.UserDto;

public class SaberAppClient {

	
	public static void main(String[]args) {
		SaberAppClient saberAppClient=new SaberAppClient();
		
		System.out.println("Calling get for helloworld...");
		System.out.println(saberAppClient.helloworl("Omega"));
		
		System.out.println("Calling get for user...by id");
		UserDto user=saberAppClient.getUserById(Long.parseLong("1"));
		System.out.println(user.toString());
		
		System.out.println("Calling get for user...by name");
		user=saberAppClient.getUserByName("montse");
		System.out.println(user.toString());
		
		
		
	}
	public SaberAppClient(){
		
		
	}
	public String helloworl(String name) {
		String uri="http://localhost:8080/helloworld";
		UriComponentsBuilder uriComp=UriComponentsBuilder.fromHttpUrl(uri).queryParam("name", name);
		RestTemplate restTemplate = new RestTemplate();
		
		return restTemplate.getForObject(uriComp.toUriString(),String.class);
		
		
		
		
	}
	
	public UserDto getUserById(Long id) {
		String uri="http://localhost:8080/user/id/{id}";

	    RestTemplate restTemplate = new RestTemplate();
		Map<String, Long> params = new HashMap<String, Long>();
	    params.put("id", id);
		UserDto userDto = restTemplate.getForObject(uri, UserDto.class, params);
		
		return userDto;
		
		
	}
	public UserDto getUserByName(String name) {
		String uri="http://localhost:8080/user/{user}";
	    RestTemplate restTemplate = new RestTemplate();
		Map<String, String> params = new HashMap<String, String>();
	    params.put("user", name);
		UserDto userDto = restTemplate.getForObject(uri,UserDto.class, params);
		
		return userDto;
		
		
		
		
	}
}
