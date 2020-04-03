package com.omega.server.saberapp.persistence.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.sql.Timestamp;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.omega.server.saberapp.entity.Materia;
import com.omega.server.saberapp.entity.Pregunta;

@SpringBootTest
public class PreguntasRepositoryIT {
	@Autowired
	private PreguntasRepository preguntasRepository;
	@Autowired
	private MateriesRepository materiesRepository;
	 
	@Test
	public void savePregunta() {
		Materia materia= materiesRepository.findByNombre("Historia");
		Timestamp timestamp=new Timestamp(20200127);
		Pregunta pregunta=new Pregunta("Año de la revolución francesa", "1879", "1789", "2001", "711", 2, timestamp, materia);
		Pregunta salvada=preguntasRepository.save(pregunta);
		
		assertEquals(pregunta.getPregunta(),salvada.getPregunta());
			
	}
	
	@Test
	public void recuperarPreguntasPorMateria() {
		Materia materia= materiesRepository.findByNombre("Geografia");
		
		List<Pregunta> lista=preguntasRepository.findByMateria(materia);
		
		assertTrue(lista.size()>0);
		
		
	}
	
	
	@Test
	public void recuperarTodasPreguntas() {
		Materia materia= materiesRepository.findByNombre("Geografia");
		
		List<Pregunta> lista=preguntasRepository.findAll();
		
		assertTrue(lista.size()==16);
		
		
	}
}
