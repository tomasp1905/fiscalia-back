package com.fiscalia.springboot.backend.apirest.models.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.fiscalia.springboot.backend.apirest.models.entity.LeyProvincial;

public interface ILeyProvincialService {
	
	public List<LeyProvincial> findAll(); 
	
	public Page<LeyProvincial> findAll(Pageable pageable); 
	
	public LeyProvincial findById(long id);
	
	public LeyProvincial save(LeyProvincial leyprovincial); //se almacena la Ley que va a retornar
	
	public void delete (long id);
	
	public List<LeyProvincial> findLeyProvincialByTitulo(String term);
	
	public List<LeyProvincial> findLeyProvincialByNumero(String term);

	
	
}
