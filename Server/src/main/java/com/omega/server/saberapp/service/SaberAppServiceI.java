package com.omega.server.saberapp.service;

import java.util.List;
import java.util.Optional;

import com.omega.server.saberapp.entity.Centre;
import com.omega.server.saberapp.entity.Materia;
import com.omega.server.saberapp.entity.Pregunta;
import com.omega.server.saberapp.entity.Respuesta;
import com.omega.server.saberapp.entity.User;

/**
 * 
 * @author Ramon
 *Interfaz que tiene las funcionalidades principales de la aplicación
 *si crece demasiado es conveniente, agrupar las interfaces por funciónes que sirven 
 *o repositorios a los que llaman
 */
public interface SaberAppServiceI {

	public User getUser(String name);
	public Optional<User> getUserById(Long id);
	public User createUser(User user);
	public String login(String nickname, String password);
	public User getUserByEmail(String email);
	public List<Centre> getCentres();
	public long deleteUser(String email);
	public User updateUser(User user);
	public User updateUserPassword(String email, String oldPassword, String newPassword);
	public List<Materia> getMateries();
	public List<Pregunta> getPreguntasByMateria(String materia);
	public List<Pregunta> getAllPreguntas();
	public Pregunta createPregunta(Pregunta pregunta);
	public List<Respuesta> getAllRespuestasByAlumno(Long idAlumno) ;
}
