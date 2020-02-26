package com.omega.server.saberapp.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

/**
 * 
 * @author Ramon
 *Clase que mapea la tabla de usuarios de base de datos
 */

@Entity
@Table(name="materies")
public class Materia implements Serializable {
	

	private static final long serialVersionUID = -3685040136903431502L;
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	Long id;
	@NotEmpty(message="La materia tiene que tener un  nombre")
	@Column(name="materia")
	String nombre;
	
	public Materia(String nombre) {
		this.nombre = nombre;
	}
	public Materia() {
		
	}
	
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	
	
	
}
