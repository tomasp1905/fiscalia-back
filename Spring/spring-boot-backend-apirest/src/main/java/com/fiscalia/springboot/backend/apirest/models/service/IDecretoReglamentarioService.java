package com.fiscalia.springboot.backend.apirest.models.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.fiscalia.springboot.backend.apirest.models.entity.DecretoReglamentario;


public interface IDecretoReglamentarioService {
	
	public List<DecretoReglamentario> findAll(); 
	
	public Page<DecretoReglamentario> findAll(Pageable pageable); 
	
	public DecretoReglamentario findById(long id);
	
	public DecretoReglamentario save(DecretoReglamentario decretoReglamentario); //se almacena la Ley que va a retornar
	
	public void delete (long id);

	
	public List<DecretoReglamentario> findDecretoByNumero(String term);

	
	
}
