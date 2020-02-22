package com.omega.server.saberapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
/**
 * 
 * @author Ramon
 *Arranca la aplicación, levanta una base de datos embebida que no será persistente si no se 
 *configura adecuadamente.
 */

@SpringBootApplication
public class ServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServerApplication.class, args);
	}
	



}
