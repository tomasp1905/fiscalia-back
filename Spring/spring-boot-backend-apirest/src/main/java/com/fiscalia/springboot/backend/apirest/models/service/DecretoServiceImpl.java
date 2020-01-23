package com.fiscalia.springboot.backend.apirest.models.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fiscalia.springboot.backend.apirest.models.dao.IDecretoDao;
import com.fiscalia.springboot.backend.apirest.models.entity.Decreto;
import com.fiscalia.springboot.backend.apirest.models.service.IDecreto;

@Service //determinamos que esta clase es un Servicio
public class DecretoServiceImpl implements IDecreto {

	
	@Autowired 
	private IDecretoDao  decretoDao;
	
	@Transactional(readOnly = true) //porque es una consulta de solo lectura, es opcional pero es recomendable ponerlo.
	public List<Decreto> findAll() {
		return (List<Decreto>) decretoDao.findAll();
	}

	
	@Transactional
	public void delete(long id) {
		decretoDao.deleteById(id);
		
	}


	@Override
	@Transactional(readOnly = true )
	public List<Decreto> findDecretoByTitulo(String term) {
		return (List<Decreto>)  decretoDao.findByTitulo(term);
	}


	@Override
	public List<Decreto> findDecretoByNumero(String term) {
		return (List<Decreto>)  decretoDao.findByNumero(term);
	}


	@Override
	@Transactional(readOnly = true )
	public Page<Decreto> findAll(Pageable pageable) {
		return decretoDao.findAll(pageable);
	}


	@Override
	public Decreto findById(long id) {
		return decretoDao.findById(id).orElse(null);
	}


	@Override
	public Decreto save(Decreto decreto) {
		return decretoDao.save(decreto);
	}
	
	@Override
	public List<Decreto> findDecretoByAnio(String term) {
		return (List<Decreto>)  decretoDao.findByAnio(term);
	}
	

}
