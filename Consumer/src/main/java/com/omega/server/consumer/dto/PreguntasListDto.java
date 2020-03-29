package com.omega.server.consumer.dto;

import java.util.ArrayList;
import java.util.List;

public class PreguntasListDto {
	private List<PreguntaDto> preguntas;
	
	public List<PreguntaDto> getMateries() {
		return preguntas;
	}

	public void setMateries(List<PreguntaDto> preguntas) {
		this.preguntas = preguntas;
	}

	public PreguntasListDto() {
		preguntas = new ArrayList<>();
    }
	
}
