package com.omega.server.saberapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
/**
 * 
 * @author Ramon
 *Arranca la aplicación, levanta una base de datos embebida que <br>no será persistente si no se 
 *configura adecuadamente</br>.
 */

@SpringBootApplication
public class ServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServerApplication.class, args);
	}
	



}
