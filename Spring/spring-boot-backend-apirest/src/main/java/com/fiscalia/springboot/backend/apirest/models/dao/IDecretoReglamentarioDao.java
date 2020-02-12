package com.fiscalia.springboot.backend.apirest.models.dao;

import org.springframework.data.repository.CrudRepository;

import com.fiscalia.springboot.backend.apirest.models.entity.DecretoReglamentario;

public interface IDecretoReglamentarioDao  extends CrudRepository<DecretoReglamentario, Long>{

}
