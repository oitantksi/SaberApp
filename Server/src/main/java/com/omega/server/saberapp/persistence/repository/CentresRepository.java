package com.omega.server.saberapp.persistence.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.omega.server.saberapp.entity.Centre;

public interface CentresRepository extends CrudRepository<Centre, Long>{
	List<Centre> findAll();
	Centre findByCentre(String centre);
}
