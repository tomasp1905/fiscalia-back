
package com.fiscalia.springboot.backend.apirest.controllers;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fiscalia.springboot.backend.apirest.models.entity.Decreto;
import com.fiscalia.springboot.backend.apirest.models.entity.DecretoLey;
import com.fiscalia.springboot.backend.apirest.models.service.IDecretoLeyService;

@CrossOrigin(origins = { "http://localhost:4200" }) // conexion con Angular
@RestController
@RequestMapping("/api")
public class DecretoLeyRestController {

	@Autowired // llamo al servicio
	private IDecretoLeyService decretoService;

	@GetMapping("/decretosley") // mapeo a la URL
	public List<DecretoLey> index() {
		return decretoService.findAll();
	}
	
	@GetMapping("/decretosley/page/{page}") // mapeo a la URL
	public Page<DecretoLey> index(@PathVariable Integer page) {
		Pageable pageable = PageRequest.of(page,8);
		return decretoService.findAll(pageable);
	}

	@GetMapping("/decretosley/{id}")
	public ResponseEntity<?> show(@PathVariable Long id) {
		DecretoLey decreto = null;
		Map<String, Object> response = new HashMap<>();
		try {
			decreto = decretoService.findById(id);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar la cosulta en la base de datos");
			response.put("mensaje", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}

		if (decreto == null) {
			response.put("mensaje", "El decreto ley ID: ".concat(id.toString().concat(" no existe en la Base de Datos! ")));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<DecretoLey>(decreto, HttpStatus.OK);
	}

	@PostMapping("/decretosley")
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<?> create(@RequestBody DecretoLey decreto) {
		DecretoLey decretonew = null;
		Map<String, Object> response = new HashMap<>();
		try {
			decretonew = decretoService.save(decreto);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar el insert en la base de datos");
			response.put("mensaje", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		response.put("mensaje", "El decreto ley ha sido creado con exito!");
		response.put("decreto", decretonew);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}

	@PutMapping("/decretosley/{id}")
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<?> update(@RequestBody DecretoLey decreto, @PathVariable Long id) {
		DecretoLey decretoActual = decretoService.findById(id);
		DecretoLey decretoUpdate = null;
		Map<String, Object> response = new HashMap<>();
		if (decretoActual == null) {
			response.put("mensaje", "Error no se pudo editar el decreto ley : "
					.concat(id.toString().concat(" no existe en la Base de Datos! ")));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		try {
			decretoActual.setTitulo(decreto.getTitulo());
			decretoActual.setNumero(decreto.getNumero());
			decretoActual.setAnio(decreto.getAnio());
			decretoActual.setfechaSancion(decreto.getfechaSancion());
			decretoActual.setPublicacionBO(decreto.getPublicacionBO());
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al actualizar el decreto ley en la base de datos");
			response.put("mensaje", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		decretoUpdate = decretoService.save(decretoActual);
		response.put("mensaje", "El decreto ley ha sido actualizado con exito!");
		response.put("decreto", decretoUpdate);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}

	@DeleteMapping("/decretosley/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ResponseEntity<?> delete(@PathVariable Long id) {

		Map<String, Object> response = new HashMap<>();
		try {
			DecretoLey decreto = decretoService.findById(id);
			String nombreDecretoAnterior = decreto.getArchivo();

			decretoService.delete(id);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al eliminar el decreto ley de la base de datos");
			response.put("mensaje", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		response.put("mensaje", "El decreto ley ha sido eliminado con exito!");
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}

	@PostMapping("/decretosley/upload")
	public ResponseEntity<?> upload(@RequestParam("archivo") MultipartFile archivo, @RequestParam("id") Long id) {
		Map<String, Object> response = new HashMap<>();
		DecretoLey decreto = decretoService.findById(id);

		if (!archivo.isEmpty()) {
			String nombreArchivo = UUID.randomUUID().toString() + "_" +archivo.getOriginalFilename().replace("", "");
			Path rutaArchivo = Paths.get("uploads").resolve(nombreArchivo).toAbsolutePath();

			try {
				Files.copy(archivo.getInputStream(), rutaArchivo);
			} catch (IOException e) {
				response.put("mensaje", "Error al subir el archivo decreto ley de la Base de Datos" + nombreArchivo);
				response.put("error", e.getMessage().concat(": ").concat(e.getCause().getMessage()));
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			}

			String nombreArchivoAnterior = decreto.getArchivo();
			if (nombreArchivoAnterior != null && nombreArchivoAnterior.length() > 0) {
				Path rutaArchivoAnterior = Paths.get("uploads").resolve(nombreArchivoAnterior).toAbsolutePath();
				File archivoAnterior = rutaArchivoAnterior.toFile();
				if (archivoAnterior.exists() && archivoAnterior.canRead()) {
					archivoAnterior.delete();
				}
			}

			decreto.setArchivo(nombreArchivo);
			decretoService.save(decreto);
			response.put("decreto", decreto);
			response.put("mensaje", "Has subido correctamente el archivo: " + nombreArchivo);
		}
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}
	
	@GetMapping("/uploads/archivodecreto/{nombreArchivo:.+}")
	public ResponseEntity<Resource> verFoto(@PathVariable String nombreArchivo) {

		Path rutaArchivo = Paths.get("uploads").resolve(nombreArchivo).toAbsolutePath();
		Resource recurso = null;

		try {
			recurso = new UrlResource(rutaArchivo.toUri());
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}

		if (!recurso.exists() && !recurso.isReadable()) {
			throw new RuntimeException("Error no se pudo cargar el archivo: " + nombreArchivo);
		}
		HttpHeaders cabecera = new HttpHeaders();
		cabecera.setContentType(MediaType.parseMediaType("application/pdf"));
		cabecera.add(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + recurso.getFilename() + "\"");
		cabecera.setCacheControl("must-revalidate, post-check=0, pre-check=0");
		return new ResponseEntity<Resource>(recurso, cabecera, HttpStatus.OK);
	}
	
	@GetMapping("/decretosley/filtrar-titulo/{term}")
	@ResponseStatus(HttpStatus.OK)
	public List<DecretoLey> filtrarTitulo(@PathVariable String term){
		return decretoService.findDecretoByTitulo(term);
	}
	
	@GetMapping("/decretosley/filtrar-numero/{term}")
	@ResponseStatus(HttpStatus.OK)
	public List<DecretoLey> filtrarNumero(@PathVariable String term){
		return decretoService.findDecretoByNumero(term);
	}
	
	@GetMapping("/decretosley/filtrar-anio/{term}")
	@ResponseStatus(HttpStatus.OK)
	public List<DecretoLey> filtrarAnio(@PathVariable String term){
		return decretoService.findDecretoByAnio(term);
	}


}
