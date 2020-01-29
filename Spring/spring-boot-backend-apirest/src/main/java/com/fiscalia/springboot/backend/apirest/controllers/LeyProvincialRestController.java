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



import com.fiscalia.springboot.backend.apirest.models.entity.LeyProvincial;
import com.fiscalia.springboot.backend.apirest.models.service.ILeyProvincialService;

@CrossOrigin(origins = { "http://localhost:4200" }) // conexion con Angular
@RestController
@RequestMapping("/api")
public class LeyProvincialRestController {

	@Autowired // llamo al servicio
	private ILeyProvincialService leyProvincialService;

	@GetMapping("/leyesProvinciales") // mapeo a la URL
	public List<LeyProvincial> index() {
		return leyProvincialService.findAll();
	}
	
	@GetMapping("/leyesProvinciales/page/{page}") // mapeo a la URL
	public Page<LeyProvincial> index(@PathVariable Integer page) {
		Pageable pageable = PageRequest.of(page,4);
		return leyProvincialService.findAll(pageable);
	}

	@GetMapping("/leyesProvinciales/{id}")
	public ResponseEntity<?> show(@PathVariable Long id) {
		LeyProvincial leyprovincial = null;
		Map<String, Object> response = new HashMap<>();
		try {
			leyprovincial = leyProvincialService.findById(id);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar la cosulta en la base de datos");
			response.put("mensaje", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}

		if (leyprovincial == null) {
			response.put("mensaje", "La ley ID: ".concat(id.toString().concat(" no existe en la Base de Datos! ")));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<LeyProvincial>(leyprovincial, HttpStatus.OK);
	}

	@PostMapping("/leyesProvinciales")
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<?> create(@RequestBody LeyProvincial leyprovincial) {
		LeyProvincial leyprovincialnew = null;
		Map<String, Object> response = new HashMap<>();
		try {
			leyprovincialnew = leyProvincialService.save(leyprovincial);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar el insert en la base de datos");
			response.put("mensaje", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		response.put("mensaje", "La Ley ha sido creado con exito!");
		response.put("ley", leyprovincialnew);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}

	@PutMapping("/leyesProvinciales/{id}")
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<?> update(@RequestBody LeyProvincial leyprovincial, @PathVariable Long id) {
		LeyProvincial leyprovincialActual = leyProvincialService.findById(id);
		LeyProvincial leyprovincialUpdate = null;
		Map<String, Object> response = new HashMap<>();
		if (leyprovincialActual == null) {
			response.put("mensaje", "Error no se pudo editar la ley ID: "
					.concat(id.toString().concat(" no existe en la Base de Datos! ")));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		try {
			leyprovincialActual.setTitulo(leyprovincial.getTitulo());
			leyprovincialActual.setFechaSancion(leyprovincial.getFechaSancion());
			leyprovincialActual.setNumero(leyprovincial.getNumero());
			leyprovincialActual.setPublicacionBO(leyprovincial.getPublicacionBO());
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al actualizar la ley en la base de datos");
			response.put("mensaje", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		leyprovincialUpdate = leyProvincialService.save(leyprovincialActual);
		response.put("mensaje", "La ley ha sido actualizado con exito!");
		response.put("ley", leyprovincialUpdate);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}

	@DeleteMapping("/leyesProvinciales/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ResponseEntity<?> delete(@PathVariable Long id) {

		Map<String, Object> response = new HashMap<>();
		try {
			LeyProvincial leyprovincial = leyProvincialService.findById(id);
			String nombreLeyAnterior = leyprovincial.getArchivo();

			leyProvincialService.delete(id);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al eliminar la ley de la base de datos");
			response.put("mensaje", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		response.put("mensaje", "La ley ha sido eliminado con exito!");
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}
	
	@PostMapping("/leyesProvinciales/upload")
	public ResponseEntity<?> upload(@RequestParam("archivo") MultipartFile archivo, @RequestParam("id") Long id) {
		Map<String, Object> response = new HashMap<>();
		LeyProvincial leyprovincial = leyProvincialService.findById(id);

		if (!archivo.isEmpty()) {
			String nombreArchivo = UUID.randomUUID().toString() + "_" +archivo.getOriginalFilename().replace("", "");
			String nombreArchivo2 = archivo.getOriginalFilename().replace("", "");
			
			Path rutaArchivo = Paths.get("uploads").resolve(nombreArchivo).toAbsolutePath();

			try {
				Files.copy(archivo.getInputStream(), rutaArchivo);
			} catch (IOException e) {
				response.put("mensaje", "Error al subir el archivo ley de la Base de Datos" + nombreArchivo);
				response.put("error", e.getMessage().concat(": ").concat(e.getCause().getMessage()));
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			}

			String nombreArchivoAnterior = leyprovincial.getArchivo();
			if (nombreArchivoAnterior != null && nombreArchivoAnterior.length() > 0) {
				Path rutaArchivoAnterior = Paths.get("uploads").resolve(nombreArchivoAnterior).toAbsolutePath();
				File archivoAnterior = rutaArchivoAnterior.toFile();
				if (archivoAnterior.exists() && archivoAnterior.canRead()) {
					archivoAnterior.delete();
				}
			}

			leyprovincial.setArchivo(nombreArchivo);
			leyProvincialService.save(leyprovincial);
			response.put("leyprovincial", leyprovincial);
			response.put("mensaje", "Has subido correctamente el archivo: " + nombreArchivo2);
		}
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}
	
	@PostMapping("/leyesProvinciales/uploadActualizado")
	public ResponseEntity<?> uploadActualizado(@RequestParam("archivo") MultipartFile archivo, @RequestParam("id") Long id) {
		Map<String, Object> response = new HashMap<>();
		LeyProvincial leyprovincial = leyProvincialService.findById(id);

		if (!archivo.isEmpty()) {
			String nombreArchivo = UUID.randomUUID().toString() + "_" +archivo.getOriginalFilename().replace("", "");
			//String nombreArchivo2 = archivo.getOriginalFilename().replace("", "");
			
			Path rutaArchivo = Paths.get("uploadsActualizado").resolve(nombreArchivo).toAbsolutePath();

			try {
				Files.copy(archivo.getInputStream(), rutaArchivo);
			} catch (IOException e) {
				response.put("mensaje", "Error al subir el archivo" + nombreArchivo);
				response.put("error", e.getMessage().concat(": ").concat(e.getCause().getMessage()));
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			}

			String nombreArchivoAnterior = leyprovincial.getArchivoActualizado();
			if (nombreArchivoAnterior != null && nombreArchivoAnterior.length() > 0) {
				Path rutaArchivoAnterior = Paths.get("uploadsActualizado").resolve(nombreArchivoAnterior).toAbsolutePath();
				File archivoAnterior = rutaArchivoAnterior.toFile();
				if (archivoAnterior.exists() && archivoAnterior.canRead()) {
					archivoAnterior.delete();
				}
			} 

			leyprovincial.setArchivoActualizado(nombreArchivo);
			leyProvincialService.save(leyprovincial);
			response.put("leyprovincial", leyprovincial);
			response.put("mensaje", "Has subido correctamente el archivo: " + nombreArchivo);
		}
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}
	
	
	//GET PARA DESCARGAR ARCHIVO
/*	@GetMapping("/uploads/img/{nombreArchivo:.+}")
	public ResponseEntity<Resource> verArchivo(@PathVariable String nombreArchivo) {

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
	}  */
	
	//GET PARA ABRIR ARCHIVO EN EL NAVEGADOR
	@GetMapping("/uploads/archivo/{nombreArchivo:.+}")
	public ResponseEntity<Resource> verArchivo(@PathVariable String nombreArchivo) {

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
	
	//GET PARA DESCARGAR ARCHIVO ACTUALIZADO
/*	@GetMapping("/uploadsActualizado/img/{nombreArchivo:.+}")
	public ResponseEntity<Resource> verArchivoActualizado(@PathVariable String nombreArchivo) {

		Path rutaArchivo = Paths.get("uploadsActualizado").resolve(nombreArchivo).toAbsolutePath();
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
	}  */
	
	//GET PARA VER ARCHIVO ACTUALIZADO
	@GetMapping("/uploadsActualizado/archivo/{nombreArchivo:.+}")
	public ResponseEntity<Resource> verArchivoActualizado(@PathVariable String nombreArchivo) {

		Path rutaArchivo = Paths.get("uploadsActualizado").resolve(nombreArchivo).toAbsolutePath();
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
	
	@GetMapping("/leyesProvinciales/filtrar-titulo/{term}")
	@ResponseStatus(HttpStatus.OK)
	public List<LeyProvincial> filtrarTitulo(@PathVariable String term){
		return leyProvincialService.findLeyProvincialByTitulo(term);
	}
	
	@GetMapping("/leyesProvinciales/filtrar-numero/{term}")
	@ResponseStatus(HttpStatus.OK)
	public List<LeyProvincial> filtrarNumero(@PathVariable String term){
		return leyProvincialService.findLeyProvincialByNumero(term);
	}


}
