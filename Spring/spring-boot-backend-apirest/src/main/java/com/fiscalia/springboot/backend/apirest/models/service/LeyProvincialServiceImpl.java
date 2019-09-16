package com.fiscalia.springboot.backend.apirest.models.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fiscalia.springboot.backend.apirest.models.dao.ILeyProvincialDao;
import com.fiscalia.springboot.backend.apirest.models.entity.LeyProvincial;

@Service //determinamos que esta clase es un Servicio
public class LeyProvincialServiceImpl implements ILeyProvincialService {

	
	@Autowired 
	private ILeyProvincialDao  leyProvincialDao;
	
	@Transactional(readOnly = true) //porque es una consulta de solo lectura, es opcional pero es recomendable ponerlo.
	public List<LeyProvincial> findAll() {
		return (List<LeyProvincial>) leyProvincialDao.findAll();
	}
	
	
	@Transactional(readOnly = true)
	public LeyProvincial findById(long id) {
		// TODO Auto-generated method stub
		return leyProvincialDao.findById(id).orElse(null);
	}

	
	@Transactional
	public LeyProvincial save(LeyProvincial leyprovincial) {
		// TODO Auto-generated method stub
		return leyProvincialDao.save(leyprovincial);
	}

	
	@Transactional
	public void delete(long id) {
		// TODO Auto-generated method stub
		leyProvincialDao.deleteById(id);;
		
	}
	

}
