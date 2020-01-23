package com.fiscalia.springboot.backend.apirest.models.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.fiscalia.springboot.backend.apirest.models.entity.Decreto;


//CRUD --> tiene implementados los metodos get,post, etc.
public interface IDecretoDao  extends JpaRepository<Decreto, Long> {


	@Query("select p from Decreto p where p.titulo like %?1%")
	public List<Decreto> findByTitulo(String term);
	
	public List<Decreto> findByTituloContainingIgnoreCase(String term);
	
	public List<Decreto> findByTituloStartingWithIgnoreCase(String term);
	
	@Query("select p from Decreto p where p.numero like ?1")
	public List<Decreto> findByNumero(String term);
	
	@Query("select p from Decreto p where p.anio like ?1")
	public List<Decreto> findByAnio(String term);

	
}
