package com.omega.server.consumer.dto;




public class CentreDto {
	
	
	Long id;
	String centre;

	public CentreDto() {
	
	}
	public CentreDto(String nombre) {
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
