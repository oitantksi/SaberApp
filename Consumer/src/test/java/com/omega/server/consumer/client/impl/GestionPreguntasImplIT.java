package com.omega.server.consumer.client.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.omega.server.consumer.client.GestionPreguntasI;
import com.omega.server.consumer.client.LoginRegistroI;
import com.omega.server.consumer.dto.MateriaDto;
import com.omega.server.consumer.dto.PreguntaDto;
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
}
