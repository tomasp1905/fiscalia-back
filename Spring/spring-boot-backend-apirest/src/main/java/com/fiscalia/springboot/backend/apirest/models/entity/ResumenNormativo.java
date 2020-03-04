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
@Table(name="T_RESUMENES")
//@Table(name="resumenes")
public class ResumenNormativo implements Serializable {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="ID_RESUMEN_NORMATIVO")
	private long id;
	
	//@Column(nullable=false)
	@Temporal(TemporalType.DATE)
	@Column(name="FECHA_RESUMEN_NORMATIVO",nullable=false)
	private Date fecha;

	@Column(name="ARCHIVO_RESUMEN_NORMATIVO")
	private String archivo;
	
	
	public long getId() {
		return id;
	}


	public void setId(long id) {
		this.id = id;
	}


	public Date getFecha() {
		return fecha;
	}


	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}


	public String getArchivo() {
		return archivo;
	}


	public void setArchivo(String archivo) {
		this.archivo = archivo;
	}


	private static final long serialVersionUID = 1L;

}
