package com.fiscalia.springboot.backend.apirest.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.fiscalia.springboot.backend.apirest.models.entity.LeyProvincial;
import com.fiscalia.springboot.backend.apirest.models.entity.Usuario;
import com.fiscalia.springboot.backend.apirest.models.service.IUsuarioService;

@CrossOrigin(origins = { "http://localhost:4200" }) 
@RestController
@RequestMapping("/api")

public class UsuarioRestController {
	
	@Autowired
	private IUsuarioService usuarioService;
	
	@GetMapping("/usuarios") // mapeo a la URL
	public List<Usuario> index() {
		return usuarioService.findAll();
	}
	
	@PostMapping("/usuarios")
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<?> create(@RequestBody Usuario usuario) {
		Usuario usuarionew = null;
		Map<String, Object> response = new HashMap<>();
		try {
			usuarionew = usuarioService.save(usuario);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al insertar el usuario en la base de datos");
			response.put("mensaje", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		response.put("mensaje", "El usuario ha sido creado con exito!");
		response.put("ley", usuarionew);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
		}

}
