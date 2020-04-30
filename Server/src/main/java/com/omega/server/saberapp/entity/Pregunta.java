package com.omega.server.saberapp.entity;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="preguntas")
public class Pregunta implements Serializable {


	/**
	 * 
	 */
	private static final long serialVersionUID = 8725762492951299247L;
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	@Column
	private String pregunta;
	@Column
	private String respuesta1;
	@Column
	private String respuesta2;
	@Column
	String respuesta3 ;
	@Column
	private String respuesta4;
	@Column
	private Integer respuesta_correcta;
	@Column
	@JoinColumn(name="materia_id")
	@JoinColumn(name="materia_id")
	private Timestamp fecha_aparicion;
	@ManyToOne(cascade = {CascadeType.ALL})
	@JoinColumn(name="materia_id")
	Materia materia;
	
	public Pregunta(String pregunta, String respuesta1, String respuesta2, String respuesta3, String respuesta4,
			Integer respuesta_correcta, Timestamp fecha_aparicion, Materia materia) {
		super();
		this.pregunta = pregunta;
		this.respuesta1 = respuesta1;
		this.respuesta2 = respuesta2;
		this.respuesta3 = respuesta3;
		this.respuesta4 = respuesta4;
		this.respuesta_correcta = respuesta_correcta;
		this.fecha_aparicion = fecha_aparicion;
		this.materia = materia;
	}
	public Pregunta() {
		super();
		// TODO Auto-generated constructor stub
	}
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
	public Materia getMateria() {
		return materia;
	}
	public void setMateria(Materia materia) {
		this.materia = materia;
	}
}
