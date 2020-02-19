package com.omega.server.saberapp.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {

	@GetMapping("/helloworld")
	public String helloworld(@RequestParam String name) {
		
		return "Hola mundo: "+ name + " Bienvenido a saberapp";
	}
	
}
