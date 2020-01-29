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
@Table(name="T_DECRETOS_REGLAMENTARIOS")
//@Table(name="decretosReglamentario")
public class DecretoReglamentario implements Serializable {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="ID_DECRETO_LEY")
	private long id;
	
	//@Column(nullable=false)
	@Column(name="NUMERO_DECRETO_REGLAMENTARIO",nullable=false)
	private String numero;
	
	//@Column(nullable=false)
	@Column(name="AÑO_DECRETO_REGLAMENTARIO",nullable=false)
	private String anio;

	//@Column(name="fecha_emision",nullable=false)
	@Temporal(TemporalType.DATE) //para las fechas
	@Column(name="FECHA_EMISION_DECRETO_REGLAMENTARIO",nullable=false)
	private Date fechaEmision;
	
	//@Column(name="publicacion_BO",nullable=false)
	@Temporal(TemporalType.DATE)
	@Column(name="PUBLICACION_BO_DECRETO_REGLAMENTARIO",nullable=false)
	private Date publicacionBO;

	@Column(name="ARCHIVO_DECRETO_REGLAMENTARIO")
	private String archivo;
	
	@Column(name="RELACION_DECRETO_REGLAMENTARIO")
	private String relacion;
	
	public String getRelacion() {
		return relacion;
	}
	public void setRelacion(String relacion) {
		this.relacion = relacion;
	}
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


	public Date getfechaEmision() {
		return fechaEmision;
	}


	public void setfechaEmision(Date fechaEmision) {
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
