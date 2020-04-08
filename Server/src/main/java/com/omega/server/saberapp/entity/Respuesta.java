package com.omega.server.saberapp.entity;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name="respuestas")
public class Respuesta implements Serializable {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="respuesta_id")
	Long id;
	
	@Column 
	Long respuesta;
	
	
	@Column(name="pregunta_id")
	Long preguntaId;
	
	
	@Column(name="alumno_id")
	Long userId;
	
	@Column(name="fecha_respuesta")
	Timestamp fechaRespuesta;
	
}
