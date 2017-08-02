package ar.com.sourcesistemas.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.google.gson.annotations.Expose;

@Entity
public class Snipplet implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Expose
	private Long id;
	@Expose
	private String titulo;
	@Expose
	private String contenido;
	@ManyToOne
	@JoinColumn(name = "categoria_id")
	private Category categoria;
	
	

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getContenido() {
		return contenido;
	}

	public void setContenido(String contenido) {
		this.contenido = contenido;
	}

	
	public Category getCategoria() {
		return categoria;
	}

	public void setCategoria(Category categoria) {
		this.categoria = categoria;
	}

	public boolean buscarTexto(String palabra) {

		if (contenido.indexOf(palabra) != -1 || titulo.indexOf(palabra) != -1) {

			return true;
		} else {

			return false;

		}

	}

}