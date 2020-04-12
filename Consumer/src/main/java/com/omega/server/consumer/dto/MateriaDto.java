package com.omega.server.consumer.dto;

public class MateriaDto {
	
	Long id;

	String nombre;

	public MateriaDto(Long id, String nombre) {
		super();
		this.id = id;
		this.nombre = nombre;
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

	public MateriaDto() {
		super();
	}
	
	
	
}
