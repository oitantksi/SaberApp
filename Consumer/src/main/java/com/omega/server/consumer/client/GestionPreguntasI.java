package com.omega.server.consumer.client;

import com.omega.server.consumer.dto.MateriaDto;
import com.omega.server.consumer.dto.PreguntaDto;

public interface GestionPreguntasI {

	public MateriaDto[] getAllMateries(String token);
	
	public PreguntaDto[] getPreguntasByMateria(String token, String materia);
	
	public PreguntaDto savePregunta(String token, PreguntaDto pregunta);
	
}
