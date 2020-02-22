package com.omega.server.saberapp.controller;




import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.omega.server.saberapp.entity.User;
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
	public User getUser(@PathVariable("user") String user) {
		
		User userObject=service.getUser(user);
		return userObject;
		
	}
	
	@GetMapping("/user/id/{id}")
	public User getUserBy(@PathVariable("id") Long id) {
		
		Optional<User> user=service.getUserById(id);
		
		
		return user.get();
		
	}
}
