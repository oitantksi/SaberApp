package com.omega.server.consumer.client;

import com.omega.server.consumer.dto.MateriaDto;
import com.omega.server.consumer.dto.PreguntaDto;
import com.omega.server.consumer.dto.RespuestaDto;

public interface GestionPreguntasI {

	public MateriaDto[] getAllMateries(String token);
	
	public PreguntaDto[] getPreguntasByMateria(String token, String materia);
	
	public PreguntaDto savePregunta(String token, PreguntaDto pregunta);
	
	public PreguntaDto[] getAllPreguntas(String token);
	
	public RespuestaDto[] getRespuestasAlumno(String token, String idAlumno);
	
	public RespuestaDto registraRespuestaAlumno(String token,RespuestaDto respuesta);
}
