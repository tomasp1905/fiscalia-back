package com.fiscalia.springboot.backend.apirest.models.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.fiscalia.springboot.backend.apirest.models.entity.Decreto;

public interface IDecreto {
	
	public List<Decreto> findAll(); 
	
	public Page<Decreto> findAll(Pageable pageable); 
	
	public Decreto findById(long id);
	
	public Decreto save(Decreto decreto); //se almacena la Ley que va a retornar
	
	public void delete (long id);
	
	public List<Decreto> findDecretoByTitulo(String term);
	
	public List<Decreto> findDecretoByNumero(String term);
	
	public List<Decreto> findDecretoByAnio(String term);

	
	
}
