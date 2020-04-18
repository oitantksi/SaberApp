package com.omega.server.saberapp.persistence.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.omega.server.saberapp.entity.Puntuacion;
import com.omega.server.saberapp.entity.PuntuacionId;

@SpringBootTest
public class PuntuacionRepositoryIT {
	@Autowired
	private PuntuacionRepository puntuacionRepository;

	@Test
	public void savePuntuacion() {
		PuntuacionId puntuacionId= new PuntuacionId(new Long(2),new Long(2));
		Puntuacion puntuacion=new Puntuacion(puntuacionId,new Long(2));
		
		Puntuacion puntuacionGuardada=puntuacionRepository.save(puntuacion);
		
		assertEquals(puntuacion,puntuacionGuardada);
	
	}
	@Test
	public void getAllPuntuaciones() {
		List<Puntuacion> lista= puntuacionRepository.findAll();
		assertTrue(lista.size()==2);
		
	}


}
