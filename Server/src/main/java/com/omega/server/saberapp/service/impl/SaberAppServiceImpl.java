package com.omega.server.saberapp.service.impl;

import static java.util.Collections.emptyList;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.omega.server.saberapp.entity.Centre;
import com.omega.server.saberapp.entity.Materia;
import com.omega.server.saberapp.entity.Pregunta;
import com.omega.server.saberapp.entity.Puntuacion;
import com.omega.server.saberapp.entity.Respuesta;
import com.omega.server.saberapp.entity.User;
import com.omega.server.saberapp.persistence.repository.CentresRepository;
import com.omega.server.saberapp.persistence.repository.MateriesRepository;
import com.omega.server.saberapp.persistence.repository.PreguntasRepository;
import com.omega.server.saberapp.persistence.repository.PuntuacionRepository;
import com.omega.server.saberapp.persistence.repository.RespuestasRepository;
import com.omega.server.saberapp.persistence.repository.UserRepository;
import com.omega.server.saberapp.service.SaberAppServiceI;
/**
 * 
 * @author Ramon
 *Lleva a cabo las acciones que se dan en la base de datos de la aplicación
 */
@Service
public class SaberAppServiceImpl implements SaberAppServiceI, UserDetailsService {

	 @Autowired
	 private UserRepository userRepository;
	 @Autowired
	 private BCryptPasswordEncoder bCryptPasswordEncoder;
	 @Autowired 
	 private CentresRepository centreRepository;
	 @Autowired
	 private MateriesRepository materiesRepository;
	 @Autowired
	 private PreguntasRepository preguntasRepository;
	 @Autowired
	 private RespuestasRepository respuestasRepository;
	 @Autowired
	 private PuntuacionRepository puntuacionRepository;
	
	@Override
	public User getUser(String name) {
		
		return userRepository.findByName(name);
	}
	@Override
	public User getUserByEmail(String email) {
		return userRepository.findByEmail(email);
		
	}
	
	@Override
	public Optional<User> getUserById(Long id) {
		
		return userRepository.findById(id);
		
	}
	
	@Override
	public User createUser(User user) {
		
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		return userRepository.save(user);
		
	}
	
	@Override
	public User updateUser(User user) {
		
		return userRepository.save(user);	
		
	}
	@Override
	public List<User> getAllUsers(){
		List<User> lista=new ArrayList<User>();
		for(User user: userRepository.findAll()) {
			user.setPassword(null);
			user.setEmail(null);
			lista.add(user);	
		};
		return lista;
	}

	@Override
	public String login(String nickname, String password) {
		// TODO Auto-generated method stub
		return null;
	}

	
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		com.omega.server.saberapp.entity.User usuarioEntity = userRepository.findByEmail(email);
		if (usuarioEntity == null) {
			throw new UsernameNotFoundException(email);
		}
		return new org.springframework.security.core.userdetails.User(usuarioEntity.getEmail(), usuarioEntity.getPassword(), emptyList());
	}
	
	@Override
	public List<Centre> getCentres(){
			
		return centreRepository.findAll();
	}
	
	@Override
	public List<Materia> getMateries(){		
		return materiesRepository.findAll();	
	}
	@Override
	public long deleteUser(String email){	
		
		return userRepository.deleteByEmail(email);
		
	}
	@Override
	public User updateUserPassword(String email, String oldPassword, String newPassword) {
		User user=getUserByEmail(email);

		//validación si el password antiguo es correcto guardamos el nuevo
		
		if(bCryptPasswordEncoder.matches(oldPassword, user.getPassword())) {
			user.setPassword(bCryptPasswordEncoder.encode(newPassword));
			return userRepository.save(user);
		}else {
			return null;
		}		
	}
	@Override
	public List<Pregunta> getPreguntasByMateria(String materia) {
		Materia materiaEntity=materiesRepository.findByNombre(materia);
		return preguntasRepository.findByMateria(materiaEntity);
	}
	@Override
	public List<Pregunta> getAllPreguntas() {
		
		return preguntasRepository.findAll();
	}
	@Override
	public Pregunta createPregunta(Pregunta pregunta) {
		
		return preguntasRepository.save(pregunta);
	}
	@Override
	public List<Respuesta> getAllRespuestasByAlumno(Long idAlumno) {

		return respuestasRepository.findByUserId(idAlumno);
	}
	@Override
	public Respuesta createRespuesta(Respuesta respuesta) {
		return respuestasRepository.save(respuesta);
		
	}
	@Override
	public List<Puntuacion> getPuntuaciones(){
		
		return puntuacionRepository.findAll();
	}
	
	
	
	
}
