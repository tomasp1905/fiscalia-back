package com.fiscalia.springboot.backend.apirest.models.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="T_DECRETOS")
//@Table(name="decretos")
public class Decreto implements Serializable {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="ID_DECRETO")
	private long id;
	
	//@Column(nullable=false)
	@Column(name="NUMERO_DECRETO",nullable=false)
	private String numero;

	//@Column(nullable=false)
	@Column(name="TITULO_DECRETO",nullable=false)
	private String titulo;
	
	//@Column(nullable=false)
	@Column(name="AÑO_DECRETO",nullable=false)
	private String anio;

	@Column(name="FECHA_EMISION_DECRETO",nullable=false) //Se usa @Column cuando se quiere cambiar el nombre a la columna
	@Temporal(TemporalType.DATE) //para las fechas
	private Date fechaEmision;
	
	@Column(name="PUBLICACION_BO_DECRETO",nullable=false)
	@Temporal(TemporalType.DATE)
	private Date publicacionBO;

	@Column(name="ARCHIVO_DECRETO")
	private String archivo;
	
	@Column(name="ARCHIVO_ACTUALIZADO_DECRETO")
	private String archivoActualizado;
	
	public String getArchivo() {
		return archivo;
	}
	public void setArchivo(String archivo) {
		this.archivo = archivo;
	}

	
	public long getId() {
		return id;
	}


	public void setId(long id) {
		this.id = id;
	}


	public String getNumero() {
		return numero;
	}


	public void setNumero(String numero) {
		this.numero = numero;
	}


	public String getTitulo() {
		return titulo;
	}


	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}


	public String getAnio() {
		return anio;
	}


	public void setAnio(String anio) {
		this.anio = anio;
	}


	public Date getFechaEmision() {
		return fechaEmision;
	}


	public void setFechaEmision(Date fechaEmision) {
		this.fechaEmision = fechaEmision;
	}


	public Date getPublicacionBO() {
		return publicacionBO;
	}


	public void setPublicacionBO(Date publicacionBO) {
		this.publicacionBO = publicacionBO;
	}


	public String getArchivoActualizado() {
		return archivoActualizado;
	}
	public void setArchivoActualizado(String archivoActualizado) {
		this.archivoActualizado = archivoActualizado;
	}


	/**
	 Atributo estático que necesita Serializable. No afecta en nada
	 */
	private static final long serialVersionUID = 1L;

}
