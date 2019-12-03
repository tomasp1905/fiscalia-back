package com.fiscalia.springboot.backend.apirest.models.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.fiscalia.springboot.backend.apirest.models.entity.DecretoLey;

public interface IDecretoLeyService {
	
	public List<DecretoLey> findAll(); 
	
	public Page<DecretoLey> findAll(Pageable pageable); 
	
	public DecretoLey findById(long id);
	
	public DecretoLey save(DecretoLey decreto); //se almacena la Ley que va a retornar
	
	public void delete (long id);
	
	public List<DecretoLey> findDecretoByTitulo(String term);
	
	public List<DecretoLey> findDecretoByNumero(String term);

	
	
}
