package com.fiscalia.springboot.backend.apirest.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@Secured("ROLE_ADMIN")
	@GetMapping("/usuarios") // mapeo a la URL
	public List<Usuario> index() {
		return usuarioService.findAll();
	}
	
	@Secured("ROLE_ADMIN")
	@GetMapping("/usuarios/page/{page}") 
	public Page<Usuario> index(@PathVariable Integer page) {
		Pageable pageable = PageRequest.of(page,10);
		return usuarioService.findAll(pageable);
	}
	 
	@Secured("ROLE_ADMIN")
	@PostMapping("/usuarios")
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<?> create(@RequestBody Usuario usuario) {
		String password = usuario.getPassword();
		String passwordBCrypt = passwordEncoder.encode(password);
		usuario.setPassword(passwordBCrypt);
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
	
	@PutMapping("/usuarios/{id}")
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<?> update(@RequestBody Usuario usuario, @PathVariable Long id) {
		Usuario usuarioActual = usuarioService.findById(id);
		Usuario usuarioUpdate = null;
		Map<String, Object> response = new HashMap<>();
		if (usuarioActual == null) {
			response.put("mensaje", "Error no se pudo editar el usuario ID: "
					.concat(id.toString().concat(" no existe en la Base de Datos! ")));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		try {
			usuarioActual.setUsername(usuario.getUsername());
			usuarioActual.setApellido(usuario.getApellido());
			usuarioActual.setEmail(usuario.getEmail());
			usuarioActual.setNombre(usuario.getNombre());
			
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al actualizar el usuario en la base de datos");
			response.put("mensaje", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		usuarioUpdate = usuarioService.save(usuarioActual);
		response.put("mensaje", "La ley ha sido actualizado con exito!");
		response.put("ley", usuarioUpdate);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}
	
	@Secured("ROLE_ADMIN")
	@DeleteMapping("/usuarios/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ResponseEntity<?> delete(@PathVariable Long id) {

		Map<String, Object> response = new HashMap<>();
		try {
			Usuario usuario = usuarioService.findById(id);
			usuarioService.delete(id);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al eliminar la ley de la base de datos");
			response.put("mensaje", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		response.put("mensaje", "La ley ha sido eliminado con exito!");
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}

	
	

} 
