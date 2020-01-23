package com.fiscalia.springboot.backend.apirest.models.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fiscalia.springboot.backend.apirest.models.dao.IDecretoLeyDao;
import com.fiscalia.springboot.backend.apirest.models.entity.Decreto;
import com.fiscalia.springboot.backend.apirest.models.entity.DecretoLey;


@Service //determinamos que esta clase es un Servicio
public class DecretoLeyServiceImpl implements IDecretoLeyService {

	
	@Autowired 
	private IDecretoLeyDao  decretoDao;
	
	@Transactional(readOnly = true) //porque es una consulta de solo lectura, es opcional pero es recomendable ponerlo.
	public List<DecretoLey> findAll() {
		return (List<DecretoLey>) decretoDao.findAll();
	}

	
	@Transactional
	public void delete(long id) {
		decretoDao.deleteById(id);
		
	}


	@Override
	@Transactional(readOnly = true )
	public List<DecretoLey> findDecretoByTitulo(String term) {
		return (List<DecretoLey>)  decretoDao.findByTitulo(term);
	}


	@Override
	public List<DecretoLey> findDecretoByNumero(String term) {
		return (List<DecretoLey>)  decretoDao.findByNumero(term);
	}
	
	@Override
	public List<DecretoLey> findDecretoByAnio(String term) {
		return (List<DecretoLey>)  decretoDao.findByAnio(term);
	}


	@Override
	@Transactional(readOnly = true )
	public Page<DecretoLey> findAll(Pageable pageable) {
		return decretoDao.findAll(pageable);
	}


	@Override
	public DecretoLey findById(long id) {
		return decretoDao.findById(id).orElse(null);
	}


	@Override
	public DecretoLey save(DecretoLey decreto) {
		return decretoDao.save(decreto);
	}
	
	


	

}
