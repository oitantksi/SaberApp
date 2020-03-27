package com.omega.server.saberapp.persistence.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.sql.Timestamp;

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
}
