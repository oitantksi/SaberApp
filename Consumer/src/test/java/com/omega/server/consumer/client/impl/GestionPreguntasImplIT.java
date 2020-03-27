package com.omega.server.consumer.client.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.omega.server.consumer.client.GestionPreguntasI;
import com.omega.server.consumer.client.LoginRegistroI;
import com.omega.server.consumer.dto.MateriaDto;
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
}
