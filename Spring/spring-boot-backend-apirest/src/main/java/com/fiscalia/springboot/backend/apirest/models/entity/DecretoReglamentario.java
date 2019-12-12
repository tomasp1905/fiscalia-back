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
@Table(name="decretosReglamentario")
public class DecretoReglamentario implements Serializable {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	
	@Column(nullable=false)
	private String numero;
	
	@Column(nullable=false)
	private String anio;

	@Column(name="fecha_emision",nullable=false) //Se usa @Column cuando se quiere cambiar el nombre a la columna
	@Temporal(TemporalType.DATE) //para las fechas
	private Date fechaEmision;
	
	@Column(name="publicacion_BO",nullable=false)
	@Temporal(TemporalType.DATE)
	private Date publicacionBO;

	private String archivo;
	
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

	public String getAnio() {
		return anio;
	}


	public void setAnio(String anio) {
		this.anio = anio;
	}


	public Date getfechaSancion() {
		return fechaEmision;
	}


	public void setfechaSancion(Date fechaEmision) {
		this.fechaEmision = fechaEmision;
	}


	public Date getPublicacionBO() {
		return publicacionBO;
	}


	public void setPublicacionBO(Date publicacionBO) {
		this.publicacionBO = publicacionBO;
	}

	private static final long serialVersionUID = 1L;

}
