package ar.com.sourcesistemas.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;



@Entity
public class Category {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@Column(name = "nombreCategoria")
	private String nombreCategoria;
	@ManyToOne
	private User user;
	@OneToMany(mappedBy = "categoria", cascade=CascadeType.ALL)
	private List<Snipplet> snipplets;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombreCategoria() {
		return nombreCategoria;
	}

	public void setNombreCategoria(String nombreCategoria) {
		this.nombreCategoria = nombreCategoria;
	}

	public List<Snipplet> getSnipplets() {
		return snipplets;
	}

	public void setSnipplets(List<Snipplet> snipplets) {
		this.snipplets = snipplets;
	}

	public void add(Snipplet snipplet) {

		if (snipplets == null)
			snipplets = new ArrayList<Snipplet>();

		snipplets.add(snipplet);

	}
}