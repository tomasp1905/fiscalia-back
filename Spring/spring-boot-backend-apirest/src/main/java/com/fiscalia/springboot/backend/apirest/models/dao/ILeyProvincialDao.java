package com.fiscalia.springboot.backend.apirest.models.dao;

import org.springframework.data.repository.CrudRepository;

import com.fiscalia.springboot.backend.apirest.models.entity.LeyProvincial;

//CRUD --> tiene implementados los metodos get,post, etc.
public interface ILeyProvincialDao  extends CrudRepository<LeyProvincial, Long> {

}
