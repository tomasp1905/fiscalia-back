package com.fiscalia.springboot.backend.apirest.models.service;

import java.util.List;

import com.fiscalia.springboot.backend.apirest.models.entity.LeyProvincial;
import com.fiscalia.springboot.backend.apirest.models.entity.Usuario;

public interface IUsuarioService {

	Usuario findByUsername(String username);

	public Usuario save(Usuario usuario);

	public List<Usuario> findAll();

}
