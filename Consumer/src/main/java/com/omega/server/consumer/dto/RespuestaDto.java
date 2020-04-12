package com.omega.server.consumer.dto;

import java.sql.Timestamp;

public class RespuestaDto {
	
	Long id;

	Long respuesta;
	
	Long preguntaId;	

	Long userId;
	
	Timestamp fechaRespuesta;
	
	public RespuestaDto() {

	}

	public RespuestaDto(Long id, Long respuesta, Long preguntaId, Long userId, Timestamp fechaRespuesta) {
		super();
		this.id = id;
		this.respuesta = respuesta;
		this.preguntaId = preguntaId;
		this.userId = userId;
		this.fechaRespuesta = fechaRespuesta;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getRespuesta() {
		return respuesta;
	}

	public void setRespuesta(Long respuesta) {
		this.respuesta = respuesta;
	}

	public Long getPreguntaId() {
		return preguntaId;
	}

	public void setPreguntaId(Long preguntaId) {
		this.preguntaId = preguntaId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Timestamp getFechaRespuesta() {
		return fechaRespuesta;
	}

	public void setFechaRespuesta(Timestamp fechaRespuesta) {
		this.fechaRespuesta = fechaRespuesta;
	}

}
