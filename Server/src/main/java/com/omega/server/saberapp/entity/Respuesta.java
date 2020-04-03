package com.omega.server.saberapp.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="respuestas")
public class Respuesta implements Serializable {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="respuesta_id")
	Long id;
	
	@Column 
	Long respuesta;
	
	@ManyToOne
	@JoinColumn(name="pregunta_id")
	Pregunta pregunta;
	
	@ManyToOne
	@JoinColumn(name="alumno_id")
	User user;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Pregunta getPregunta() {
		return pregunta;
	}

	public void setPregunta(Pregunta pregunta) {
		this.pregunta = pregunta;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Long getRespuesta() {
		return respuesta;
	}

	public void setRespuesta(Long respuesta) {
		this.respuesta = respuesta;
	}

}
