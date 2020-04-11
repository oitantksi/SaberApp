package com.omega.server.saberapp.entity;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="respuestas")
public class Respuesta implements Serializable {

	

	/**
	 * 
	 */
	private static final long serialVersionUID = 6678057026491452955L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="respuesta_id")
	Long id;
	
	@Column 
	Long respuesta;
	
	
	@Column(name="pregunta_id")
	Long preguntaId;
	
	
	@Column(name="alumno_id")
	Long userId;
	
//	@JsonFormat(pattern="yyyy-MM-dd")
	@Column(name="fecha_respuesta")
	Timestamp fechaRespuesta;
	
	public Respuesta(Long id, Long respuesta, Long preguntaId, Long userId, Timestamp fechaRespuesta) {
		super();
		this.id = id;
		this.respuesta = respuesta;
		this.preguntaId = preguntaId;
		this.userId = userId;
		this.fechaRespuesta = fechaRespuesta;
	}

	public Respuesta() {
		super();
		// TODO Auto-generated constructor stub
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
