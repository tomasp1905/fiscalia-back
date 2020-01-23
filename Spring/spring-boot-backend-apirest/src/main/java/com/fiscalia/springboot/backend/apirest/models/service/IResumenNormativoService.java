package com.fiscalia.springboot.backend.apirest.models.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.fiscalia.springboot.backend.apirest.models.entity.ResumenNormativo;

public interface IResumenNormativoService {
	
	public List<ResumenNormativo> findAll(); 
	
	public Page<ResumenNormativo> findAll(Pageable pageable); 
	
	public ResumenNormativo findById(long id);
	
	public ResumenNormativo save(ResumenNormativo resumenNormativo); //se almacena la Ley que va a retornar
	
	public void delete (long id);
	
//	public List<ResumenNormativo> findResumenNormativoByAnio(String term);

	
	
}
