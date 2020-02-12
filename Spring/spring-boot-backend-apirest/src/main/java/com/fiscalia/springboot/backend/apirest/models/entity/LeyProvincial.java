package com.fiscalia.springboot.backend.apirest.models.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;





@Entity
@Table(name="T_LEYES")
//@Table(name="leyes")
public class LeyProvincial implements Serializable {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="ID_LEY")
	private long id;
	
	//@Column(nullable=false)
	@Column(name="NUMERO_LEY",nullable=false)
	private String numero;
	
	//@Column(nullable=false)
	@Column(name="TITULO_LEY",nullable=false)
	private String titulo;
	
	//@Column(name="fecha_sancion",nullable=false) 
	@Temporal(TemporalType.DATE) //para las fechas
	@Column(name="FECHA_SANCION_LEY",nullable=false)
	private Date fechaSancion;
	
	//@Column(name="publicacion_BO",nullable=false)
	@Temporal(TemporalType.DATE)
	@Column(name="PUBLICACION_BO_LEY",nullable=false)
	private Date publicacionBO;
	
	@Column(name="ARCHIVO_LEY")
	private String archivo;
	
	@Column(name="ARCHIVO_ACTUALIZADO_LEY")
	private String archivoActualizado;
	
	@Column(name="ARCHIVO_DECRETO_REGLAMENTARIO_LEY")
    private String archivoDecretoReglamentario;
	
	@Column(name="ARCHIVO_DECRETO_REGLAMENTARIO_2_LEY")
	private String archivoDecretoReglamentario2;
	
    
    /*
	@JsonIgnoreProperties({"leyProvincial", "hibernateLazyInitializer","handler"})
	@OneToMany(fetch=FetchType.LAZY, mappedBy = "leyProvincial", cascade = {CascadeType.ALL})
	private List<DecretoReglamentario> listaDeDecretosReglamentarios; */
	
	
   /*
	public LeyProvincial() {
		this.listaDeDecretosReglamentarios = new ArrayList<>();
	} */

 
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

	public void setNumero(String number) {
		this.numero = number;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public Date getFechaSancion() {
		return fechaSancion;
	}

	public void setFechaSancion(Date fechaSancion) {
		this.fechaSancion = fechaSancion;
	}

	public Date getPublicacionBO() {
		return publicacionBO;
	}

	public void setPublicacionBO(Date publicacionBO) {
		this.publicacionBO = publicacionBO;
	}
	
	/**
	 Atributo estático que necesita Serializable. No afecta en nada
	 */
	private static final long serialVersionUID = 1L;

	

	public String getArchivoActualizado() {
		return archivoActualizado;
	}


	public void setArchivoActualizado(String archivoActualizado) {
		this.archivoActualizado = archivoActualizado;
	}


	/*
	public List<DecretoReglamentario> getListaDeDecretosReglamentarios() {
		return listaDeDecretosReglamentarios;
	}


	public void setListaDeDecretosReglamentarios(List<DecretoReglamentario> listaDeDecretosReglamentarios) {
		this.listaDeDecretosReglamentarios = listaDeDecretosReglamentarios;
	} */
	

	public String getArchivoDecretoReglamentario() {
		return archivoDecretoReglamentario;
	}


	public void setArchivoDecretoReglamentario(String archivoDecretoReglamentario) {
		this.archivoDecretoReglamentario = archivoDecretoReglamentario;
	}


	public String getArchivoDecretoReglamentario2() {
		return archivoDecretoReglamentario2;
	}


	public void setArchivoDecretoReglamentario2(String archivoDecretoReglamentario2) {
		this.archivoDecretoReglamentario2 = archivoDecretoReglamentario2;
	}
	
	


	

}
