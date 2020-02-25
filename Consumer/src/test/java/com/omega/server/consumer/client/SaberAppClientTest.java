package com.omega.server.consumer.client;

import com.omega.server.consumer.dto.UserDto;

public class SaberAppClientTest {
	public static void main(String[]args) {
		SaberAppClient saberAppClient=new SaberAppClient();
		
		System.out.println("Calling get for login...");
		String token=saberAppClient.login("server", "omega");
		System.out.println(saberAppClient.login("server", "omega"));
		
		System.out.println("Calling get for helloworld...");
		System.out.println(saberAppClient.helloworld("Omega",token).toString());
		
		System.out.println("Calling get for user...by id");
		UserDto user=saberAppClient.getUserById(Long.parseLong("1"), token);
		System.out.println(user.toString());
	
		System.out.println("Calling get for user...by name");
		user=saberAppClient.getUserByName("montse", token);
		System.out.println(user.toString());
			
	}
}
