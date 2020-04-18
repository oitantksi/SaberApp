package com.omega.server.saberapp.persistence.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.omega.server.saberapp.entity.Puntuacion;
import com.omega.server.saberapp.entity.PuntuacionId;

public interface PuntuacionRepository extends CrudRepository<Puntuacion,PuntuacionId >{

	List<Puntuacion> findAll();
}
