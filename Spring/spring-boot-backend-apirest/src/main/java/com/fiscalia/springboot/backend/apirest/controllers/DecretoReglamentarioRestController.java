package com.fiscalia.springboot.backend.apirest.controllers;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fiscalia.springboot.backend.apirest.models.entity.DecretoReglamentario;
import com.fiscalia.springboot.backend.apirest.models.entity.LeyProvincial;
import com.fiscalia.springboot.backend.apirest.models.service.ILeyProvincialService;

@CrossOrigin(origins = { "http://localhost:4200" })
@RestController
@RequestMapping("/api")
public class DecretoReglamentarioRestController {
	
	@Autowired
	private ILeyProvincialService leyProvincialService;
	
	@GetMapping("/decretosReglamentarios/{id}")
	@ResponseStatus(HttpStatus.OK)
	public DecretoReglamentario show(@PathVariable Long id) {
		return leyProvincialService.findDecretoReglamentarioById(id);
	}
	
	@PostMapping("/decretosReglamentarios/upload")
	public ResponseEntity<?> upload(@RequestParam("archivo") MultipartFile archivo, @RequestParam("id") Long id) {
		Map<String, Object> response = new HashMap<>();
		DecretoReglamentario decretoReglamentario = leyProvincialService.findDecretoReglamentarioById(id);

		if (!archivo.isEmpty()) {
			String nombreArchivo = UUID.randomUUID().toString() + "_" +archivo.getOriginalFilename().replace("", "");
			String nombreArchivo2 = archivo.getOriginalFilename().replace("", "");
			
			Path rutaArchivo = Paths.get("uploadsDecReg").resolve(nombreArchivo).toAbsolutePath();

			try {
				Files.copy(archivo.getInputStream(), rutaArchivo);
			} catch (IOException e) {
				response.put("mensaje", "Error al subir el archivo ley de la Base de Datos" + nombreArchivo);
				response.put("error", e.getMessage().concat(": ").concat(e.getCause().getMessage()));
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			}

			String nombreArchivoAnterior = decretoReglamentario.getArchivo();
			if (nombreArchivoAnterior != null && nombreArchivoAnterior.length() > 0) {
				Path rutaArchivoAnterior = Paths.get("uploadsDecReg").resolve(nombreArchivoAnterior).toAbsolutePath();
				File archivoAnterior = rutaArchivoAnterior.toFile();
				if (archivoAnterior.exists() && archivoAnterior.canRead()) {
					archivoAnterior.delete();
				}
			}

			decretoReglamentario.setArchivo(nombreArchivo);
			leyProvincialService.saveDecretoReglamentario(decretoReglamentario);
			response.put("decretoReglamentario", decretoReglamentario);
			response.put("mensaje", "Has subido correctamente el archivo: " + nombreArchivo2);
		}
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}
	
	@GetMapping("/decretosReglamentarios/uploads/archivo/{nombreArchivo:.+}")
	public ResponseEntity<Resource> verArchivo(@PathVariable String nombreArchivo) {

		Path rutaArchivo = Paths.get("uploadsDecReg").resolve(nombreArchivo).toAbsolutePath();
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
	
      
}
