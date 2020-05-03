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
	private Long puntos;

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
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((puntos == null) ? 0 : puntos.hashCode());
		result = prime * result + ((puntuacionId == null) ? 0 : puntuacionId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Puntuacion other = (Puntuacion) obj;
		if (puntos == null) {
			if (other.puntos != null)
				return false;
		} else if (!puntos.equals(other.puntos))
			return false;
		if (puntuacionId == null) {
			if (other.puntuacionId != null)
				return false;
		} else if (!puntuacionId.equals(other.puntuacionId))
			return false;
		return true;
	}
}
