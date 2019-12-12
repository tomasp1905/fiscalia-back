package com.fiscalia.springboot.backend.apirest.models.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fiscalia.springboot.backend.apirest.models.dao.IDecretoReglamentarioDao;
import com.fiscalia.springboot.backend.apirest.models.entity.DecretoReglamentario;


@Service //determinamos que esta clase es un Servicio
public class DecretoReglamentarioServiceImpl implements IDecretoReglamentarioService {

	
	@Autowired 
	private IDecretoReglamentarioDao  decretoReglamentarioDao;
	
	@Transactional(readOnly = true) //porque es una consulta de solo lectura, es opcional pero es recomendable ponerlo.
	public List<DecretoReglamentario> findAll() {
		return (List<DecretoReglamentario>) decretoReglamentarioDao.findAll();
	}

	
	@Transactional
	public void delete(long id) {
		decretoReglamentarioDao.deleteById(id);
		
	}


	@Override
	public List<DecretoReglamentario> findDecretoByNumero(String term) {
		return (List<DecretoReglamentario>)  decretoReglamentarioDao.findByNumero(term);
	}


	@Override
	@Transactional(readOnly = true )
	public Page<DecretoReglamentario> findAll(Pageable pageable) {
		return decretoReglamentarioDao.findAll(pageable);
	}


	@Override
	public DecretoReglamentario findById(long id) {
		return decretoReglamentarioDao.findById(id).orElse(null);
	}


	@Override
	public DecretoReglamentario save(DecretoReglamentario decretoReglamentario) {
		return decretoReglamentarioDao.save(decretoReglamentario);
	}



	

}
