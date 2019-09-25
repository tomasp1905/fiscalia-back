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
@Table(name="leyes")
public class LeyProvincial implements Serializable {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	@Column(nullable=false)
	private String numero;
	@Column(nullable=false)
	private String titulo;
	@Column(name="fecha_sancion",nullable=false) //Se usa @Column cuando se quiere cambiar el nombre a la columna
	@Temporal(TemporalType.DATE) //para las fechas
	private Date fechaSancion;
	
	@Column(name="publicacion_BO",nullable=false)
	@Temporal(TemporalType.DATE)
	private Date publicacionBO;
	
	
	private String foto;
	
	public String getFoto() {
		return foto;
	}

	public void setFoto(String foto) {
		this.foto = foto;
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

}
