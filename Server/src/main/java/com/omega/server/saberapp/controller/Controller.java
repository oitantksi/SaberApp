package com.omega.server.saberapp.controller;




import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.omega.server.consumer.dto.PasswordDto;
import com.omega.server.consumer.dto.UserDto;
import com.omega.server.saberapp.entity.Centre;
import com.omega.server.saberapp.entity.Materia;
import com.omega.server.saberapp.entity.Pregunta;
import com.omega.server.saberapp.entity.Puntuacion;
import com.omega.server.saberapp.entity.Respuesta;
import com.omega.server.saberapp.entity.User;
import com.omega.server.saberapp.service.SaberAppServiceI;
/**
 * 
 * @author Ramon
 *Controlador, recibe las peticiones que llegan, llama a un service para resoverla y la devuelve.
 */
@RestController
public class Controller {
	
	@Autowired
	SaberAppServiceI service;
	

	@GetMapping("/helloworld")
	public String helloworld(@RequestParam String name) {
		
		return "Hola mundo: "+ name + " Bienvenido a saberapp";
	}
	
	@GetMapping("/user/{user}")
	public User getUser(@PathVariable("user") String user) {
		
		User userObject=service.getUser(user);
		return userObject;
		
	}

	@GetMapping("/user /id/{id}")
	public User getUserBy(@PathVariable("id") Long id) {
		
		Optional<User> user=service.getUserById(id);
		
		
		return user.get();
		
	}
	
	@GetMapping("/user/email/{email}")        
	public User getUserBy(@PathVariable("email") String email) {
		
		User user=service.getUserByEmail(email);
		
		return user;
		
	}
	
	/**
	 * 
	 * @param request
	 * @return
	 */
	@PostMapping(path = "/user", consumes = "application/json", produces = "application/json")
	public User postUser( @Valid @RequestBody UserDto request) {
		
		User user=new User(request.getName(), 
						   request.getCognom(),
						   request.getEmail(),
						   request.getNickname(),
						   request.getPassword(),
						   request.getCenter(), 
						   request.getRol());
		
		return service.createUser(user);
		
	} 
	/**
	 * Modifica usuario a excepción de password
	 * @param email
	 * @param request
	 * @return
	 */
	@PutMapping(path="/user/{email}")
	public User putUser(@PathVariable("email") String email,@RequestBody UserDto request) {
		User user=service.getUserByEmail(email);
		user.setName(request.getName());
		user.setCognom(request.getCognom());   
		user.setEmail(request.getEmail());   
		user.setNickname(request.getNickname());   
		//user.setPassword(request.getPassword());   
		user.setCenter(request.getCenter());    
		user.setRol(request.getRol());   
		
	
		
		return service.updateUser(user);
	
		
	}
	
	/**
	 * Actualización del mail de usuario
	 * @param email
	 * @param request
	 * @return
	 */
	@PutMapping(path="/user/password/{email}")
	public User putUserPassword(@PathVariable("email") String email,@RequestBody PasswordDto request) {
	
		return service.updateUserPassword(email, request.getOldPassword(),request.getNewPassword());
		
	}
	@DeleteMapping(path ="/user/{email}")
	public long deleteUser(@PathVariable("email") String email) {
	
		return service.deleteUser(email);		
	}
	
	@GetMapping("/centres")
	public List<Centre> getAllCentres(){
		
		return service.getCentres();
		
	}
	@GetMapping("/materies")
	public List<Materia> getAllMateries(){
		
		return service.getMateries();
		
	}
	@GetMapping("/preguntes/{materia}")
	public List<Pregunta> getPreguntasByMateria(@PathVariable("materia") String materia){
		
		return service.getPreguntasByMateria(materia);
		
	}
	@GetMapping("/preguntes/totes")
	public List<Pregunta> getAllPreguntas(){
		
		return service.getAllPreguntas();
		
	}
	@PostMapping(path = "/pregunta", consumes = "application/json", produces = "application/json")
	public Pregunta postPregunta( @Valid @RequestBody Pregunta request) {
		
		
		
		return service.createPregunta(request);
		
	}
	@GetMapping("/respostes/alumno/{idAlumno}")
	public List<Respuesta> getAllRepuestasAlumno(@PathVariable("idAlumno") Long idAlumno){
		
		return service.getAllRespuestasByAlumno(idAlumno);
		
	}
	@PostMapping(path = "/respostes/alumno", consumes = "application/json", produces = "application/json")
	public Respuesta postRespuesta(@RequestBody Respuesta request) {
		
		return service.createRespuesta(request);
	}
	
	@GetMapping("/user/all")
	public List<User> getAllUsers(){
		
		return service.getAllUsers();
	}
	
	@GetMapping(path="/puntuacions")
	public List<Puntuacion> getPuntuaciones(){	
		
		return service.getPuntuaciones();
		
	}
	
	@GetMapping(
            value = "/user/image/{id}",
            produces = MediaType.IMAGE_PNG_VALUE
          )
          public @ResponseBody byte[] getImageWithMediaType(@PathVariable("id") Long id) throws IOException {
              InputStream in = getClass()
                .getResourceAsStream("/images/user_"+id+".png");

              return IOUtils.toByteArray(in);
          }
	
	@PostMapping(value = "/user/uploadImage/{userId}")
	public Boolean submit(@RequestParam("file") MultipartFile file,@PathVariable("userId") Long userId) {
		   try {

	            // Get the file and save it somewhere
	            byte[] bytes = file.getBytes();
	      
	            Path path = Paths.get(System.getProperty("user.dir")+"/src/main/resources/images/user_"+userId+".png");
	            Files.write(path, bytes);


	        } catch (IOException e) {
	            e.printStackTrace();
	            return false;
	        }
	    return true;
	}
	
	
}
