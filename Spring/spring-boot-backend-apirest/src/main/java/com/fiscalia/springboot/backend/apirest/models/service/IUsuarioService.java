 package com.fiscalia.springboot.backend.apirest.models.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.fiscalia.springboot.backend.apirest.models.entity.LeyProvincial;
import com.fiscalia.springboot.backend.apirest.models.entity.Usuario;

public interface IUsuarioService {

	Usuario findByUsername(String username);

	public Usuario save(Usuario usuario);

	public List<Usuario> findAll();

	public Page<Usuario> findAll(Pageable pageable);

	public Usuario findById(long id);

	public void delete (long id);
}
