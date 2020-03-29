package com.omega.server.consumer.dto;

import java.sql.Timestamp;


public class PreguntaDto {
	Long id;
	String pregunta;	
	String respuesta1;
	String respuesta2;
	String respuesta3 ;
	String respuesta4;
	Integer respuesta_correcta;
	Timestamp fecha_aparicion;
	MateriaDto materia;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getPregunta() {
		return pregunta;
	}
	public void setPregunta(String pregunta) {
		this.pregunta = pregunta;
	}
	public String getRespuesta1() {
		return respuesta1;
	}
	public void setRespuesta1(String respuesta1) {
		this.respuesta1 = respuesta1;
	}
	public String getRespuesta2() {
		return respuesta2;
	}
	public void setRespuesta2(String respuesta2) {
		this.respuesta2 = respuesta2;
	}
	public String getRespuesta3() {
		return respuesta3;
	}
	public void setRespuesta3(String respuesta3) {
		this.respuesta3 = respuesta3;
	}
	public String getRespuesta4() {
		return respuesta4;
	}
	public void setRespuesta4(String respuesta4) {
		this.respuesta4 = respuesta4;
	}
	public Integer getRespuesta_correcta() {
		return respuesta_correcta;
	}
	public void setRespuesta_correcta(Integer respuesta_correcta) {
		this.respuesta_correcta = respuesta_correcta;
	}
	public Timestamp getFecha_aparicion() {
		return fecha_aparicion;
	}
	public void setFecha_aparicion(Timestamp fecha_aparicion) {
		this.fecha_aparicion = fecha_aparicion;
	}
	public MateriaDto getMateria() {
		return materia;
	}
	public void setMateria(MateriaDto materia) {
		this.materia = materia;
	}
	
}
