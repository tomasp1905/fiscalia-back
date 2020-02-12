package com.fiscalia.springboot.backend.apirest.models.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "T_DECRETOS_REGLAMENTARIOS")
public class DecretoReglamentario implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID_DECRETO_REGLAMENTARIO")
	private long id;

	@Column(name = "ARCHIVO_DECRETO_REGLAMENTARIO")
	private String archivo;

	@Column(name = "NUMERO_DECRETO_REGLAMENTARIO")
	private String numero;

	@Temporal(TemporalType.DATE)
	@Column(name = "FECHA_EMISION_DECRETO_REGLAMENTARIO")
	private Date fechaEmision;

	@Temporal(TemporalType.DATE)
	@Column(name = "PUBLICACION_BO_DECRETO_REGLAMENTARIO")
	private Date publicacionBO;

	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.ALL })
	@JoinColumn(name = "ID_LEY")
	private LeyProvincial leyProvincial;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getArchivo() {
		return archivo;
	}

	public void setArchivo(String archivo) {
		this.archivo = archivo;
	}

	public LeyProvincial getLeyProvincial() {
		return leyProvincial;
	}

	public void setLeyProvincial(LeyProvincial leyProvincial) {
		this.leyProvincial = leyProvincial;
	}
	
	

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
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


	private static final long serialVersionUID = 1L;

}
