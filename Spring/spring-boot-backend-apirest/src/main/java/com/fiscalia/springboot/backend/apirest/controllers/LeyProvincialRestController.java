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
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
	public ResponseEntity<?>  show(@PathVariable Long id) {
		LeyProvincial leyprovincial = null;
		Map<String, Object> response = new HashMap<>();
		try {
			leyprovincial = leyProvincialService.findById(id);
		} catch(DataAccessException e ) {
			response.put("mensaje", "Error al realizar la cosulta en la base de datos");
			response.put("mensaje", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String,Object>>(response, HttpStatus.NOT_FOUND);
		}
		
		
		if(leyprovincial == null) {
			response.put("mensaje", "La ley ID: ".concat(id.toString().concat(" no esxiste en la Base de Datos! ")));
			return new ResponseEntity<Map<String,Object>>(response, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<LeyProvincial>(leyprovincial,HttpStatus.OK);
	}
	
	@PostMapping("/leyesProvinciales")
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<?> create(@RequestBody LeyProvincial leyprovincial) {
		LeyProvincial leyprovincialnew = null;
		Map<String, Object> response = new HashMap<>();
		try {
			leyprovincialnew = leyProvincialService.save(leyprovincial);
		}catch(DataAccessException e) {
			response.put("mensaje", "Error al realizar el insert en la base de datos");
			response.put("mensaje", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String,Object>>(response, HttpStatus.NOT_FOUND);
		}
		response.put("mensaje", "El cliente ha sido cread con exito!");
		response.put("ley", leyprovincialnew);
		return new ResponseEntity<Map<String,Object>>(response,HttpStatus.CREATED);
	}
	
	@PutMapping("/leyesProvinciales/{id}")
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<?> update(@RequestBody LeyProvincial leyprovincial, @PathVariable Long id) {
		LeyProvincial leyprovincialActual = leyProvincialService.findById(id);
		LeyProvincial leyprovincialUpdate = null;  
		Map<String, Object> response = new HashMap<>();
		if(leyprovincialActual == null) {
			response.put("mensaje", "Error no se pudo editar la ley ID: ".concat(id.toString().concat(" no esxiste en la Base de Datos! ")));
			return new ResponseEntity<Map<String,Object>>(response, HttpStatus.NOT_FOUND);
		}
		try {
		leyprovincialActual.setTitulo(leyprovincial.getTitulo());
		leyprovincialActual.setFechaSancion(leyprovincial.getFechaSancion());
		leyprovincialActual.setNumero(leyprovincial.getNumero());
		leyprovincialActual.setPublicacionBO(leyprovincial.getPublicacionBO());
			}catch(DataAccessException e) {
			response.put("mensaje", "Error al actualizar la ley en la base de datos");
			response.put("mensaje", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String,Object>>(response, HttpStatus.NOT_FOUND);
		}
		leyprovincialUpdate =leyProvincialService.save(leyprovincialActual);
		response.put("mensaje", "El cliente ha sido actualizado con exito!");
		response.put("ley", leyprovincialUpdate);
		return new ResponseEntity<Map<String,Object>>(response,HttpStatus.CREATED); 
	}
	
	
	@DeleteMapping("/leyesProvinciales/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ResponseEntity<?> delete(@PathVariable Long id) {
		
		/*
		 * Poner Parte del video de subir fotos (93.Borrar imagen anterior al
		 * actualizar)
		 */
		Map<String, Object> response = new HashMap<>();
		try {
		
		leyProvincialService.delete(id);
		}catch(DataAccessException e) {
		response.put("mensaje", "Error al eliminar la ley de la base de datos");
		response.put("mensaje", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
		return new ResponseEntity<Map<String,Object>>(response, HttpStatus.NOT_FOUND);
	}
		response.put("mensaje", "El cliente eliminado con exito!");
		return new ResponseEntity<Map<String,Object>>(response,HttpStatus.OK);
	}
	
	@PostMapping("/leyesProvinciales/upload")
	public ResponseEntity<?> upload(@RequestParam("archivo") MultipartFile archivo, @RequestParam("id") Long id ){
		Map<String,Object> response = new HashMap<>();
		LeyProvincial leyprovincial = leyProvincialService.findById(id);
		
		if(!archivo.isEmpty()) {
			String nombreArchivo = archivo.getOriginalFilename().replace("", "");
			Path rutaArchivo = Paths.get("uploads").resolve(nombreArchivo).toAbsolutePath();
			
			try {
				Files.copy(archivo.getInputStream(), rutaArchivo);
			} catch (IOException e) {
				response.put("mensaje", "Error al eliminar la Foto ley de la Base de Datos"+ nombreArchivo);
				response.put("error",e.getMessage().concat(": ").concat(e.getCause().getMessage()));
				return new ResponseEntity<Map<String,Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);
			}
			
			String nombreFotoAnterior = leyprovincial.getFoto();
			if (nombreFotoAnterior !=null && nombreFotoAnterior.length() > 0 ) {
				Path rutaFotoAnterior = Paths.get("uploads").resolve(nombreFotoAnterior).toAbsolutePath();
				File archivoFotoAnterior = rutaFotoAnterior.toFile();
				if (archivoFotoAnterior.exists() && archivoFotoAnterior.canRead()) {
					archivoFotoAnterior.delete();
				}
			}
			
			leyprovincial.setFoto(nombreArchivo);
			leyProvincialService.save(leyprovincial);
			response.put("leyprovincial", leyprovincial);
			response.put("mensaje","Has subido correctamente la imagen: "+ nombreArchivo);
		}
		return new ResponseEntity<Map<String,Object>>(response,HttpStatus.CREATED);
	}
	
	@GetMapping("/uploads/img/{nombreFoto:.+}")
	public ResponseEntity<Resource> verFoto(@PathVariable String nombreFoto){
		
		Path rutaArchivo = Paths.get("uploads").resolve(nombreFoto).toAbsolutePath();
		Resource recurso = null;
		
		try {
			recurso = new UrlResource(rutaArchivo.toUri());
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		
		if(!recurso.exists() && !recurso.isReadable()) {
			throw new RuntimeException("Error no se pudo cargar la imagen: " + nombreFoto);
		}
		HttpHeaders cabecera = new HttpHeaders();
		cabecera.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + recurso.getFilename() + "\"" );
		return new ResponseEntity<Resource>(recurso,cabecera,HttpStatus.OK);
	}
	
	
}
