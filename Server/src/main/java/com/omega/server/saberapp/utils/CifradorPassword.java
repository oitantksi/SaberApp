package com.omega.server.saberapp.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
/**
 * 
 * @author Ramon
 *Clase de utilidades para cifrar la password.
 *En la BBDD la contraseña del usuario ha de ir cifrada, para el juego de datos inicial debemos insertar el campo password cifrado
 *Funcionamiento:
 *-Cambiar password por el string que se desee cifrar, run as java application, nos devolverá el string cifrado
 */
public class CifradorPassword {
	
	
	public static void main (String[]args) {
		BCryptPasswordEncoder encoder=new BCryptPasswordEncoder();
		
		String password= "omega";
		String passwordCifrada=encoder.encode(password);
		
		System.out.println(passwordCifrada);
		
	}
}
