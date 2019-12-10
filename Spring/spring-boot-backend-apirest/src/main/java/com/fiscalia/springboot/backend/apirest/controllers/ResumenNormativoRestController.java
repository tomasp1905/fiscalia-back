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

import com.fiscalia.springboot.backend.apirest.models.entity.ResumenNormativo;
import com.fiscalia.springboot.backend.apirest.models.service.IResumenNormativoService;

@CrossOrigin(origins = { "http://localhost:4200" }) // conexion con Angular
@RestController
@RequestMapping("/api")
public class ResumenNormativoRestController {

	@Autowired // llamo al servicio
	private IResumenNormativoService resumenNormativoService;

	@GetMapping("/resumenNormativo") // mapeo a la URL
	public List<ResumenNormativo> index() {
		return resumenNormativoService.findAll();
	}
	
	@GetMapping("/resumenNormativo/page/{page}") // mapeo a la URL
	public Page<ResumenNormativo> index(@PathVariable Integer page) {
		Pageable pageable = PageRequest.of(page,4);
		return resumenNormativoService.findAll(pageable);
	}

	@GetMapping("/resumenNormativo/{id}")
	public ResponseEntity<?> show(@PathVariable Long id) {
		ResumenNormativo resumenNormativo = null;
		Map<String, Object> response = new HashMap<>();
		try {
			resumenNormativo = resumenNormativoService.findById(id);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar la cosulta en la base de datos");
			response.put("mensaje", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}

		if (resumenNormativo == null) {
			response.put("mensaje", "La ley ID: ".concat(id.toString().concat(" no existe en la Base de Datos! ")));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<ResumenNormativo>(resumenNormativo, HttpStatus.OK);
	}

	@PostMapping("/resumenNormativo")
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<?> create(@RequestBody ResumenNormativo resumenNormativo) {
		ResumenNormativo resumenNormativonew = null;
		Map<String, Object> response = new HashMap<>();
		try {
			resumenNormativonew = resumenNormativoService.save(resumenNormativo);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar el insert en la base de datos");
			response.put("mensaje", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		response.put("mensaje", "El resumen normativo  ha sido creado con exito!");
		response.put("resumen normativo", resumenNormativonew);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}

	@PutMapping("/resumenNormativo/{id}")
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<?> update(@RequestBody ResumenNormativo resumenNormativo, @PathVariable Long id) {
		ResumenNormativo resumenNormativoActual = resumenNormativoService.findById(id);
		ResumenNormativo resumenNormativoUpdate = null;
		Map<String, Object> response = new HashMap<>();
		if (resumenNormativoActual == null) {
			response.put("mensaje", "Error no se pudo editar el resumen normativo ID: "
					.concat(id.toString().concat(" no existe en la Base de Datos! ")));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		try {
			resumenNormativoActual.setAnio(resumenNormativo.getAnio());
			resumenNormativoActual.setMes(resumenNormativo.getMes());
			resumenNormativoActual.setSemana(resumenNormativo.getSemana());
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al actualizar el resumen normativo en la base de datos");
			response.put("mensaje", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		resumenNormativoUpdate = resumenNormativoService.save(resumenNormativoActual);
		response.put("mensaje", "El resumen ha sido actualizado con exito!");
		response.put("ley", resumenNormativoUpdate);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}

	@DeleteMapping("/resumenNormativo/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ResponseEntity<?> delete(@PathVariable Long id) {

		Map<String, Object> response = new HashMap<>();
		try {
			ResumenNormativo resumenNormativo = resumenNormativoService.findById(id);
			String nombreresumenNormativoAnterior = resumenNormativo.getArchivo();

			resumenNormativoService.delete(id);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al eliminar el resumen normativo de la base de datos");
			response.put("mensaje", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		response.put("mensaje", "El resumen normativo ha sido eliminado con exito!");
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}
	
	@PostMapping("/resumenNormativo/upload")
	public ResponseEntity<?> upload(@RequestParam("archivo") MultipartFile archivo, @RequestParam("id") Long id) {
		Map<String, Object> response = new HashMap<>();
		ResumenNormativo resumenNormativo = resumenNormativoService.findById(id);

		if (!archivo.isEmpty()) {
			String nombreArchivo = UUID.randomUUID().toString() + "_" +archivo.getOriginalFilename().replace("", "");
			Path rutaArchivo = Paths.get("uploads").resolve(nombreArchivo).toAbsolutePath();

			try {
				Files.copy(archivo.getInputStream(), rutaArchivo);
			} catch (IOException e) {
				response.put("mensaje", "Error al subir el archivo del resumen normativo de la Base de Datos" + nombreArchivo);
				response.put("error", e.getMessage().concat(": ").concat(e.getCause().getMessage()));
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			}

			String nombreFotoAnterior = resumenNormativo.getArchivo();
			if (nombreFotoAnterior != null && nombreFotoAnterior.length() > 0) {
				Path rutaFotoAnterior = Paths.get("uploads").resolve(nombreFotoAnterior).toAbsolutePath();
				File archivoFotoAnterior = rutaFotoAnterior.toFile();
				if (archivoFotoAnterior.exists() && archivoFotoAnterior.canRead()) {
					archivoFotoAnterior.delete();
				}
			}

			resumenNormativo.setArchivo(nombreArchivo);
			resumenNormativoService.save(resumenNormativo);
			response.put("resumenNormativo", resumenNormativo);
			response.put("mensaje", "Has subido correctamente el archivo: " + nombreArchivo);
		}
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}
	
	@GetMapping("/uploads/archivoResumen/{nombreFoto:.+}")
	public ResponseEntity<Resource> verFoto(@PathVariable String nombreFoto) {

		Path rutaArchivo = Paths.get("uploads").resolve(nombreFoto).toAbsolutePath();
		Resource recurso = null;

		try {
			recurso = new UrlResource(rutaArchivo.toUri());
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}

		if (!recurso.exists() && !recurso.isReadable()) {
			throw new RuntimeException("Error no se pudo cargar el archivo: " + nombreFoto);
		}
		HttpHeaders cabecera = new HttpHeaders();
		cabecera.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + recurso.getFilename() + "\"");
		return new ResponseEntity<Resource>(recurso, cabecera, HttpStatus.OK);
	}
	
	@GetMapping("/resumenNormativo/filtrar-anio/{term}")
	@ResponseStatus(HttpStatus.OK)
	public List<ResumenNormativo> filtrarNumero(@PathVariable String term){
		return resumenNormativoService.findResumenNormativoByAnio(term);
	}


}
