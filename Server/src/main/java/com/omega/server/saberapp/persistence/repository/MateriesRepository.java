package com.omega.server.saberapp.persistence.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.omega.server.saberapp.entity.Materia;
/**
 * 
 * @author Ramon
 *Repositorio de materias.
 */
public interface MateriesRepository extends CrudRepository<Materia, Long>{
	List<Materia> findAll();
	Materia findByNombre(String nombre);
}
