package com.omega.server.saberapp.persistence.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.omega.server.saberapp.entity.Materia;
import com.omega.server.saberapp.entity.Pregunta;

public interface PreguntasRepository  extends CrudRepository<Pregunta, Long>{
	List<Pregunta> findAll();
	List<Pregunta> findByMateria(Materia materia);
}

