package com.omega.server.saberapp.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;


@Embeddable
public class PuntuacionId implements Serializable {
	
	@Column(name="alumno_id")
	private Long idAlumno;
	@Column(name="materia_id")
	private Long idMateria;
	public Long getIdAlumno() {
		return idAlumno;
	}
	public void setIdAlumno(Long idAlumno) {
		this.idAlumno = idAlumno;
	}
	public Long getIdMateria() {
		return idMateria;
	}
	public void setIdMateria(Long idMateria) {
		this.idMateria = idMateria;
	}
	public PuntuacionId(Long idAlumno, Long idMateria) {
		super();
		this.idAlumno = idAlumno;
		this.idMateria = idMateria;
	}
	public PuntuacionId() {
		super();
		// TODO Auto-generated constructor stub
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idAlumno == null) ? 0 : idAlumno.hashCode());
		result = prime * result + ((idMateria == null) ? 0 : idMateria.hashCode());
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
		PuntuacionId other = (PuntuacionId) obj;
		if (idAlumno == null) {
			if (other.idAlumno != null)
				return false;
		} else if (!idAlumno.equals(other.idAlumno))
			return false;
		if (idMateria == null) {
			if (other.idMateria != null)
				return false;
		} else if (!idMateria.equals(other.idMateria))
			return false;
		return true;
	}
	
	

}
