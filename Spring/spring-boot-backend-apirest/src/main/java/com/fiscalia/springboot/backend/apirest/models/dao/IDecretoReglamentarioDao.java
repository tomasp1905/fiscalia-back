package com.fiscalia.springboot.backend.apirest.models.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.fiscalia.springboot.backend.apirest.models.entity.DecretoReglamentario;


public interface IDecretoReglamentarioDao  extends JpaRepository<DecretoReglamentario, Long> {
	
	@Query("select p from DecretoReglamentario p where p.numero like ?1")
	public List<DecretoReglamentario> findByNumero(String term);
	

	
}
