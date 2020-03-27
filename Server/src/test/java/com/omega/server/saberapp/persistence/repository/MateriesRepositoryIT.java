package com.omega.server.saberapp.persistence.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.omega.server.saberapp.entity.Materia;

@SpringBootTest
public class MateriesRepositoryIT {
	 @Autowired
	 private MateriesRepository materiesRepository;
	 
	 /**
	  * 
	  * @author Ramon
	  *Salva una materia y la recupera.
	  */
	 @Test
	 public void saveWithName_whenFindByName_thenReturnMateria() {
	     // given
	     Materia entity = new Materia("Biografía");
	     materiesRepository.save(entity);
	   
	  
	     // when
	     Materia found = materiesRepository.findByNombre("Biografía");
	  
	     // then
	     assertEquals(found.getNombre(), entity.getNombre(), "Ha encontrado la materia esperada");
	 }
	 
	 /**
	  * @ramon
	  * Prueba para ver que devuelve un registro(arte) almacenado en bbdd previamente a levantar el contexto
	  */
	 @Test
	 public void whenFindByNombre_thenReturnMateria() {
	     // given
	     String nombre = "Arte";
	     
	   
	  
	     // when
	     Materia found = materiesRepository.findByNombre("Arte");
	  
	     // then
	     assertEquals(found.getNombre(), "Arte", "Ha encontrado la materia esperada");
	     assertEquals(found.getId(),3);
	 }
	 
}
