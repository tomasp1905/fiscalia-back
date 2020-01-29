package com.fiscalia.springboot.backend.apirest.models.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="T_DECRETOS_LEY")
//@Table(name="decretosley")
public class DecretoLey implements Serializable {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="ID_DECRETO_LEY")
	private long id;
	
	//@Column(nullable=false)
	@Column(name="NUMERO_DECRETO_LEY",nullable=false)
	private String numero;

	//@Column(nullable=false)
	@Column(name="TITULO_DECRETO_LEY",nullable=false)
	private String titulo;
	
	//@Column(nullable=false)
	@Column(name="AÑO_DECRETO_LEY",nullable=false)
	private String anio;

	//@Column(name="fecha_emision",nullable=false) 
	@Temporal(TemporalType.DATE) //para las fechas
	@Column(name="FECHA_EMISION_DECRETO_LEY",nullable=false)
	private Date fechaEmision;
	
	//@Column(name="publicacion_BO",nullable=false)
	@Temporal(TemporalType.DATE)
	@Column(name="PUBLICACION_BO_DECRETO_LEY",nullable=false)
	private Date publicacionBO;

	@Column(name="ARCHIVO_DECRETO_LEY")
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


	public Date getfechaSancion() {
		return fechaEmision;
	}


	public void setfechaSancion(Date fechaSancion) {
		this.fechaEmision = fechaSancion;
	}


	public Date getPublicacionBO() {
		return publicacionBO;
	}


	public void setPublicacionBO(Date publicacionBO) {
		this.publicacionBO = publicacionBO;
	}

	private static final long serialVersionUID = 1L;

}
