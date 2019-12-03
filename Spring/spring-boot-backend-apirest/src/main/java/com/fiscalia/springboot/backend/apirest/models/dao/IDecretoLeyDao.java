package com.fiscalia.springboot.backend.apirest.models.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.fiscalia.springboot.backend.apirest.models.entity.DecretoLey;


//CRUD --> tiene implementados los metodos get,post, etc.
public interface IDecretoLeyDao  extends JpaRepository<DecretoLey, Long> {


	@Query("select p from Decreto p where p.titulo like %?1%")
	public List<DecretoLey> findByTitulo(String term);
	
	public List<DecretoLey> findByTituloContainingIgnoreCase(String term);
	
	public List<DecretoLey> findByTituloStartingWithIgnoreCase(String term);
	
	@Query("select p from Decreto p where p.numero like ?1")
	public List<DecretoLey> findByNumero(String term);
	

	
}
