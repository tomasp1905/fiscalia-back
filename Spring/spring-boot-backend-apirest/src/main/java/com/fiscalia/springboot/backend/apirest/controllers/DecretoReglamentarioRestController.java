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

import com.fiscalia.springboot.backend.apirest.models.entity.DecretoReglamentario;
import com.fiscalia.springboot.backend.apirest.models.service.IDecretoReglamentarioService;

@CrossOrigin(origins = { "http://localhost:4200" }) // conexion con Angular
@RestController
@RequestMapping("/api")
public class DecretoReglamentarioRestController {

	@Autowired // llamo al servicio
	private IDecretoReglamentarioService decretoReglamentarioService;

	@GetMapping("/decretosReglamentario") // mapeo a la URL
	public List<DecretoReglamentario> index() {
		return decretoReglamentarioService.findAll();
	}
	
	@GetMapping("/decretosReglamentario/page/{page}") // mapeo a la URL
	public Page<DecretoReglamentario> index(@PathVariable Integer page) {
		Pageable pageable = PageRequest.of(page,4);
		return decretoReglamentarioService.findAll(pageable);
	}

	@GetMapping("/decretosReglamentario/{id}")
	public ResponseEntity<?> show(@PathVariable Long id) {
		DecretoReglamentario decretoReglamentario = null;
		Map<String, Object> response = new HashMap<>();
		try {
			decretoReglamentario = decretoReglamentarioService.findById(id);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar la cosulta en la base de datos");
			response.put("mensaje", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}

		if (decretoReglamentario == null) {
			response.put("mensaje", "El decreto reglamentario ID: ".concat(id.toString().concat(" no existe en la Base de Datos! ")));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<DecretoReglamentario>(decretoReglamentario, HttpStatus.OK);
	}

	@PostMapping("/decretosReglamentario")
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<?> create(@RequestBody DecretoReglamentario decretoReglamentario) {
		DecretoReglamentario decretoReglamentarionew = null;
		Map<String, Object> response = new HashMap<>();
		try {
			decretoReglamentarionew = decretoReglamentarioService.save(decretoReglamentario);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar el insert en la base de datos");
			response.put("mensaje", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		response.put("mensaje", "El decreto reglamentario ha sido creado con exito!");
		response.put("decreto reglamentario", decretoReglamentarionew);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}

	@PutMapping("/decretosReglamentario/{id}")
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<?> update(@RequestBody DecretoReglamentario decretoReglamentario, @PathVariable Long id) {
		DecretoReglamentario decretoReglamentarioActual = decretoReglamentarioService.findById(id);
		DecretoReglamentario decretoReglamentarioUpdate = null;
		Map<String, Object> response = new HashMap<>();
		if (decretoReglamentarioActual == null) {
			response.put("mensaje", "Error no se pudo editar el decreto reglamentario: "
					.concat(id.toString().concat(" no existe en la Base de Datos! ")));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		try {
			decretoReglamentarioActual.setNumero(decretoReglamentarioActual.getNumero());
			decretoReglamentarioActual.setAnio(decretoReglamentarioActual.getAnio());
			decretoReglamentarioActual.setfechaEmision(decretoReglamentarioActual.getfechaEmision());
			decretoReglamentarioActual.setPublicacionBO(decretoReglamentarioActual.getPublicacionBO());
			decretoReglamentarioActual.setRelacion(decretoReglamentarioActual.getRelacion());
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al actualizar el decreto reglamentario en la base de datos");
			response.put("mensaje", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		decretoReglamentarioUpdate = decretoReglamentarioService.save(decretoReglamentarioActual);
		response.put("mensaje", "El decreto reglamentario ha sido actualizado con exito!");
		response.put("decreto reglamentario", decretoReglamentarioUpdate);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}

	@DeleteMapping("/decretosReglamentario/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ResponseEntity<?> delete(@PathVariable Long id) {

		Map<String, Object> response = new HashMap<>();
		try {
			DecretoReglamentario decretoReglamentario = decretoReglamentarioService.findById(id);
			String nombreDecretoAnterior = decretoReglamentario.getArchivo();

			decretoReglamentarioService.delete(id);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al eliminar el decreto de la base de datos");
			response.put("mensaje", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		response.put("mensaje", "El decreto ha sido eliminado con exito!");
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}

	@PostMapping("/decretosReglamentario/upload")
	public ResponseEntity<?> upload(@RequestParam("archivo") MultipartFile archivo, @RequestParam("id") Long id) {
		Map<String, Object> response = new HashMap<>();
		DecretoReglamentario decretoReglamentario = decretoReglamentarioService.findById(id);

		if (!archivo.isEmpty()) {
			String nombreArchivo = UUID.randomUUID().toString() + "_" +archivo.getOriginalFilename().replace("", "");
			String nombreArchivo2 = archivo.getOriginalFilename().replace("", "");
			
			Path rutaArchivo = Paths.get("uploads").resolve(nombreArchivo).toAbsolutePath();

			try {
				Files.copy(archivo.getInputStream(), rutaArchivo);
			} catch (IOException e) {
				response.put("mensaje", "Error al subir el archivo decreto de la Base de Datos" + nombreArchivo);
				response.put("error", e.getMessage().concat(": ").concat(e.getCause().getMessage()));
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			}

			String nombreArchivoAnterior = decretoReglamentario.getArchivo();
			if (nombreArchivoAnterior != null && nombreArchivoAnterior.length() > 0) {
				Path rutaArchivoAnterior = Paths.get("uploads").resolve(nombreArchivoAnterior).toAbsolutePath();
				File archivoAnterior = rutaArchivoAnterior.toFile();
				if (archivoAnterior.exists() && archivoAnterior.canRead()) {
					archivoAnterior.delete();
				}
			}

			decretoReglamentario.setArchivo(nombreArchivo);
			decretoReglamentarioService.save(decretoReglamentario);
			response.put("decreto reglamentario", decretoReglamentario);
			response.put("mensaje", "Has subido correctamente el archivo: " + nombreArchivo2);
		}
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}
	
	@GetMapping("/uploads/archivoReglamentario/{nombreArchivo:.+}")
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
		cabecera.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + recurso.getFilename() + "\"");
		return new ResponseEntity<Resource>(recurso, cabecera, HttpStatus.OK);
	}

	
	@GetMapping("/decretosReglamentario/filtrar-numero/{term}")
	@ResponseStatus(HttpStatus.OK)
	public List<DecretoReglamentario> filtrarNumero(@PathVariable String term){
		return decretoReglamentarioService.findDecretoByNumero(term);
	}


}
