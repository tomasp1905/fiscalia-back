package com.fiscalia.springboot.backend.apirest.models.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fiscalia.springboot.backend.apirest.models.dao.IResumenNormativoDao;
import com.fiscalia.springboot.backend.apirest.models.entity.ResumenNormativo;


@Service //determinamos que esta clase es un Servicio
public class ResumenNormativoServiceImpl implements IResumenNormativoService {

	
	@Autowired 
	private IResumenNormativoDao  resumenNormativoDao;
	
	@Transactional(readOnly = true) //porque es una consulta de solo lectura, es opcional pero es recomendable ponerlo.
	public List<ResumenNormativo> findAll() {
		return (List<ResumenNormativo>) resumenNormativoDao.findAll();
	}
	
	
	@Transactional(readOnly = true)
	public ResumenNormativo findById(long id) {
		return resumenNormativoDao.findById(id).orElse(null);
	}

	
	@Transactional
	public ResumenNormativo save(ResumenNormativo resumenNormativo) {
		// TODO Auto-generated method stub
		return resumenNormativoDao.save(resumenNormativo);
	}

	
	@Transactional
	public void delete(long id) {
		resumenNormativoDao.deleteById(id);
		
	}


	@Override
	public List<ResumenNormativo> findResumenNormativoByAnio(String term) {
		return (List<ResumenNormativo>)  resumenNormativoDao.findByAnio(term);
	}


	@Override
	@Transactional(readOnly = true )
	public Page<ResumenNormativo> findAll(Pageable pageable) {
		return resumenNormativoDao.findAll(pageable);
	}
	

}
