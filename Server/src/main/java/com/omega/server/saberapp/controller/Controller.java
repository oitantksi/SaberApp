package com.omega.server.saberapp.controller;




import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.omega.server.saberapp.service.SaberAppService;

@RestController
public class Controller {
	
	@Autowired
	SaberAppService service;

	@GetMapping("/helloworld")
	public String helloworld(@RequestParam String name) {
		
		return "Hola mundo: "+ name + " Bienvenido a saberapp";
	}
	
	@GetMapping("/user/{user}")
	public String getUser(@PathVariable("user") String user) {
		
		return service.getUser(user).toString();
		
	}
	
	@GetMapping("/user/id/{id}")
	public String getUserBy(@PathVariable("id") Long id) {
		
		return service.getUserById(id).toString();
		
	}
}
