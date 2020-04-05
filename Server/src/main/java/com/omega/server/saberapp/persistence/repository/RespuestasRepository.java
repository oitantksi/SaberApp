package com.omega.server.saberapp.persistence.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.omega.server.saberapp.entity.Respuesta;
import com.omega.server.saberapp.entity.User;



public interface RespuestasRepository  extends CrudRepository<Respuesta, Long>{
		public List<Respuesta> findAll();
		public List<Respuesta> findByUser(User user);
		
}

