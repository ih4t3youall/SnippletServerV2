package ar.com.sourcesistemas.dto;



import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CategoriaDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String nombre;
	private List<SnippletDTO> snipplets;
	private List<String> tags;

	public CategoriaDTO() {
	}

	public CategoriaDTO(String nombre) {
		this.nombre = nombre;

	}

	public void addSnippletDTO(SnippletDTO snipplet) {
		if (snipplets == null) {

			snipplets = new ArrayList<SnippletDTO>();

		}

		snipplets.add(snipplet);

	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public List<SnippletDTO> getSnipplets() {
		return snipplets;
	}

	public void setSnipplets(List<SnippletDTO> snipplets) {
		this.snipplets = snipplets;
	}

	public List<String> getTags() {
		return tags;
	}

	public void setTags(List<String> tags) {
		this.tags = tags;
	}

}
