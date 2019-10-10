package com.fiscalia.springboot.backend.apirest.models.service;

import com.fiscalia.springboot.backend.apirest.models.entity.Usuario;

public interface IUsuarioService {

	Usuario findByUsername(String username);

}
