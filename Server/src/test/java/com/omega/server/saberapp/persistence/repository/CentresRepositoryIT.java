package com.omega.server.saberapp.persistence.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.omega.server.saberapp.entity.Centre;
import com.omega.server.saberapp.entity.Materia;

@SpringBootTest
public class CentresRepositoryIT {
	 @Autowired
	 private CentresRepository centresRepository;
	 
	 /**
	  * 
	  * @author Ramon
	  *Salva un centro y lo recupera.
	  */
	 @Test
	 public void saveWithName_whenFindByName_thenReturnMateria() {
	     // given
	     Centre entity = new Centre("Instituto Maragall");
	     centresRepository.save(entity);
	   
	  
	     // when
	     Centre found = centresRepository.findByCentre("Instituto Maragall");
	  
	     // then
	     assertEquals(found.getCentre(), entity.getCentre(), "Ha encontrado el centro esperado");
	 }
	 
	 /**
	  * @ramon
	  * Prueba para ver que devuelve un registro(arte) almacenado en bbdd previamente a levantar el contexto
	  */
	 @Test
	 public void whenFindByNombre_thenReturnMateria() {
	     // given
	     String nombre = "IOC";
	    
	   
	  
	     // when
	     Centre found =  centresRepository.findByCentre(nombre);
	  
	     // then
	     assertEquals(found.getCentre(), "IOC", "Ha encontrado la materia esperada");
	     assertEquals(found.getId(),1);
	 }
	 
}
