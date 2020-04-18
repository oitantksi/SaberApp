package com.omega.server.saberapp.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="puntuacion")
public class Puntuacion implements Serializable  {

	private static final long serialVersionUID = -5158941904682637516L;
	/**
	 * 
	 */
	@EmbeddedId
    private PuntuacionId puntuacionId;

	@Column(name="puntos")
	Long puntos;

	public PuntuacionId getPuntuacionId() {
		return puntuacionId;
	}

	public void setPuntuacionId(PuntuacionId puntuacionId) {
		this.puntuacionId = puntuacionId;
	}

	public Long getPuntos() {
		return puntos;
	}

	public void setPuntos(Long puntos) {
		this.puntos = puntos;
	}

	public Puntuacion(PuntuacionId puntuacionId, Long puntos) {
		super();
		this.puntuacionId = puntuacionId;
		this.puntos = puntos;
	}

	public Puntuacion() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
}
