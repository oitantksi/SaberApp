package com.omega.server.consumer.client;

import com.omega.server.consumer.dto.MateriaDto;

public interface GestionPreguntasI {

	public MateriaDto[] getAllMateries(String token);
	
}
