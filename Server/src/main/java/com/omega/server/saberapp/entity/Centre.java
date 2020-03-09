package com.omega.server.saberapp.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name="centres")
public class Centre implements Serializable  {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2881687767630534687L;
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	Long id;
	@NotEmpty(message="El centro tiene que tener un nombre")
	@Column
	String centre;
	
	
	
	public Centre() {
	
	}
	public Centre(String nombre) {
		this.centre = nombre;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getCentre() {
		return centre;
	}
	public void setCentre(String centre) {
		this.centre = centre;
	}
	
	
}
