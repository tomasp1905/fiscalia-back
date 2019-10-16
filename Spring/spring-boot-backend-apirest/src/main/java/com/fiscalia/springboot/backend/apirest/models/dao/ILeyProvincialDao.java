package com.fiscalia.springboot.backend.apirest.models.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.fiscalia.springboot.backend.apirest.models.entity.LeyProvincial;

//CRUD --> tiene implementados los metodos get,post, etc.
public interface ILeyProvincialDao  extends CrudRepository<LeyProvincial, Long> {


	@Query("select p from LeyProvincial p where p.titulo like %?1%")
	public List<LeyProvincial> findByTitulo(String term);
	
	public List<LeyProvincial> findByTituloContainingIgnoreCase(String term);
	
	public List<LeyProvincial> findByTituloStartingWithIgnoreCase(String term);
}
