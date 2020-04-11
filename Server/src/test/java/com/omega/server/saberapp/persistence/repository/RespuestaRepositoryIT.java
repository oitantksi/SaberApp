package com.omega.server.saberapp.persistence.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.omega.server.saberapp.entity.Respuesta;

@SpringBootTest
public class RespuestaRepositoryIT {
	
	@Autowired
	private RespuestasRepository respuestaRepository;
	
//	@Test
//	public void obtenerRespuestaAlumno() {
//		
//		Long userId=new Long(1);
//		List<Respuesta> lista=respuestaRepository.findByUser(userId);
//		Pregunta pregunta1 = lista.get(0).getPreguntas().get(0);
//		
//		assertEquals("País amb més habitants del món",pregunta1.getPregunta());
//	}
	
	@Test
	public void obtenerRespuestaAlumno() {
		
		Long id=new Long(1);
		Optional<Respuesta> lista=respuestaRepository.findById(id);
		System.out.println("Respuesta: "+ lista.toString());
		
		
	}
	@Test
	public void grabarRespuestaAlumno() {
		
		
		
	}
	

}
