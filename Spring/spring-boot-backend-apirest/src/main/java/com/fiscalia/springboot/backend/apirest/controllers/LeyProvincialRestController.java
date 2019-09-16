package com.fiscalia.springboot.backend.apirest.controllers;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
import com.fiscalia.springboot.backend.apirest.models.service.ILeyProvincialService;


@CrossOrigin(origins= {"http://localhost:4200"}) //conexion con Angular
@RestController 
@RequestMapping("/api")
public class LeyProvincialRestController {
	
	@Autowired //llamo al servicio
	private ILeyProvincialService leyProvincialService;
	
	@GetMapping("/leyesProvinciales") //mapeo a la URL
	public List<LeyProvincial> index() {
		return leyProvincialService.findAll();
	}
	
	@GetMapping("/leyesProvinciales/{id}")
	public LeyProvincial show(@PathVariable Long id) {
		return leyProvincialService.findById(id);
	}
	
	@PostMapping("/leyesProvinciales")
	@ResponseStatus(HttpStatus.CREATED)
	public LeyProvincial create(@RequestBody LeyProvincial leyprovincial) {
		return leyProvincialService.save(leyprovincial);
	}
	
	@PutMapping("/leyesProvinciales/{id}")
	@ResponseStatus(HttpStatus.CREATED)
	public LeyProvincial create(@RequestBody LeyProvincial leyprovincial, @PathVariable Long id) {
		LeyProvincial leyprovincialActual = leyProvincialService.findById(id);
		leyprovincialActual.setTitulo(leyprovincial.getTitulo());
		leyprovincialActual.setFechaSancion(leyprovincialActual.getFechaSancion());
		leyprovincialActual.setNumero(leyprovincialActual.getNumero());
		leyprovincialActual.setPublicacionBO(leyprovincialActual.getPublicacionBO());
		
		return leyProvincialService.save(leyprovincialActual); 
	}
	
	@DeleteMapping("/leyesProvinciales/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable Long id) {
		leyProvincialService.delete(id);
	}
	
	
}
