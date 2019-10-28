package com.fiscalia.springboot.backend.apirest.models.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
		return leyProvincialDao.findById(id).orElse(null);
	}

	
	@Transactional
	public LeyProvincial save(LeyProvincial leyprovincial) {
		// TODO Auto-generated method stub
		return leyProvincialDao.save(leyprovincial);
	}

	
	@Transactional
	public void delete(long id) {
		leyProvincialDao.deleteById(id);
		
	}


	@Override
	@Transactional(readOnly = true )
	public List<LeyProvincial> findLeyProvincialByTitulo(String term) {
		return (List<LeyProvincial>)  leyProvincialDao.findByTitulo(term);
	}


	@Override
	public List<LeyProvincial> findLeyProvincialByNumero(String term) {
		return (List<LeyProvincial>)  leyProvincialDao.findByNumero(term);
	}


	@Override
	@Transactional(readOnly = true )
	public Page<LeyProvincial> findAll(Pageable pageable) {
		return leyProvincialDao.findAll(pageable);
	}
	

}
