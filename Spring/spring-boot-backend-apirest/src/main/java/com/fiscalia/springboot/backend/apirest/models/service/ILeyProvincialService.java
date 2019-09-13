package com.fiscalia.springboot.backend.apirest.models.service;

import java.util.List;

import com.fiscalia.springboot.backend.apirest.models.entity.LeyProvincial;

public interface ILeyProvincialService {
	
	public List<LeyProvincial> findAll(); 

}
