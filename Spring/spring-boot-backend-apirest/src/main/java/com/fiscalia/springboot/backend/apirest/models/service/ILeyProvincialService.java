package com.fiscalia.springboot.backend.apirest.models.service;

import java.util.List;

import com.fiscalia.springboot.backend.apirest.models.entity.LeyProvincial;

public interface ILeyProvincialService {
	
	public List<LeyProvincial> findAll(); 
	
	public LeyProvincial findById(long id);
	
	public LeyProvincial save(LeyProvincial leyprovincial); //se almacena la Ley que va a retornar
	
	public void delete (long id);
	
	public List<LeyProvincial> findLeyProvincialByTitulo(String term);
	
	
}
