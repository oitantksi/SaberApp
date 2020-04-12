package com.omega.server.consumer.client.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.sql.Time;
import java.sql.Timestamp;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.omega.server.consumer.client.GestionPreguntasI;
import com.omega.server.consumer.client.LoginRegistroI;
import com.omega.server.consumer.dto.MateriaDto;
import com.omega.server.consumer.dto.PreguntaDto;
import com.omega.server.consumer.dto.RespuestaDto;
@SpringBootTest
public class GestionPreguntasImplIT {
	@Autowired
	GestionPreguntasI gestionPreguntas;
	@Autowired
	LoginRegistroI loginRegistro;
	
	@Test
	public void whenFindAll_thenReturnMateriesArray() {
		String token=loginRegistro.login("master@omega.com", "omega");
		MateriaDto[] list=gestionPreguntas.getAllMateries(token);
	
		assertEquals("Geografia", list[0].getNombre());
		assertEquals(4, list.length);
		
	}
	@Test
	public void whenFindByMateria_thenReturnPreguntasArray() {
		String token=loginRegistro.login("master@omega.com", "omega");
		PreguntaDto[] list=gestionPreguntas.getPreguntasByMateria(token,"Geografia");
	
		assertEquals("Geografia", list[0].getMateria().getNombre());
		assertTrue(list.length>0);
		
	}
	@Test
	public void whenFindAll_thenReturnPreguntasArray() {
		String token=loginRegistro.login("master@omega.com", "omega");
		PreguntaDto[] list=gestionPreguntas.getAllPreguntas(token);
	

		assertTrue(list.length==16);
		
	
	}
	@Test
	public void whenFind_thenReturnRespuestasArray() {
		String token=loginRegistro.login("master@omega.com", "omega");
		String idAlumno= "1";
		RespuestaDto[] list=gestionPreguntas.getRespuestasAlumno(token, idAlumno);
				
		assertTrue(list.length>0);		
				
				
	}
	@Test
	public void whenSaveRepuesta_thenReturnRespuesta() {
		String token=loginRegistro.login("master@omega.com", "omega");
		Timestamp fechaRespuesta=new Timestamp(System.currentTimeMillis());
		RespuestaDto respuesta= new RespuestaDto(null, new Long(2), new Long(2), new Long(1), fechaRespuesta);
		RespuestaDto repuestaSalvada=gestionPreguntas.registraRespuestaAlumno(token, respuesta);
				
		assertEquals(respuesta.getRespuesta(), repuestaSalvada.getRespuesta());		
		assertNotNull(repuestaSalvada.getId());
				
				
	}
	
	@Test
	public void whenSavePregunta_thenReturnPregunta() {
		String token=loginRegistro.login("master@omega.com", "omega");
		Timestamp fechaPregunta=new Timestamp(System.currentTimeMillis());
		MateriaDto materia=new MateriaDto(new Long(2), "Historia");

		
		PreguntaDto pregunta= new PreguntaDto(null, "Año que se descrubrió el coronavirus", "2020", "2019", "2018", "1918", 2, fechaPregunta, materia);
		PreguntaDto repuestaSalvada=gestionPreguntas.savePregunta(token, pregunta);
				
		assertEquals(pregunta.getPregunta(), repuestaSalvada.getPregunta());		
		assertNotNull(repuestaSalvada.getId());
				
				
	}
	
	
}
				
				
